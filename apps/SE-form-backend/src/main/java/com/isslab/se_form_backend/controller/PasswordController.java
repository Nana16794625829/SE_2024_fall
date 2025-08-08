package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.model.ChangePasswordRequest;
import com.isslab.se_form_backend.model.ForgetPasswordRequest;
import com.isslab.se_form_backend.model.ForgetPasswordResponse;
import com.isslab.se_form_backend.model.ResetPasswordRequest;
import com.isslab.se_form_backend.service.impl.PasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/password")
public class PasswordController {

    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @PostMapping("/change")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest req,
            Authentication authentication
    ) {
        String username = authentication.getName();
        log.info("username: {}", username);
        String oldPassword = req.getOldPassword();
        log.info("old password: " + oldPassword);
        String newPassword = req.getNewPassword();
        boolean valid = passwordService.checkPassword(username, oldPassword);

        if(valid) {
            passwordService.changePassword(username, newPassword);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest req,
            @RequestHeader("Authorization") String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token validation failed");
        }
        String token = authHeader.substring(7);
        String newPassword = req.getNewPassword();
        passwordService.resetPassword(token, newPassword);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/forget")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> forgetPassword(@RequestBody ForgetPasswordRequest req) {
        String username = req.getUsername();
        ForgetPasswordResponse res = passwordService.getResetEmailInfo(username);

        return new ResponseEntity<>(ResponseEntity.ok(res), HttpStatus.OK);
    }
}
