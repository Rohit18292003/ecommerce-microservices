package org.resttemplate.productservice.controller;
import org.resttemplate.productservice.entity.Product;
import org.resttemplate.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/check/{productID}")
    public String checkProduct(@PathVariable String productID) throws InterruptedException {
        System.out.println("Product Service: request started");
        Thread.sleep(10000);
        System.out.println("Product Service: request complete");
       return productService.isProductAvailableInStock(productID);

    }

    @PostMapping("/create")
    public Product createProduct(@RequestBody Product product){

        return  productService.createProduct(product);
    }

}
