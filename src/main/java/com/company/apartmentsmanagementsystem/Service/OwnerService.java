package com.company.apartmentsmanagementsystem.Service;

import com.company.apartmentsmanagementsystem.Repo.OwnerRepo;
import com.company.apartmentsmanagementsystem.model.Owner;
import com.sun.mail.smtp.SMTPSaslAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service
public class OwnerService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private OwnerRepo ownerRepo;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public List<Owner> Ownerlist(){
        return (List<Owner>)ownerRepo.findAll();

    }

    public Owner getOwnerDetails(int apartmentNumber, int buildId) throws UsernameNotFoundException{
/*       Optional<Owner> owner = ownerRepo.findById(apartmentNumber);

       if(owner.isPresent()){
           return owner.get();
       }
       throw new UsernameNotFoundException("Cannot find apartment Id "+ apartmentNumber);*/

        Owner owner = ownerRepo.getOwnerDetails(apartmentNumber, buildId);

        return owner;
    }

    public void saveData(Owner owner) {
        ownerRepo.save(owner);
    }

    public void updateData(Owner owner){
        ownerRepo.updateQuery(owner.getBuild_id(), owner.getApartmentNumber(), owner.getBills(), owner.getEmail(), owner.getFirstname(), owner.getLastname(), owner.getParking_num());
    }

    public void deleteApartment(int buildId, int apartmentNumber){
        ownerRepo.deleteOwnerDetails(buildId, apartmentNumber);
    }
    public List<Owner> getApartmentDetailsById(int buildId){
//        return ownerRepo.findAll();
        List<Owner> ownerList = new ArrayList<>();
        for(Owner owner: ownerRepo.findAll()){
            if(owner.getBuild_id() == buildId){
                ownerList.add(owner);
            }
        }

        return ownerList;
    }

    public List<Owner> getOwnerListByKeyword(List<Owner> owners, String keyword){
        List<Owner> ownerList = new ArrayList<>();

        for(Owner owner: owners){
            if(keyword.equals("")){
                ownerList.add(owner);
            } else if (owner.getFirstname().toLowerCase().replaceAll("[^a-zA-Z ]", "").contains(keyword.toLowerCase())) {
                ownerList.add(owner);
            }
        }

        return ownerList;
    }

    public LocalDate generateDate(){
        return LocalDate.now();

    }

    public void sendEmail(Owner owner){

        LocalDate date = generateDate();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(owner.getEmail());
        message.setSubject("Utility Bills, " + date);
        message.setText("Hi "+ owner.getFirstname()+","+System.lineSeparator()+System.lineSeparator()+"Your Utility Bills as of date- "+ date+" are $"+ owner.getBills());
        mailSender.send(message);

    }
}
