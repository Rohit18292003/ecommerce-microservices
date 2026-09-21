package org.resttemplate.orderservice.services;


import org.resttemplate.orderservice.DAO.ProductRequest;
import org.resttemplate.orderservice.DAO.ProductResp;
import org.resttemplate.orderservice.client.ProductClient;
import org.resttemplate.orderservice.excep.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class OrderService {

    //Rest Template
//    @Autowired
//    private  RestTemplate restTemplate;


    //RestClient
    @Autowired
    private RestClient restClient;

    @Autowired
    ProductClient productClient;

//    public String getOrderService(String productID) {
//
//        //  String resultOfrestTemplate = restTemplate.getForObject("http://localhost:8081/product/check/" + productID, String.class);
//
//
//        //RestClient Demo
//        String resultOfRestClient = restClient.get().uri("http://localhost:8081/product/check/" + productID)
//                .retrieve()
//                .onStatus(httpStatusCode -> httpStatusCode.equals(404), (request, response) -> {
//                            throw new ProductNotFoundException("Product not found");
//                        }
//
//                )
//                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
//                    throw new RuntimeException("Product service is not working");
//                })).body(String.class);
//
//
//        return "Response :" + resultOfRestClient;
//    }


//    public ProductResp createProduct(ProductRequest product) {
//
//
//        ProductResp result = restClient.post()
//                .uri("http://localhost:8081/product/create")
//                .body(product)
//                .retrieve()
//                .body(ProductResp.class);
//        System.out.println("product service response " + result);
//        return result;
//    }


    //Feign Client Demo

    public String  getProductStatus(String productID){
        System.out.println("Order Service: before Feign call");
        String result = productClient.checkProduct(productID);

        System.out.println("Order Service: after Feign call");
return  result;
    }


    public ProductResp createProduct(ProductRequest product) {
        return productClient.createProduct(product);

    }
}
