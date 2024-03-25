package com.bell.takehome.client;


import com.bell.takehome.model.MediaWikiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mediaWikiClient", url = "${media-wiki-client.url}")
public interface MediaWikiClient {

    @GetMapping
    MediaWikiResponse getNearbyPlaces(@RequestParam("action") String action,
                                      @RequestParam("list") String list,
                                      @RequestParam("format") String format,
                                      @RequestParam("gscoord") String gscoord,
                                      @RequestParam("gslimit") int gslimit,
                                      @RequestParam("gsprop") String gsprop);
}
