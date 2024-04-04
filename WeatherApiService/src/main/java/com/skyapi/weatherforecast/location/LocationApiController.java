package com.skyapi.weatherforecast.location;


import com.skyapi.weatherforecast.common.Location;
import com.skyapi.weatherforecast.common.RealTimeWeather;
import com.skyapi.weatherforecast.realtime.RealTimeWeatherDTO;
import org.hibernate.id.uuid.LocalObjectUuidHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1/locations")
public class LocationApiController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LocationService service;

    public LocationApiController(LocationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LocationDTO> addLocation(@RequestBody @Valid Location location) {
        Location addLocation = service.add(location);
        URI uri = URI.create("/v1/locations/" + location.getCode());

        return ResponseEntity.created(uri).body(entity2Dto(addLocation));
    }

    @GetMapping
    public ResponseEntity<?> listLocation() {

        List<Location> list = service.list();
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listEntity2Dto(list));

    }


    @GetMapping("/{code}")
    public ResponseEntity<?> getLocation(@PathVariable("code") String code) {

        Location location = service.get(code);
        System.out.println(location);
        if (location == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(entity2Dto(location));
    }

    @PutMapping
    public ResponseEntity<?> updateLocation(@RequestBody @Valid Location location) {
        try {
            Location updatedLocation = service.update(location);

            return ResponseEntity.ok(entity2Dto(updatedLocation));
        } catch (LocationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteLocation(@PathVariable String code)  {
        try {
            service.delete(code);
            return ResponseEntity.noContent().build();
        } catch (LocationNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    private List<LocationDTO> listEntity2Dto(List<Location> listEntity) {
        return listEntity.stream().map(entity -> entity2Dto(entity)).collect(Collectors.toList());
    }

    private LocationDTO entity2Dto(Location location) {
        return modelMapper.map(location, LocationDTO.class);
    }

    private Location dto2Entity(LocationDTO dto) {
        return modelMapper.map(dto, Location.class);
    }


}
