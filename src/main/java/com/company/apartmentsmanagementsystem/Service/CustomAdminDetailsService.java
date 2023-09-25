package com.company.apartmentsmanagementsystem.Service;

import com.company.apartmentsmanagementsystem.CustomAdminDetails;
import com.company.apartmentsmanagementsystem.Repo.AdminRepo;
import com.company.apartmentsmanagementsystem.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomAdminDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepo adminRepo;

    public List<Admin> adminList(){
        return adminRepo.findAll();
    }

    public Admin getAdminDetails(int adminId){

        return adminRepo.findAll().get(adminId);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin = adminRepo.findByUsername(username);
        if(admin == null){
            throw new UsernameNotFoundException("Admin not found");
        }
        return new CustomAdminDetails(admin);
    }

}
