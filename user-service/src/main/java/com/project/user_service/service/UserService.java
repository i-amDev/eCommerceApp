package com.project.user_service.service;

import com.project.user_service.dto.AddressDto;
import com.project.user_service.dto.UserRequest;
import com.project.user_service.dto.UserResponse;
import com.project.user_service.entity.Address;
import com.project.user_service.entity.User;
import com.project.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(UserRequest userRequest) {
        User user = new User();
        User entity = mapToUser(user, userRequest);
        userRepository.save(entity);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .toList();
    }

    public UserResponse getUser(Long id) {
        return mapToUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!")));
    }

    public boolean updateUser(Long id, UserRequest userRequest) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    mapToUser(existingUser, userRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDto address = new AddressDto();
            address.setCity(user.getAddress().getCity());
            address.setState(user.getAddress().getState());
            address.setCountry(user.getAddress().getCountry());
            address.setStreet(user.getAddress().getStreet());
            address.setZipCode(user.getAddress().getZipCode());
            response.setAddress(address);
        }

        return response;
    }

    private User mapToUser(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());

        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setStreet(userRequest.getAddress().getStreet());
            address.setZipCode(userRequest.getAddress().getZipCode());
            user.setAddress(address);
        }

        return user;
    }



}
