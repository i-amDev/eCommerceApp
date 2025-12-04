package com.project.order_service.clients;

import com.project.order_service.dto.UserResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserServiceClient {

    @GetExchange("/user/get/{id}")
    UserResponse getUserDetails(@PathVariable String id);

}
