package com.skyapi.weatherforecast.location;


import com.skyapi.weatherforecast.common.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@Controller
@RequestMapping("/v1/locations")
public class LocationApiController {


    @Autowired
    private LocationService service;

    public LocationApiController(LocationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Location> addLocation(@RequestBody Location location) {
        Location addLocation = service.add(location);
        URI uri = URI.create("/v1/locations/" + location.getCode());

        return ResponseEntity.created(uri).body(addLocation);
    }


}
