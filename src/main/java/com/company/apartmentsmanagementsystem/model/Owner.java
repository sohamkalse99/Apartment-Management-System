package com.company.apartmentsmanagementsystem.model;

import javax.lang.model.element.Name;
import javax.persistence.*;

@Entity
@Table(name = "apartment_info")
public class Owner {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apartment_id")
    private int apartment_id;

    @Column(name = "apartment_number")
    private int apartmentNumber;

    @Column(length = 8, nullable = false, name = "parking_number")
    private int parking_num;

    @Column(name = "build_id", nullable = false)
    private int build_id;

    @Column(length = 40, nullable = false, name="first_name")
    private String firstname;

    @Column(length = 40, nullable = false, name="last_name")
    private String lastname;

    @Column(nullable = false, unique = true, length = 40, name = "email_id")
    private String email;

    @Column(name = "bills")
    private int bills;

    public int getApartment_id() {
        return apartment_id;
    }

    public void setApartment_id(int apartment_id) {
        this.apartment_id = apartment_id;
    }

    public int getParking_num() {
        return parking_num;
    }

    public void setParking_num(int parking_num) {
        this.parking_num = parking_num;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getBills() {
        return bills;
    }

    public void setBills(int bills) {
        this.bills = bills;
    }

    public int getBuild_id() {
        return build_id;
    }

    public void setBuild_id(int build_id) {
        this.build_id = build_id;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

}
