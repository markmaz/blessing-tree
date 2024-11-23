package com.blessingtree.service;

import com.blessingtree.dto.FamilyNoteDTO;
import com.blessingtree.dto.ParentDTO;
import com.blessingtree.exceptions.ResourceNotFoundException;
import com.blessingtree.model.FamilyNote;
import com.blessingtree.model.Parent;
import com.blessingtree.model.User;
import com.blessingtree.repository.FamilyNoteRepository;
import com.blessingtree.repository.ParentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.http.HttpStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParentService extends BaseService{
    private final ParentRepository parentRepository;

    private final FamilyNoteRepository noteRepository;

    public ParentService(@Autowired  ParentRepository parentRepository,
                         @Autowired ModelMapper mapper,
                         @Autowired FamilyNoteRepository noteRepository){
        super(mapper);
        this.parentRepository = parentRepository;
        this.noteRepository = noteRepository;
    }

    public List<ParentDTO> getParents(){
        return parentRepository.findAll(Sort.by("lastName"))
                .stream()
                .map(parent -> convertToDTO(parent, ParentDTO.class))
                .collect(Collectors.toList());
    }

    public ParentDTO saveParent(ParentDTO parentDTO, User loggedInUser){
        Parent parent = modelMapper.map(parentDTO, Parent.class);
        parent.setModifiedBy(loggedInUser);
        Timestamp timestamp = Timestamp.from(Instant.now());
        parent.setModifiedDate(timestamp.toString());

        if(parent.getChildren() != null) {
            parent.getChildren().forEach(child -> child.setModifiedBy(loggedInUser));
            parent.getChildren().forEach(child -> child.setModifiedDate(timestamp.toString()));
        }

        return modelMapper.map(parentRepository.save(parent), ParentDTO.class);
    }

    public ParentDTO findParentByBTID(String bt_id){
        Optional<Parent> parent = parentRepository.findParentByBtid(bt_id);

        if(parent.isPresent()){
            return modelMapper.map(parent.get(), ParentDTO.class);
        }else{
            throw new ResourceNotFoundException("Parent not found for: " + bt_id);
        }

    }

    public ParentDTO findParentByID(Long id){
        Optional<Parent> parent = parentRepository.findParentById(id);

        if(parent.isPresent()){
            return modelMapper.map(parent.get(), ParentDTO.class);
        }else{
            throw new ResourceNotFoundException("Parent not found for: " + id);
        }
    }

    public void deleteParent(Long id){
        parentRepository.deleteById(id);
    }

    public ParentDTO patchUpdateParent(Long id, Map<String, Object> updates, User loggedInUser){
        Optional<Parent> optionalParent = parentRepository.findParentById(id);
        if(optionalParent.isPresent()){
            Parent parent = optionalParent.get();
            updates.forEach((key, value) -> {
                if(!key.equals("id")) {

                    Field field = ReflectionUtils.findField(Parent.class, key);

                    if (field != null) {
                        field.setAccessible(true);
                        ReflectionUtils.setField(field, parent, value);
                    }
                }
            });

            parent.setModifiedBy(loggedInUser);
            Timestamp timestamp = Timestamp.from(Instant.now());
            parent.setModifiedDate(timestamp.toString());

            return modelMapper.map(parentRepository.save(parent), ParentDTO.class);
        }else{
            throw new ResourceNotFoundException("Could not find Parent ID: " + id);
        }
    }

    @Transactional
    public ParentDTO updateParent(Long id, ParentDTO parentDTO, User user){
        Parent parent = parentRepository.findParentById(id).orElseThrow(() -> new EntityNotFoundException("Parent Not found"));
        parent.setBtid(parentDTO.getBtid());
        parent.setMHID(parentDTO.getMhid());
        parent.setFirstName(parentDTO.getFirstName());
        parent.setLastName(parentDTO.getLastName());
        parent.setModifiedBy(user);
        Timestamp timestamp = Timestamp.from(Instant.now());
        parent.setModifiedDate(timestamp.toString());

        return convertToDTO(parentRepository.save(parent), ParentDTO.class);
    }

    public ParentDTO addNote(Long parentID, FamilyNoteDTO note, User user){
        Parent parent = parentRepository.findParentById(parentID).orElseThrow(() -> new EntityNotFoundException("Parent Not found"));
        FamilyNote familyNote = modelMapper.map(note, FamilyNote.class);
        familyNote.setParent(parent);
        parent.getNotes().add(familyNote);

        return convertToDTO(parentRepository.save(parent), ParentDTO.class);
    }

    public int deleteNote(Long noteID){
        noteRepository.deleteById(noteID);
        return HttpStatus.SC_OK;
    }

    public List<ParentDTO> findUnsponsoredChildren(){
        List<Parent> parents = parentRepository.findUnsponsoredChildren();

        parents.forEach(parent ->
                parent.getChildren().forEach(child -> child.getGifts().clear())
        );

        return parents
                .stream()
                .map(parent -> convertToDTO(parent, ParentDTO.class))
                .collect(Collectors.toList());
    }


    public Long getCount(){
        return parentRepository.count();
    }
}
