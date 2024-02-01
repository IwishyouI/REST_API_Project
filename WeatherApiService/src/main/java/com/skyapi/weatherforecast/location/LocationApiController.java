package com.skyapi.weatherforecast.location;


import com.skyapi.weatherforecast.common.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<?> listLocation() {

        List<Location> list = service.list();
        if (list.isEmpty()) {
           return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);

    }



}
