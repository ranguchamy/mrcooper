package com.mrcooper.userservice.controller;


import com.mrcooper.userservice.request.LoginRequest;
import com.mrcooper.userservice.response.ApiResponse;
import com.mrcooper.userservice.response.JwtResponse;
import com.mrcooper.userservice.security.jwt.JwtUtils;
import com.mrcooper.userservice.security.user.MRCooperUserDetails;
import com.mrcooper.userservice.utils.FeedBackMessage;
import com.mrcooper.userservice.utils.UrlMapping;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(UrlMapping.AUTH)
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping(UrlMapping.LOGIN)
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        try{
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateTokenForUser(authentication);
            MRCooperUserDetails userDetails = (MRCooperUserDetails) authentication.getPrincipal();
            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.AUTHENTICATION_SUCCESS, jwtResponse));

        }   catch (DisabledException e){
            log.error("DisabledException: ", e);
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(FeedBackMessage.ACCOUNT_DISABLED,null));
        } catch (AuthenticationException e){
            log.error("AuthenticationException: ", e);

            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), FeedBackMessage.INVALID_PASSWORD));

        }
    }


}
