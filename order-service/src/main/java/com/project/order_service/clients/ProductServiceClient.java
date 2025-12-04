package com.project.order_service.clients;

import com.project.order_service.dto.ProductResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProductServiceClient {

    @GetExchange("/product/get/{id}")
    ProductResponse getProductDetails(@PathVariable String id);

}
