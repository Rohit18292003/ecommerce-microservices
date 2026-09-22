package org.resttemplate.orderservice.controller;


import org.resttemplate.orderservice.DAO.ProductRequest;
import org.resttemplate.orderservice.DAO.ProductResp;
import org.resttemplate.orderservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/checkProduct/{productID}")
    public String checkProduct(@PathVariable String productID){
       return orderService.getProductStatus(productID);
    }


    @PostMapping("/createProduct")
    public ProductResp createProduct(@RequestBody ProductRequest product){
        return orderService.createProduct(product);
    }

}
