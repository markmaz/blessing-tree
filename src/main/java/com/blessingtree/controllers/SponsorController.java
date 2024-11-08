package com.blessingtree.controllers;

import com.blessingtree.dto.SponsorDTO;
import com.blessingtree.service.SponsorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SponsorController extends BaseController{
    private final SponsorService sponsorService;

    public SponsorController(@Autowired SponsorService sponsorService){
        this.sponsorService = sponsorService;
    }

    @GetMapping("/sponsors")
    public List<SponsorDTO> getAllSponsors(){
        return sponsorService.getAllSponsors();
    }

    @GetMapping("/sponsors/{id}")
    public SponsorDTO getSponsorByID(@PathVariable Long id){
        return sponsorService.findSponsor(id);
    }

    @PostMapping("/sponsors")
    public SponsorDTO createSponsor(@RequestBody SponsorDTO sponsorDTO, HttpServletRequest request){
        return sponsorService.saveSponsor(sponsorDTO, getLoggedInUser(request));
    }

    @PutMapping("/sponsors/{id}")
    public SponsorDTO updateSponsor(@PathVariable Long id, @RequestBody SponsorDTO sponsorDTO, HttpServletRequest request){
        return sponsorService.updateSponsor(id, sponsorDTO, getLoggedInUser(request));
    }

    @DeleteMapping("/sponsors/{id}")
    public void deleteSponsor(@PathVariable Long id){
        sponsorService.deleteSponsor(id);
    }
}

