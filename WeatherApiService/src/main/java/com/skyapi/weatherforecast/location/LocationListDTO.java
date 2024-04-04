package com.skyapi.weatherforecast.location;

import java.util.ArrayList;
import java.util.List;

public class LocationListDTO {

    List<LocationDTO> listLocationDTO = new ArrayList<>();

    LocationDTO locationDTO = new LocationDTO();


    public LocationDTO getLocationDTO() {
        return locationDTO;
    }

    public void setLocationDTO(LocationDTO locationDTO) {
        this.locationDTO = locationDTO;
    }

    public List<LocationDTO> getListLocationDTO() {
        return listLocationDTO;
    }

    public void setListLocationDTO(List<LocationDTO> listLocationDTO) {
        this.listLocationDTO = listLocationDTO;
    }
}
