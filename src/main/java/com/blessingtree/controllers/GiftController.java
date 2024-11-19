package com.blessingtree.controllers;

import com.blessingtree.dto.*;
import com.blessingtree.service.GiftService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
    public List<ParentDTO> getGifts(){
        return giftService.getGiftsByFamily();
    }

    @GetMapping("/gifts/count")
    public CountDTO getCount(){
        CountDTO countDTO = new CountDTO();
        countDTO.setCount(giftService.getCount());
        return countDTO;
    }

    @GetMapping("/gifts/unsponsored")
    public List<ParentDTO> getUnsponsoredGifts(){
        return giftService.getUnsponsoredGiftsByFamily();
    }

    @GetMapping("/gifts/top-ten")
    public List<TopTenDTO> getTopTen(@RequestParam(defaultValue = "M") String gender,
                                     @RequestParam(defaultValue = "10") String limit){
        return giftService.getTopTen(gender, limit);
    }

    @PostMapping("/gifts/{giftID}/sponsors/{sponsorID}")
    public GiftDTO sponsorGift(@PathVariable Long giftID, @PathVariable Long sponsorID){
        return giftService.sponsorGift(giftID, sponsorID);
    }

    @DeleteMapping("/gifts/{giftID}/sponsors/{sponsorID}")
    public ResponseEntity<?> unsponsorGift(@PathVariable Long giftID, @PathVariable Long sponsorID){
        return ResponseEntity.status(giftService.removeGiftFromSponsor(giftID, sponsorID)).build();
    }
}
