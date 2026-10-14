package org.resttemplate.orderservice.services;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;

import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.ws.rs.core.MediaType;
import org.resttemplate.orderservice.DAO.ProductRequest;
import org.resttemplate.orderservice.DAO.ProductResp;

import org.resttemplate.orderservice.client.ProductFeignClient;

import org.springframework.beans.factory.annotation.Autowired;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;


import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

@Service
public class OrderService {

    // ==========================================
    // 1. RestTemplate
    // ==========================================

    // @Autowired
    // private RestTemplate restTemplate;


    // ==========================================
    // 2. RestClient
    // ==========================================

    @Autowired
    private RestClient restClient;


    // ==========================================
    // 3. Feign Client
    // ==========================================

    @Autowired
    private ProductFeignClient productClient;


    @Autowired
    private WebClient webClient;

    // ==========================================
    // 4. Get Product Status using RestClient
    // ==========================================

    /*
    public String getOrderService(String productID) {

        String result = restClient.get()
                .uri("http://localhost:8081/product/check/" + productID)
                .retrieve()
                .onStatus(
                    status -> status.value() == 404,
                    (request, response) -> {
                        throw new ProductNotFoundException(
                            "Product not found"
                        );
                    }
                )
                .onStatus(
                    HttpStatusCode::is5xxServerError,
                    (request, response) -> {
                        throw new RuntimeException(
                            "Product service is not working"
                        );
                    }
                )
                .body(String.class);

        return "Response: " + result;
    }
    */


    // ==========================================
    // 5. Create Product using RestClient
    // ==========================================

    /*
    public ProductResp createProductUsingRestClient(
            ProductRequest product) {

        ProductResp result = restClient.post()
                .uri("http://localhost:8081/product/create")
                .body(product)
                .retrieve()
                .body(ProductResp.class);

        System.out.println("Product service response: " + result);

        return result;
    }
    */


    // ==========================================
    // 6. Get Product Status using Feign Client
    // ==========================================

   /* public String getProductStatus(String productID) {

        System.out.println("Order Service: Before Feign call");

        String result = productClient.checkProduct(productID);

        System.out.println("Order Service: After Feign call");

        return result;
    }

    */


    // ==========================================
    // 7. Create Product using Feign Client
    // ==========================================

  /*  public ProductResp createProduct(ProductRequest product) {

        return productClient.createProduct(product);
    }
    */

    @CircuitBreaker(
            name = "productService",
            fallbackMethod = "productFallback"
    )

    public Mono<String> getProductStatus(String productID){

        return  webClient.get()
                .uri("/product/check/{productId}", productID)
                .header("X-Service-Name", "order-service")
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(12));

    }
    //Circuit breaker
    // Must match the original method's arguments,
    // followed by the exception.
    public Mono<String> productFallback(
            String productId,
            Throwable ex) {

        System.out.println(
                "Product Service failed: " + ex.getMessage()
        );

        return Mono.just(
                "Fallback: Product Service is unavailable. Product ID: "
                        + productId
        );
    }


   // @TimeLimiter(name = "createProductService", fallbackMethod = "createproductFallback")
    @Bulkhead(
            name = "productService",
            type = Bulkhead.Type.SEMAPHORE
    )
    public Mono<ProductResp> createProduct(ProductRequest product) {

        RetryConfig config = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .build();

        Retry retry = Retry.of("productService", config);

        return webClient.post()
                .uri("/product/create")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .retrieve()
                .bodyToMono(ProductResp.class)
                .transformDeferred(RetryOperator.of(retry));
    }


    private Mono<ProductResp> createProductFallback(
            ProductRequest product,
            Throwable exception) {

        System.out.println(
                "Create product failed: " + exception.getMessage() +" for Product "+product.getProductName()
        );

        return Mono.error(
                new RuntimeException(
                        "Product Service is taking too long to respond.. Please try again later.",
                        exception
                )
        );
    }

}
