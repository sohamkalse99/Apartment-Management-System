package com.company.apartmentsmanagementsystem.Controller;

import com.company.apartmentsmanagementsystem.CustomAdminDetails;
import com.company.apartmentsmanagementsystem.Repo.OwnerRepo;
import com.company.apartmentsmanagementsystem.Service.BuildingServices;
import com.company.apartmentsmanagementsystem.Service.CustomAdminDetailsService;
import com.company.apartmentsmanagementsystem.Service.OwnerService;
import com.company.apartmentsmanagementsystem.model.Building;
import com.company.apartmentsmanagementsystem.model.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;

@Controller
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private CustomAdminDetailsService adminService;

    @Autowired
    private BuildingServices buildingServices;

    @GetMapping("/login")
    public String LoginPage(){
        Authentication authObj = SecurityContextHolder.getContext().getAuthentication();
        if(authObj==null || authObj instanceof AnonymousAuthenticationToken){
            return "login";
        }
        else {
            return "redirect:/";
        }
    }

    @GetMapping("/apartments")
    public String showList(Model model){
        Authentication authObj = SecurityContextHolder.getContext().getAuthentication();
        if(authObj==null || authObj instanceof AnonymousAuthenticationToken){
            return "redirect:/login?error=true";
        }
        else {
            CustomAdminDetails admin=(CustomAdminDetails)authObj.getPrincipal();
            int adminId = admin.getAdminId();
            

            //adminService.getAdminDetails(adminId);
            try{
                Building building = buildingServices.getBuildingDetailsOfAdmin(adminId);
                List<Owner> owners= ownerService.getApartmentDetailsById(building.getBuild_id());
                owners.sort(new Comparator<Owner>() {
                    @Override
                    public int compare(Owner o1, Owner o2) {
                        return o1.getApartmentNumber()-o2.getApartmentNumber();
                    }
                });
                model.addAttribute("OwnerLists", owners);
                return "apartments";
            }catch (NullPointerException e){
                System.out.println("No Building Id present for Admin Id in building_info table");
                return "redirect:/login?error=true";
            }


//            List<Owner> owners = ownerService.Ownerlist();
        }
    }

    @GetMapping("/apartments/add")
    public String createApartment(Model model){
        model.addAttribute("owner", new Owner());
        return "add_apartment";
    }

    @GetMapping("/apartments/edit/buildId/{buildId}/apartmentNumber/{apartmentNumber}")
    public String editApartment(@PathVariable("buildId") int buildId, @PathVariable("apartmentNumber") int apartmentNumber,Model model){

        try{
            Owner owner = ownerService.getOwnerDetails(apartmentNumber, buildId);
            model.addAttribute("owner", owner);
            return "editApartment";
        }catch (UsernameNotFoundException e){
            System.out.println(e);
            return "redirect:/apartments";
        }

    }

    @GetMapping("/apartments/delete/buildId/{buildId}/apartmentNumber/{apartmentNumber}")
    public String deleteApartment( @PathVariable("buildId") int buildId, @PathVariable("apartmentNumber") int apartmentNumber){
        ownerService.deleteApartment(buildId, apartmentNumber);

        return "redirect:/apartments";
    }

    @GetMapping("/apartments/email/buildId/{buildId}/apartmentNumber/{apartmentNumber}")
    public String sendEmail(@PathVariable("buildId") int buildId, @PathVariable("apartmentNumber") int apartmentNumber, RedirectAttributes redirectAttributes){
        Owner owner = ownerService.getOwnerDetails(apartmentNumber, buildId);
        ownerService.sendEmail(owner);
        redirectAttributes.addFlashAttribute("message", "Email Successfully sent");
        return "redirect:/apartments";
    }

    @GetMapping("/apartments/search")
    public String searchApartment(){
        return "searchApartment";
    }


    @PostMapping("/apartments/search")
    public String searchApartmentPost(@RequestParam("firstname") String keyword, Model model){
        keyword = keyword.substring(0, keyword.indexOf(","));
        Authentication authObj = SecurityContextHolder.getContext().getAuthentication();

        CustomAdminDetails admin = (CustomAdminDetails) authObj.getPrincipal();
        int adminId = admin.getAdminId();

        Building building = buildingServices.getBuildingDetailsOfAdmin(adminId);

        List<Owner> owners= ownerService.getApartmentDetailsById(building.getBuild_id());

        List<Owner> specificOwners= ownerService.getOwnerListByKeyword(owners, keyword);

        model.addAttribute("OwnerLists", specificOwners);

        return "searchApartment";
    }
    @PostMapping("/apartments/update")
    public String UpdateApartmentDetails(Owner owner){
        ownerService.updateData(owner);
        return "redirect:/apartments";
    }


    @PostMapping("/apartments/save")
    public String saveApartmentDetails(Owner owner){
        Authentication authObj = SecurityContextHolder.getContext().getAuthentication();
        if(authObj==null || authObj instanceof AnonymousAuthenticationToken){
            return "redirect:/login?error=true";
        }
        else {
            CustomAdminDetails admin = (CustomAdminDetails) authObj.getPrincipal();
            int adminId = admin.getAdminId();

            Building building = buildingServices.getBuildingDetailsOfAdmin(adminId);
            List<Owner> owners= ownerService.getApartmentDetailsById(building.getBuild_id());


            //adminService.getAdminDetails(adminId);
            try {
//                Building building = buildingServices.getBuildingDetailsOfAdmin(adminId);
                for(Owner o: owners){
                    if(o.getApartmentNumber() == owner.getApartmentNumber() || o.getParking_num() == owner.getParking_num()){

                        return "redirect:/apartments/add?error=true";
                    }
                }

                if(owner.getBuild_id() == adminId){
                    ownerService.saveData(owner);
                    return "redirect:/apartments";

                }
            }catch (Exception e){
                System.out.println("Not the right admin for the building");
            }
        }
        return "redirect:/apartments/add?error=true";
    }

}
