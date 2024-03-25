package com.bell.takehome.service;

import com.bell.takehome.client.IssLocationClient;
import com.bell.takehome.client.MediaWikiClient;
import com.bell.takehome.exception.NotFoundPlaceException;
import com.bell.takehome.model.MediaWikiResponse;
import com.bell.takehome.model.Place;
import com.bell.takehome.model.PlacesResponse;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IssPlaceService {
    private static final String ACTION = "query";
    private static final String LIST = "geosearch";
    private static final String FORMAT = "json";
    private static final int GSLIMIT = 10;

    private static final String GSPROP = "country";

    private final IssLocationClient issLocationClient;

    private final MediaWikiClient mediaWikiClient;

    public IssPlaceService(IssLocationClient issLocationClient, MediaWikiClient mediaWikiClient) {
        this.issLocationClient = issLocationClient;
        this.mediaWikiClient = mediaWikiClient;
    }

    public PlacesResponse getNearbyPlaces() {

        var location = issLocationClient.getCurrentLocation();
        if (location == null || location.iss_position() == null) throw new NotFoundPlaceException("Current location of ISS not found.");

        var gscoord = location.iss_position().latitude() + "|" + location.iss_position().longitude();
        //var gscoord = "40.712776" + "|" + "-74.005974";
        var response = mediaWikiClient.getNearbyPlaces(ACTION, LIST, FORMAT, gscoord, GSLIMIT, GSPROP);

        var places = transformResponse(response);
        return new PlacesResponse(places);
    }

    private List<Place> transformResponse(MediaWikiResponse response) {
        if (response == null || response.query() == null || response.query().geosearch() == null) {
            throw new NotFoundPlaceException("No Tagged post were found.");
        }

        return response.query().geosearch().stream()
                .map(place -> new Place(place.title(),
                        place.lat(),
                        place.lon(),
                        place.country()))
                .toList();
    }

}
