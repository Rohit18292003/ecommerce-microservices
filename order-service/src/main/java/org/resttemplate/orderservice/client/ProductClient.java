package org.resttemplate.orderservice.client;


import org.resttemplate.orderservice.DAO.ProductRequest;
import org.resttemplate.orderservice.DAO.ProductResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name ="product-service",
        url ="http://localhost:8081/product"
        
)
public interface ProductClient {

    @GetMapping("/check/{productID}")
    public String checkProduct(@PathVariable String productID);


    @PostMapping("/create")
    public ProductResp createProduct(@RequestBody ProductRequest product);
}
