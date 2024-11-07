package com.blessingtree.service;

import com.blessingtree.dto.GiftDTO;
import com.blessingtree.exceptions.ResourceNotFoundException;
import com.blessingtree.model.Child;
import com.blessingtree.model.Gift;
import com.blessingtree.model.User;
import com.blessingtree.repository.ChildRepository;
import com.blessingtree.repository.GiftRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftService extends BaseService{
    private final GiftRepository giftRepository;
    private final ChildRepository childRepository;

    public GiftService(@Autowired ModelMapper mapper,
                       @Autowired GiftRepository giftRepository,
                       @Autowired ChildRepository childRepository) {
        super(mapper);
        this.giftRepository = giftRepository;
        this.childRepository = childRepository;
    }

    public GiftDTO saveGift(Long childID, GiftDTO giftDTO, User loggedInUser){
        Optional<Child> childOptional = childRepository.findById(childID);

        if(childOptional.isPresent()){
            Gift gift = modelMapper.map(giftDTO, Gift.class);
            gift.setChild(childOptional.get());
            gift.setModifiedBy(loggedInUser);
            Timestamp timestamp = Timestamp.from(Instant.now());
            gift.setModifiedDate(timestamp.toString());

            return modelMapper.map(giftRepository.save(gift), GiftDTO.class);
        }else {
            throw new ResourceNotFoundException("Child not found with ID: " + childID);
        }

    }

    public void deleteGift(Long id){
        giftRepository.deleteById(id);
    }

    public GiftDTO get(Long id){
        Optional<Gift> giftOptional = giftRepository.findById(id);

        if(giftOptional.isPresent()){
            return modelMapper.map(giftOptional.get(), GiftDTO.class);
        }else{
            throw new ResourceNotFoundException("Gift not found for id: " + id);
        }
    }

    public List<GiftDTO> getGiftsForChild(Long id){
        Gift gift = new Gift();
        Child child = childRepository.getReferenceById(id);
        gift.setChild(child);

        Example<Gift> example = Example.of(gift);

        return giftRepository.findAll(example)
                .stream()
                .map(g -> convertToDTO(g, GiftDTO.class))
                .collect(Collectors.toList());

    }

    public GiftDTO updateGift(Long id, GiftDTO giftDTO, User user){
        Gift gift = giftRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Gift not found"));
        gift.setModifiedBy(user);
        Timestamp timestamp = Timestamp.from(Instant.now());
        gift.setModifiedDate(timestamp.toString());

        gift.setDescription(giftDTO.getDescription());
        gift.setSize(giftDTO.getSize());
        gift.setStatus(giftDTO.getStatus());

        return modelMapper.map(giftRepository.save(gift), GiftDTO.class);
    }

    public List<GiftDTO> getAllGifts(){
        return giftRepository.findAll()
                .stream()
                .map(gift -> convertToDTO(gift, GiftDTO.class))
                .collect(Collectors.toList());
    }
}
