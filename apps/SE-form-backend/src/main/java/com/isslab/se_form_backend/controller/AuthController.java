package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.security.JwtUtil;
import com.isslab.se_form_backend.security.property.AdminAuthProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AdminAuthProperties adminAuthProperties;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, AdminAuthProperties adminAuthProperties) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.adminAuthProperties = adminAuthProperties;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        if (adminAuthProperties.getUsername().equals(req.getUsername()) &&
                adminAuthProperties.getPassword().equals(req.getPassword())) {

            String token = jwtUtil.generateAdminToken();
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        }

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );

            if (Boolean.TRUE.equals(auth.isAuthenticated())) {
                log.info("user: {} 驗證通過", req.getUsername());
                String token = jwtUtil.generateToken(req.getUsername());
                return ResponseEntity.ok(Collections.singletonMap("token", token));
            }
        } catch (Exception e) {
            log.warn("使用者驗證失敗: {}", e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @Getter
    @Setter
    public static class LoginRequest {
        private String username;
        private String password;
        // getters & setters
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();

        // 如果你之後有 custom UserDetails，可以回傳更完整的資料
        return ResponseEntity.ok(
                new MeResponse(username)
        );
    }

    public record MeResponse(String username) {}
}
