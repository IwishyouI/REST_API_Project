package com.skyapi.weatherforecast.common;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locations")
public class Location {


    @Column(length = 12, nullable = false, unique = true)
    private String code;

    @Column(length = 128, nullable = false)
    private String cityName;

    @Column(length = 128, nullable = false)
    private String regionName;

    @Column(length = 64, nullable = false)
    private String countryName;

    @Column(length = 2, nullable = false)
    private String countryCode;

    private boolean enabled;
    private boolean trashed;



}
