package com.blessingtree.controllers;

import com.blessingtree.dto.SeniorDTO;
import com.blessingtree.service.SeniorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeniorController extends BaseController{
    private final SeniorService seniorService;

    public SeniorController(@Autowired SeniorService seniorService){
        this.seniorService = seniorService;
    }

    @GetMapping("/seniors")
    public List<SeniorDTO> getAllSeniors(){
        return seniorService.getAllSeniors();
    }
}
