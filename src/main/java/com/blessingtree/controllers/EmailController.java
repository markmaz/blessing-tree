package com.blessingtree.controllers;

import com.blessingtree.dto.EmailDTO;
import com.blessingtree.service.EmailService;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController extends BaseController{
    private final EmailService emailService;

    public EmailController(@Autowired EmailService emailService){
        this.emailService = emailService;
    }

    @PostMapping("/sponsorEmail")
    public ResponseEntity<?> parseEmail(@RequestBody EmailDTO emailDTO){
        return ResponseEntity.status(emailService.parseBodyAndCreateSponsor(emailDTO)).build();
    }

}

