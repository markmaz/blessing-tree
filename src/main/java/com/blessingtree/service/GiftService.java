package com.blessingtree.service;

import com.blessingtree.dto.GiftDTO;
import com.blessingtree.dto.TopGiftDTO;
import com.blessingtree.dto.TopTenDTO;
import com.blessingtree.exceptions.ResourceNotFoundException;
import com.blessingtree.model.Child;
import com.blessingtree.model.Gift;
import com.blessingtree.model.User;
import com.blessingtree.repository.ChildRepository;
import com.blessingtree.repository.GiftRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftService extends BaseService{
    private final GiftRepository giftRepository;
    private final ChildRepository childRepository;
    private final EntityManager entityManager;

    public GiftService(@Autowired ModelMapper mapper,
                       @Autowired GiftRepository giftRepository,
                       @Autowired ChildRepository childRepository,
                       @Autowired EntityManager entityManager) {
        super(mapper);
        this.giftRepository = giftRepository;
        this.childRepository = childRepository;
        this.entityManager = entityManager;
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

    public List<TopGiftDTO> getAllGifts(){
        return giftRepository.findAll()
                .stream()
                .map(gift -> convertToDTO(gift, TopGiftDTO.class))
                .collect(Collectors.toList());
    }

    public Long getCount(){
        return giftRepository.count();
    }

    public Page<TopGiftDTO> getAllUnsponsoredGifts(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Gift> giftPage= giftRepository.findBySponsorIsNull(pageable);

        return giftPage.map(gift -> modelMapper.map(gift, TopGiftDTO.class));
    }

    public List<TopTenDTO> getTopTen(String gender, String limit){
        String jql = "SELECT g.description, count(g) as cnt " +
                "FROM Gift g " +
                "JOIN g.child c " +
                "WHERE c.gender = :gender " +
                "GROUP BY g.description " +
                "ORDER BY cnt DESC";

        // Use setMaxResults to limit the query results, as JPQL doesn't support `LIMIT`
        List<Object[]> results = entityManager.createQuery(jql, Object[].class)
                .setParameter("gender", gender)
                .setMaxResults(Integer.parseInt(limit))
                .getResultList();

        return results
                .stream()
                .map(result -> new TopTenDTO(result[0].toString(), ((Number) result[1]).longValue()))
                .collect(Collectors.toList());
    }
}
