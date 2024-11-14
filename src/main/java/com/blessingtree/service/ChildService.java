package com.blessingtree.service;

import com.blessingtree.dto.ChildDTO;
import com.blessingtree.exceptions.ResourceNotFoundException;
import com.blessingtree.model.Child;
import com.blessingtree.model.Parent;
import com.blessingtree.model.User;
import com.blessingtree.repository.ChildRepository;
import com.blessingtree.repository.ParentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChildService extends BaseService {
    private final ChildRepository childRepository;
    private final ParentRepository parentRepository;

    public ChildService(@Autowired ModelMapper mapper,
                        @Autowired ChildRepository childRepository,
                        @Autowired ParentRepository parentRepository) {
        super(mapper);
        this.childRepository = childRepository;
        this.parentRepository = parentRepository;
    }

    public List<ChildDTO> findChildrenByParentID(Long id){
        return childRepository.findAllByParentId(id)
                .stream()
                .map(child -> convertToDTO(child, ChildDTO.class))
                .collect(Collectors.toList());
    }

    public ChildDTO saveChild(ChildDTO childDTO, Long parentID, User loggedInUser){
        Child child = modelMapper.map(childDTO, Child.class);
        Optional<Parent> parentOption = parentRepository.findParentById(parentID);

        if(parentOption.isPresent()){
            child.setParent(parentOption.get());
            Timestamp timestamp = Timestamp.from(Instant.now());
            child.setModifiedDate(timestamp.toString());
            child.setModifiedBy(loggedInUser);

            return modelMapper.map(childRepository.save(child), ChildDTO.class);
        }else {
            throw new ResourceNotFoundException("Parent not found: " + parentID);
        }
    }

    public void deleteChild(Long id){
        childRepository.deleteById(id);
    }

    public ChildDTO updateChild(Long childID, ChildDTO childDTO, Long parentID, User user){
        Child child = childRepository.findById(childID).orElseThrow(() -> new EntityNotFoundException("Child Not found"));
        Timestamp timestamp = Timestamp.from(Instant.now());
        child.setModifiedDate(timestamp.toString());
        child.setModifiedBy(user);

        child.setAge(childDTO.getAge());
        child.setGender(childDTO.getGender());

        return modelMapper.map(childRepository.save(child), ChildDTO.class);

    }

    public Long getCount(){
        return childRepository.count();
    }
}
