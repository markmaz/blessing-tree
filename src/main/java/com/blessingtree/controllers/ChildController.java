package com.blessingtree.controllers;

import com.blessingtree.dto.ChildDTO;
import com.blessingtree.dto.CountDTO;
import com.blessingtree.dto.GiftDTO;
import com.blessingtree.service.ChildService;
import com.blessingtree.service.GiftService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChildController extends BaseController{
    private final ChildService childService;
    private final GiftService giftService;

    public ChildController(@Autowired ChildService childService,
                           @Autowired GiftService giftService){
        this.childService = childService;
        this.giftService = giftService;
    }

    @GetMapping("/parents/{id}/children")
    public List<ChildDTO> getAllChildrenByParentID(@PathVariable Long id){
        return childService.findChildrenByParentID(id);
    }

    @PostMapping("/parents/{id}/children")
    public ChildDTO saveChild(@PathVariable Long id, @RequestBody ChildDTO childDTO, HttpServletRequest request){
        return childService.saveChild(childDTO, id, getLoggedInUser(request));
    }

    @DeleteMapping("/parents/{id}/children/{child_id}")
    public void deleteChild(@PathVariable Long child_id){
        childService.deleteChild(child_id);
    }

    @PutMapping("/parents/{id}/children/{child_id}")
    public void updateChild(@PathVariable Long child_id, @PathVariable Long id,
                            @RequestBody ChildDTO childDTO, HttpServletRequest request){
        childService.updateChild(child_id, childDTO, id, getLoggedInUser(request));
    }

    @PostMapping("/parents/{id}/children/{child_id}/gifts")
    public GiftDTO addGiftToChild(@PathVariable Long child_id, @RequestBody GiftDTO giftDTO, HttpServletRequest request){
        return giftService.saveGift(child_id, giftDTO, getLoggedInUser(request));
    }

    @GetMapping("/children/count")
    public CountDTO getCount(){
        CountDTO countDTO = new CountDTO();
        countDTO.setCount(childService.getCount());
        return countDTO;
    }
}