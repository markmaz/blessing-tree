package com.blessingtree.service;

import com.blessingtree.dto.EmailDTO;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class EmailService extends BaseService{
    private SponsorService sponsorService;

    public EmailService(@Autowired SponsorService sponsorService, @Autowired ModelMapper mapper){
        super(mapper);
        this.sponsorService = sponsorService;
    }

    public int createSponsor(EmailDTO emailDTO){
        String content = emailDTO.getBody();;
        Timestamp timestamp = Timestamp.from(Instant.now());
        String fileName = "output_" + timestamp + ".txt";

        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println(content);
            System.out.println("File written successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return HttpStatus.SC_OK;
    }
}
