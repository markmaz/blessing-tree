package com.blessingtree.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {
    protected ModelMapper modelMapper;

    public BaseService(@Autowired ModelMapper mapper){
        this.modelMapper = mapper;
    }

    protected <S, T> T convertToDTO(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }
}
