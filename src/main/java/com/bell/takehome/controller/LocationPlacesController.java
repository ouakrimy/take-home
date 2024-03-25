package com.bell.takehome.controller;

import com.bell.takehome.model.PlacesResponse;
import com.bell.takehome.service.IssPlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v0/location")
public class LocationPlacesController {

    private final IssPlaceService issPlaceService;

    public LocationPlacesController(IssPlaceService issPlaceService) {
        this.issPlaceService = issPlaceService;
    }


    @GetMapping
    public ResponseEntity<PlacesResponse> getLocation() {
        PlacesResponse placesResponse = issPlaceService.getNearbyPlaces();
        return ResponseEntity.ok(placesResponse);
    }
}
