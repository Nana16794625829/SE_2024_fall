package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.model.Presenter;
import com.isslab.se_form_backend.service.PresenterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/presenters")
public class PresenterController {

    private final PresenterService presenterService;

    public PresenterController(PresenterService presenterService) {
        this.presenterService = presenterService;
    }

    @GetMapping("")
    public ResponseEntity<?> getPresenters() {
        List<Presenter> presenters = presenterService.getPresenters();
        return ResponseEntity.ok(presenters);
    }
}
