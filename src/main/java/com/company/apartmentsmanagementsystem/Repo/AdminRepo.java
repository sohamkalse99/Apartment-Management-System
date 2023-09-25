package com.company.apartmentsmanagementsystem.Repo;

import com.company.apartmentsmanagementsystem.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepo extends JpaRepository<Admin, Integer> {

    @Query("SELECT a from Admin a where a.username = ?1")
    public Admin findByUsername(String username);
}

