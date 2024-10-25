package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.model.Presenter;
import com.isslab.se_form_backend.model.Status;
import com.isslab.se_form_backend.service.impl.PresenterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presenters")
public class PresenterController {

    private final PresenterService presenterService;

    public PresenterController(PresenterService presenterService) {
        this.presenterService = presenterService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePresenter(@RequestBody Presenter presenter) {
        presenterService.savePresenter(presenter);
        Status status = Status.builder().status("200").response("ok").build();
        return ResponseEntity.ok(status);
    }
}
