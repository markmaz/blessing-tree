package com.blessingtree.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name="addresses")
public class Address {
    public Address() {
    }
    public Address(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="address")
    private String street;

    @Column(name="city")
    private String city;

    @Column(name="state")
    private String state;

    @Column(name="zip")
    private String zip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String address) {
        this.street = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address1)) return false;
        return Objects.equals(id, address1.id) && Objects.equals(street, address1.street) && Objects.equals(city, address1.city) && Objects.equals(state, address1.state) && Objects.equals(zip, address1.zip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, city, state, zip);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", address='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}
