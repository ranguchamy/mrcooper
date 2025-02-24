package com.mrcooper.userservice.controller;

import com.mrcooper.userservice.utils.EntityConverter;
import com.mrcooper.userservice.dto.UserDto;
import com.mrcooper.userservice.exception.AlreadyExistsException;
import com.mrcooper.userservice.model.User;
import com.mrcooper.userservice.request.RegistrationRequest;
import com.mrcooper.userservice.response.ApiResponse;
import com.mrcooper.userservice.service.user.UserService;
import com.mrcooper.userservice.utils.FeedBackMessage;
import com.mrcooper.userservice.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RequestMapping(UrlMapping.USERS)
@RestController
public class UserController {
    private final UserService userService;
    private final EntityConverter<User, UserDto> entityConverter;


    @PostMapping(UrlMapping.REGISTER_USER)
    public ResponseEntity<ApiResponse> register(@RequestBody RegistrationRequest request) {
        try {
            User theUser = userService.register(request);
            UserDto registeredUser = entityConverter.mapEntityToDto(theUser, UserDto.class);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.CREATE_USER_SUCCESS, registeredUser));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }



}


