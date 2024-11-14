package com.blessingtree.service;

import com.blessingtree.dto.SeniorDTO;
import com.blessingtree.repository.SeniorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeniorService extends BaseService {
    private final SeniorRepository seniorRepository;

    public SeniorService(@Autowired ModelMapper mapper, @Autowired SeniorRepository seniorRepository){
        super(mapper);
        this.seniorRepository = seniorRepository;
    }

    public List<SeniorDTO> getAllSeniors(){
        return seniorRepository.findAll()
                .stream()
                .map(senior -> convertToDTO(senior, SeniorDTO.class))
                .collect(Collectors.toList());
    }

    public Long getCount() {
        return seniorRepository.count();
    }

}
