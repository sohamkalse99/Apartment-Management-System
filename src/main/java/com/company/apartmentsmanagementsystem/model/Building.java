package com.company.apartmentsmanagementsystem.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "building_info")
public class Building {

    @Id
    @Column(name = "build_id", nullable = false)
    private int build_id;

    @Column(name = "admin_id", nullable = false)
    private int admin_id;

/*
    @OneToMany(targetEntity = Owner.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "build_id", referencedColumnName = "build_id")
    private List<Owner> ownerList;
*/

    public int getBuild_id() {
        return build_id;
    }

    public void setBuild_id(int build_id) {
        this.build_id = build_id;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }
}
