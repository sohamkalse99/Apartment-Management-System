package com.company.apartmentsmanagementsystem.Repo;

import com.company.apartmentsmanagementsystem.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepo extends JpaRepository<Building, Integer> {
}
