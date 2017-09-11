package com.hiwotab.springboot12.controller;

import com.cloudinary.utils.ObjectUtils;
import com.google.common.collect.Lists;
import com.hiwotab.springboot12.CloudinaryConfig;
import com.hiwotab.springboot12.model.Actor;
import com.hiwotab.springboot12.repositories.ActorRepostory;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    ActorRepostory actorRepostory;
    @Autowired
    CloudinaryConfig cloudinaryConfig;

    @Autowired
    public EmailService emailService;
    public void sendEmailWithoutTemplating() throws UnsupportedEncodingException {
        final Email email= DefaultEmail.builder()
                .from(new InternetAddress("mahifentaye@gmail.com", "Marco Tullio Cicero ne"))
                .to(Lists.newArrayList(new InternetAddress("mymahder@gmail.com","pomponiu s Atticus")))
                .subject("Laelius de amicitia")
                .body("Firmamentum autem stabilitatis constantiaeque eius, quam in amicitia q uaermius, fides est.")
                .encoding("UTF-8").build();
        System.out.println("test it");
        emailService.send(email);
    }


    @RequestMapping("/")
    public String listActors(Model model) throws UnsupportedEncodingException {

        model.addAttribute ("actors",actorRepostory.findAll());
        sendEmailWithoutTemplating();
        return "list";
    }
    @GetMapping("/add")
    public String newActor(Model model) {
        model.addAttribute("actor",new Actor());
        return "form";
    }

    @PostMapping("/add")
    public String processActor(@ModelAttribute Actor actor, @RequestParam("file")MultipartFile multipartFile){
        if(multipartFile.isEmpty()){
            return "redirect:/add";

        }
        try {
            Map uploadResult = cloudinaryConfig.upload(multipartFile.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            actor.setHeadshot(uploadResult.get("url").toString());
            actorRepostory.save(actor);
        }catch(IOException e){
            e.printStackTrace();
            return "redirect:/add";

        }
        return  "redirect:/";
    }
}
