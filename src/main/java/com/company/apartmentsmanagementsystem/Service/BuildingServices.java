package com.company.apartmentsmanagementsystem.Service;

import com.company.apartmentsmanagementsystem.Repo.BuildingRepo;
import com.company.apartmentsmanagementsystem.model.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingServices {

    @Autowired private BuildingRepo buildingRepo;

    public Building getBuildingDetailsOfAdmin(int adminId){
        for (Building build : buildingRepo.findAll()){
            if(build.getAdmin_id() == adminId)
                return build;
        }

        return null;
//        return buildingRepo.findAll().get(adminId);

    }
}
