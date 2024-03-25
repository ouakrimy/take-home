package com.bell.takehome.client;

import com.bell.takehome.model.IssLocationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name= "issLocationClient", url = "${iss-client.url}")
public interface IssLocationClient {

    @GetMapping("/iss-now.json")
    IssLocationResponse getCurrentLocation();
}
