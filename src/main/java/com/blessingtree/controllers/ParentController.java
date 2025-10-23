package com.blessingtree.controllers;

import com.blessingtree.dto.CountDTO;
import com.blessingtree.dto.FamilyNoteDTO;
import com.blessingtree.dto.ParentDTO;
import com.blessingtree.service.ParentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@RestController
public class ParentController extends BaseController{
    private final ParentService parentService;

    public ParentController(@Autowired ParentService parentService){
        this.parentService = parentService;
    }

    @PostMapping("/parents")
    public ParentDTO createParent(@RequestBody ParentDTO parentDTO, HttpServletRequest servletRequest){
        return parentService.saveParent(parentDTO, getLoggedInUser(servletRequest));
    }

    @GetMapping("/parents/active")
    public List<ParentDTO> getAllActiveParents(){
        return parentService.getParents();
    }

    @GetMapping("/parents")
    public List<ParentDTO> getAllParents(){
        return parentService.getAllParents();
    }

    @GetMapping("/parents/{id}")
    public ParentDTO findParentByID(@PathVariable Long id){
        return parentService.findParentByID(id);
    }

    @GetMapping("/parents/btid/{id}")
    public ParentDTO findParentByBT_ID(@PathVariable String id){
        return parentService.findParentByBTID(id);
    }

    @DeleteMapping("/parents/{id}")
    public void deleteParent(@PathVariable Long id){
        parentService.deleteParent(id);
    }

    @PatchMapping("/parents/{id}")
    public ParentDTO patchParentUpdate(@PathVariable Long id, @RequestBody Map<String, Object> updates, HttpServletRequest request){
        return parentService.patchUpdateParent(id, updates, getLoggedInUser(request));
    }

    @PutMapping("/parents/{id}")
    public ParentDTO parentUpdate(@PathVariable Long id, @RequestBody ParentDTO parentDTO, HttpServletRequest request){
        return parentService.updateParent(id, parentDTO, getLoggedInUser(request));
    }

    @PostMapping("/parents/{id}/notes")
    public ParentDTO addNoteToParent(@PathVariable Long id, @RequestBody FamilyNoteDTO note, HttpServletRequest request){
        return parentService.addNote(id, note, getLoggedInUser(request));
    }

    @DeleteMapping("/parents/{id}/notes/{note_id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id, @PathVariable Long note_id){
        return ResponseEntity.status(parentService.deleteNote(note_id)).build();
    }

    @GetMapping("/parents/count")
    public CountDTO getCount(){
        return new CountDTO(parentService.getCount());
    }

    @GetMapping("/parents/unsponsored_children")
    public List<ParentDTO> getUnsponsoredChildren(){
        return parentService.findUnsponsoredChildren();
    }

    @GetMapping("/parents/rename")
    public void renameTheChildren(){
        parentService.renameAllTheChildren();
    }
}
