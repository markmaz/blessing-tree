package com.blessingtree.service;

import com.blessingtree.dto.SponsorDTO;
import com.blessingtree.model.Address;
import com.blessingtree.model.Sponsor;
import com.blessingtree.model.User;
import com.blessingtree.repository.AddressRepository;
import com.blessingtree.repository.SponsorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SponsorService extends BaseService{
    private final SponsorRepository sponsorRepository;
    private final AddressRepository addressRepository;

    public SponsorService(@Autowired SponsorRepository sponsorRepository,
                          @Autowired ModelMapper mapper,
                          @Autowired AddressRepository addressRepository){
        super(mapper);
        this.sponsorRepository = sponsorRepository;
        this.addressRepository = addressRepository;
    }

    public List<SponsorDTO> getAllSponsors(){
        return sponsorRepository.findAll()
                .stream()
                .map(sponsor -> convertToDTO(sponsor, SponsorDTO.class))
                .collect(Collectors.toList());
    }

    public void deleteSponsor(Long id){
        sponsorRepository.deleteById(id);
    }

    public SponsorDTO saveSponsor(SponsorDTO sponsorDTO, User user){
        Sponsor sponsor = modelMapper.map(sponsorDTO, Sponsor.class);

        Timestamp timestamp = Timestamp.from(Instant.now());
        sponsor.setModifiedDate(timestamp.toString());
        sponsor.setModifiedBy(user);

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
        sponsor.setAddress(modelMapper.map(sponsorDTO.getAddress(), Address.class));
        sponsor.setHasSponsoredPreviously(sponsorDTO.getHasSponsoredPreviously());
        sponsor.setBestTimeToCall(sponsorDTO.getBestTimeToCall());
        sponsor.setEmail(sponsorDTO.getEmail());
        sponsor.setFirstName(sponsorDTO.getFirstName());
        sponsor.setHowDidYouHearAboutUs(sponsorDTO.getHowDidYouHearAboutUs());
        sponsor.setLastName(sponsorDTO.getLastName());
        sponsor.setPhone(sponsorDTO.getPhone());
        sponsor.setWantToVolunteer(sponsorDTO.getWantToVolunteer());

        return modelMapper.map(sponsorRepository.save(sponsor), SponsorDTO.class);
    }

    public Long getCount(){
        return sponsorRepository.count();
    }

    @Transactional
    public SponsorDTO createSponsor(Sponsor sponsor){
        Address sponsorAddress = sponsor.getAddress();
        Address address = addressRepository.findByStreetAndCityAndStateAndZip(sponsorAddress.getStreet(),
                sponsorAddress.getCity(), sponsorAddress.getState(), sponsorAddress.getZip());

        if(address == null){
            address = sponsorAddress;
        }

        sponsor.setAddress(address);
        return  modelMapper.map(sponsorRepository.save(sponsor), SponsorDTO.class);
    }
}
