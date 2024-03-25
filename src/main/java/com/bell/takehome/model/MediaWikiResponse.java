package com.bell.takehome.model;

import java.util.List;

public record MediaWikiResponse(Query query) {

    public record Query(List<Place> geosearch) {}

    public record Place(int pageid, String title, double lat, double lon, String country) {}
}