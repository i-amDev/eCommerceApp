package com.project.order_service.dto;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private UserRole role;

    private AddressDto address;
}
