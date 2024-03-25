package com.bell.takehome.model;

public record IssLocationResponse(String message, long timestamp, IssPosition iss_position) {

    public record IssPosition(String latitude, String longitude) {}

}