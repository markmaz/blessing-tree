package com.blessingtree.service;

import com.blessingtree.dto.CallLogDTO;
import com.blessingtree.dto.CountDTO;
import com.blessingtree.dto.SponsorDTO;
import com.blessingtree.model.Address;
import com.blessingtree.model.CallLog;
import com.blessingtree.model.Sponsor;
import com.blessingtree.model.User;
import com.blessingtree.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.weaver.ast.Call;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SponsorService extends BaseService{
    public static final String SYSTEM_USER = "mmaslako";
    private final SponsorRepository sponsorRepository;
    private final AddressRepository addressRepository;
    private final CallLogRepository callLogRepository;
    private final UserRepository userRepository;
    private final GiftService giftService;

    public SponsorService(@Autowired SponsorRepository sponsorRepository,
                          @Autowired ModelMapper mapper,
                          @Autowired AddressRepository addressRepository,
                          @Autowired UserRepository userRepository,
                          @Autowired GiftService giftService,
                          @Autowired CallLogRepository callLogRepository){
        super(mapper);
        this.sponsorRepository = sponsorRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.giftService = giftService;
        this.callLogRepository = callLogRepository;
    }

    public List<SponsorDTO> getAllSponsors(){
        Sort sort = Sort.by("lastName", "firstName").ascending();
        return sponsorRepository.findAll(sort)
                .stream()
                .map(sponsor -> convertToDTO(sponsor, SponsorDTO.class))
                .collect(Collectors.toList());
    }

    public List<SponsorDTO> getAllActiveSponsors(){
        Sort sort = Sort.by("lastName", "firstName").ascending();
        return sponsorRepository.findByActiveTrue(sort)
                .stream()
                .map(sponsor -> convertToDTO(sponsor, SponsorDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteSponsor(Long id){
        Sponsor sponsor = sponsorRepository.findById(id).orElse(null);

        if(sponsor != null){
            sponsor.getGifts().forEach(gift -> {
                giftService.removeGiftFromSponsor(gift.getId(), sponsor.getId());
            });
        }

        sponsorRepository.deleteById(id);
    }

    public SponsorDTO saveSponsor(SponsorDTO sponsorDTO, User user){
        Sponsor sponsor = modelMapper.map(sponsorDTO, Sponsor.class);

        Timestamp timestamp = Timestamp.from(Instant.now());
        sponsor.setModifiedDate(timestamp.toString());
        sponsor.setModifiedBy(user);

        sponsor.getAddress().setModifiedBy(user);
        sponsor.getAddress().setModifiedDate(timestamp.toString());

//        if(sponsor.getSponsorYear() != null){
//            sponsor.getSponsorYear().forEach(sponsorYear -> {
//                sponsorYear.setModifiedBy(user);
//                sponsorYear.setModifiedDate(timestamp.toString());
//            });
//        }


        return modelMapper.map(sponsorRepository.save(sponsor), SponsorDTO.class);
    }

    public SponsorDTO findSponsor(Long id){
        Sponsor sponsor = sponsorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Sponsor not found"));
        return modelMapper.map(sponsor, SponsorDTO.class);
    }

    public SponsorDTO updateSponsor(Long id, SponsorDTO sponsorDTO, User user){
        Sponsor sponsor = sponsorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Sponsor not found"));
        sponsor.setModifiedBy(user);
        Timestamp timestamp = Timestamp.from(Instant.now());
        sponsor.setModifiedDate(timestamp.toString());

        Address address = addressRepository.findById(sponsor.getAddress().getId()).orElse(null);

        if(address == null){
            sponsor.setAddress(modelMapper.map(sponsorDTO.getAddress(), Address.class));
            sponsor.getAddress().setModifiedDate(timestamp.toString());
            sponsor.getAddress().setModifiedBy(user);
        }else{
            address.setZip(sponsorDTO.getAddress().getZip());
            address.setCity(sponsorDTO.getAddress().getCity());
            address.setState(sponsorDTO.getAddress().getState());
            address.setStreet(sponsorDTO.getAddress().getStreet());
            sponsor.setAddress(address);
        }

        sponsor.setHasSponsoredPreviously(sponsorDTO.getHasSponsoredPreviously());
        sponsor.setBestTimeToCall(sponsorDTO.getBestTimeToCall());
        sponsor.setEmail(sponsorDTO.getEmail());
        sponsor.setFirstName(sponsorDTO.getFirstName());
        sponsor.setHowDidYouHearAboutUs(sponsorDTO.getHowDidYouHearAboutUs());
        sponsor.setLastName(sponsorDTO.getLastName());
        sponsor.setPhone(sponsorDTO.getPhone());
        sponsor.setWantToVolunteer(sponsorDTO.getWantToVolunteer());
        sponsor.setActive(sponsorDTO.getActive());

        return modelMapper.map(sponsorRepository.save(sponsor), SponsorDTO.class);
    }

    public Long getCount(){
        return sponsorRepository.count();
    }

    @Transactional
    public SponsorDTO createSponsor(Sponsor sponsor){
        User user = userRepository.findUserByUsername(SYSTEM_USER).orElse(null);
        Timestamp timestamp = Timestamp.from(Instant.now());
        sponsor.setModifiedDate(timestamp.toString());
        sponsor.setModifiedBy(user);

        Address sponsorAddress = sponsor.getAddress();
        Address address = addressRepository.findByStreetAndCityAndStateAndZip(sponsorAddress.getStreet(),
                sponsorAddress.getCity(), sponsorAddress.getState(), sponsorAddress.getZip());

        if(address == null){
            address = sponsorAddress;
            address.setModifiedBy(user);
            address.setModifiedDate(timestamp.toString());
        }

        Address saveAddress = addressRepository.save(address);
        sponsor.setAddress(saveAddress);

        return  modelMapper.map(sponsorRepository.save(sponsor), SponsorDTO.class);
    }

    public Sponsor findSponsor(String email, String firstName, String lastName){
        return sponsorRepository.findByEmailAndFirstNameAndLastName(email, firstName, lastName);
    }

    public CallLogDTO addLogEntry(Long id, CallLogDTO logEntry) {
        Sponsor sponsor = sponsorRepository.findById(id).orElseThrow(() -> new RuntimeException("Missing sponsor"));
        CallLog log = modelMapper.map(logEntry, CallLog.class);
        log.setSponsor(sponsor);

        return modelMapper.map(callLogRepository.save(log), CallLogDTO.class);
    }

    public void removeLogEntry(Long id, Long entryID) {
        callLogRepository.deleteById(entryID);
    }

    public List<SponsorDTO> getTopSponsors(){
        return sponsorRepository.findTop10SponsorsWithMostGifts()
                .stream()
                .map(sponsor -> convertToDTO(sponsor, SponsorDTO.class))
                .collect(Collectors.toList());
    }

    public SponsorDTO updateGiftStatus(Long sponsorID, String status){
        Sponsor sponsor = sponsorRepository.findById(sponsorID).orElseThrow(() -> new RuntimeException("Missing Sponsor"));
        sponsor.setGiftStatus(status);
        return modelMapper.map(sponsorRepository.save(sponsor), SponsorDTO.class);
    }

    public Long getStatusCounts(){
        return this.sponsorRepository.getStatusCounts();
    }
}
