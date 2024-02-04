package com.skyapi.weatherforecast.location;


import com.skyapi.weatherforecast.common.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/v1/locations")
public class LocationApiController {


    @Autowired
    private LocationService service;

    public LocationApiController(LocationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Location> addLocation(@RequestBody @Valid Location location) {
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

    @GetMapping("/{code}")
    public ResponseEntity<?> getLocation(@PathVariable("code") String code) {

        Location location = service.get(code);
        System.out.println(location);
        if (location == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(location);
    }

    @PutMapping
    public ResponseEntity<?> updateLocation(@RequestBody @Valid Location location) {
        try {
            Location updatedLocation = service.update(location);

            return ResponseEntity.ok(updatedLocation);
        } catch (LocationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
