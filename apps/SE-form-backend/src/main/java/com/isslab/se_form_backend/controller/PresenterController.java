package com.isslab.se_form_backend.controller;

import com.isslab.se_form_backend.model.Presenter;
import com.isslab.se_form_backend.service.impl.PresenterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presenter")
@PreAuthorize("hasRole('ADMIN')")
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

    @GetMapping("/{week}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Presenter>> getPresenters(@PathVariable("week") String week) {
        List<Presenter> presenters = presenterService.getPresentersByWeek(week);

        return ResponseEntity.ok(presenters);
    }

}
