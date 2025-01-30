package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;


    @NotBlank
    @Size(min = 5, message = "Building must contain at least 5 characters")
    private String buildingName;

    @NotBlank
    @Size(min = 5, message = "Street must contain at least 5 characters")
    private String street;

    @NotBlank
    @Size(min = 5, message = "City must contain at least 5 characters")
    private String city;

    @NotBlank
    @Size(min = 2, message = "state must contain at least 5 characters")
    private String state;

    @NotBlank
    @Size(min = 5, message = "Country code must contain at least 5 characters")
    private String country;

    @NotBlank
    @Size(min = 5, message = "Postal code must contain at least 5 characters")
    private String postalCode;

    @ToString.Exclude
    @ManyToMany(mappedBy="addresses")
    private List<User> users = new ArrayList<>();

    public Address(String buildingName, String street, String city, String state, String country, String postalCode) {
        this.buildingName = buildingName;
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
    }
}
