package com.skyapi.weatherforecast.location;


import com.skyapi.weatherforecast.common.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class LocationService {


    @Autowired
    private LocationRepository repo;

    public LocationService(LocationRepository repo) {
        this.repo = repo;
    }

    public Location add(Location location) {

        return repo.save(location);
    }

    public List<Location> list() {
        List<Location> locations = repo.findUntrashed();
        return locations;
    }

    public Location get(String code) {
        return repo.findByCode(code);
    }

    public Location update(Location locationInRequest) throws LocationNotFoundException {
        String code = locationInRequest.getCode();

        Location locationInDB = repo.findByCode(code);

        if (locationInDB == null) {
            throw new LocationNotFoundException("No location found with the given code: " + code);
        }
        locationInDB.setCode(locationInRequest.getCode());
        locationInDB.setCityName(locationInRequest.getCityName());
        locationInDB.setRegionName(locationInRequest.getRegionName());
        locationInDB.setCountryCode(locationInRequest.getCountryCode());
        locationInDB.setCountryName(locationInRequest.getCountryName());
        locationInDB.setEnabled(locationInRequest.isEnabled());

        return repo.save(locationInDB);
    }

    public void delete(String code) throws LocationNotFoundException {
        Location location = repo.findByCode(code);
        if (location == null) {
            throw new LocationNotFoundException("No Such Location Not found");
        }
        repo.trashedByCode(code);



    }
}
