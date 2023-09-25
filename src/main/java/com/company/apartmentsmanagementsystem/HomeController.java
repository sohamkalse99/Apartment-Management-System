package com.company.apartmentsmanagementsystem;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(Model model){
        Authentication authObj = SecurityContextHolder.getContext().getAuthentication();
        CustomAdminDetails admin=(CustomAdminDetails)authObj.getPrincipal();
        String adminName = admin.getUsername();
        model.addAttribute("name",adminName);
        return "index";
    }

/*    @GetMapping("")
    public String homePageBlank(Model model){
        Authentication authObj = SecurityContextHolder.getContext().getAuthentication();
        CustomAdminDetails admin=(CustomAdminDetails)authObj.getPrincipal();
        String adminName = admin.getUsername();
        model.addAttribute("name",adminName);
        return "index";
    }*/


}
