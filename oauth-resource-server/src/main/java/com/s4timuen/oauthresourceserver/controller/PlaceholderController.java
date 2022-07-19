package com.s4timuen.oauthresourceserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@RestController
@RequestMapping(path = "/api/v1")
public class PlaceholderController {

    /**
     * API endpoints for placeholder.
     */
    @GetMapping("/placeholder")
    public String[] placeholder() {
        return new String[]{"placeholder", "placeholder", "placeholder"};
    }
}
