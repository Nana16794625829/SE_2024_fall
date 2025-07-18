package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.model.Presenter;
import com.isslab.se_form_backend.service.impl.PresenterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/presenter")
public class PresenterController {

    private final PresenterService presenterService;

    public PresenterController(PresenterService presenterService) {
        this.presenterService = presenterService;
    }

    @PostMapping("/")
    public ResponseEntity<Presenter> addPresenter(@RequestBody Presenter presenter) {
        presenterService.addPresenter(presenter);

        return ResponseEntity.ok(presenter);
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Presenter>> addPresenterBatch(@RequestBody List<Presenter> presenters) {
        presenterService.addPresentersByWeek(presenters);

        return ResponseEntity.ok(presenters);
    }

}
