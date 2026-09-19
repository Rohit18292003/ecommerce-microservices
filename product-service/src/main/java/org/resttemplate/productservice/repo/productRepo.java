package org.resttemplate.productservice.repo;

import org.resttemplate.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface productRepo extends JpaRepository<Product, Long> {
}
