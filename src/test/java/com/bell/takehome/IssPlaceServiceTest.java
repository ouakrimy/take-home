package com.bell.takehome;

import com.bell.takehome.client.IssLocationClient;
import com.bell.takehome.client.MediaWikiClient;
import com.bell.takehome.model.IssLocationResponse;
import com.bell.takehome.model.MediaWikiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class IssPlaceServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssLocationClient issLocationClient;

    @MockBean
    private MediaWikiClient mediaWikiClient;

    @Test
    public void testGetNearbyPlaces_Success() throws Exception {
        // Mock current location of ISS
        IssLocationResponse issLocationResponse = new IssLocationResponse("Success", 123456789L, new IssLocationResponse.IssPosition("40.712776", "-74.005974"));
        when(issLocationClient.getCurrentLocation()).thenReturn(issLocationResponse);

        // Mock response from MediaWikiClient
        MediaWikiResponse mediaWikiResponse = new MediaWikiResponse(new MediaWikiResponse.Query(Collections.singletonList(new MediaWikiResponse.Place(1, "Place 1", 40.712776, -74.005974, "USA"))));
        when(mediaWikiClient.getNearbyPlaces(anyString(), anyString(), anyString(), anyString(), anyInt(), anyString())).thenReturn(mediaWikiResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/v0/location"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.results.length()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].title").value("Place 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].latitude").value(40.712776))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].longitude").value(-74.005974))
                .andExpect(MockMvcResultMatchers.jsonPath("$.results[0].country").value("USA"));
    }
}
