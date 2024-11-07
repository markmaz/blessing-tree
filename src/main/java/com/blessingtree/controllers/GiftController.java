package com.blessingtree.controllers;

import com.blessingtree.dto.GiftDTO;
import com.blessingtree.service.GiftService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GiftController extends BaseController{
    private final GiftService giftService;

    public GiftController(@Autowired GiftService giftService){
        this.giftService = giftService;
    }

    @GetMapping("/gifts/{id}")
    public GiftDTO getGiftByID(@PathVariable Long id){
        return giftService.get(id);
    }

    @DeleteMapping("/gifts/{id}")
    public void deleteGift(@PathVariable Long id){
        giftService.deleteGift(id);
    }

    @GetMapping("/gifts/child/{id}")
    public List<GiftDTO> getGiftsForChild(@PathVariable Long id){
        return giftService.getGiftsForChild(id);
    }

    @PutMapping("/gifts/{id}")
    public GiftDTO updateGift(@PathVariable Long id, @RequestBody GiftDTO giftDTO, HttpServletRequest request){
        return giftService.updateGift(id, giftDTO, getLoggedInUser(request));
    }

    @GetMapping("/gifts")
    public List<GiftDTO> getGifts(){
        return giftService.getAllGifts();
    }
}
