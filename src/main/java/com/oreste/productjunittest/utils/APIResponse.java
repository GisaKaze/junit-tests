package com.oreste.productjunittest.utils;

import com.oreste.productjunittest.model.Product;

public class APIResponse {
    private Boolean status;

    private String message;

    private Product data;

    public APIResponse(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public APIResponse(Boolean status, String message,Product data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Product getData() {
        return data;
    }

    public void setData(Product data) {
        this.data = data;
    }
}
