package org.resttemplate.orderservice.DAO;

public class ProductResp {

    Long id;
    String productName;

    public Long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Long productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ProductResp{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productQuantity=" + productQuantity +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    Long productQuantity;
}
