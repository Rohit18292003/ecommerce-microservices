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
    public String checkProduct(@PathVariable String productID){
       return productService.isProductAvailableInStock(productID);

    }

    @PostMapping("/create")
    public Product createProduct(@RequestBody Product product){

        return  productService.createProduct(product);
    }

}
