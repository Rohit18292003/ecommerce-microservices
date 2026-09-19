package org.resttemplate.productservice.services;

import org.resttemplate.productservice.entity.Product;
import org.resttemplate.productservice.repo.productRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private productRepo repo;

    public String isProductAvailableInStock(String productID){

        if(productID.equals("1")){
            return "Product available and your order confirm";
        }else{
            return "product not available";
        }

    }


    public Product createProduct(Product product){
       return repo.save(product);
    }
}
