package com.blessingtree.service;

import com.blessingtree.components.JwtTokenUtil;
import com.blessingtree.dto.UserDTO;
import com.blessingtree.exceptions.ResourceNotFoundException;
import com.blessingtree.model.User;
import com.blessingtree.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final JwtTokenUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserService(
                       @Autowired JwtTokenUtil jwtUtil,
                       @Autowired PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByUsername(username);

        if (userOptional.isPresent()) {
            User user =  userOptional.get();
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("USER"));

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }else{
            throw new UsernameNotFoundException("User not found with this username.");
        }
    }

    public User getUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByUsername(username);

        if (userOptional.isPresent()) {
            return userOptional.get();
        }else{
            throw new UsernameNotFoundException("User not found with this username.");
        }
    }

    public UserDTO createUser(UserDTO userDTO, User loggedInUser){
        User user = modelMapper.map(userDTO, User.class);
        String password = user.getPassword();
        user.setPassword(this.passwordEncoder.encode(password));
        user.setModifiedBy(loggedInUser);

        Timestamp timestamp = Timestamp.from(Instant.now());
        user.setModifiedDate(timestamp.toString());
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    public UserDTO patchUpdateUser(Long id, Map<String, Object> updates, User loggedInUser){
        User user = findUserById(id);

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(User.class, key);

            if(key.equalsIgnoreCase("password")){
                value = this.passwordEncoder.encode(value.toString());
            }

            if(field != null){
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, value);
            }
        });

        user.setModifiedBy(loggedInUser);
        Timestamp timestamp = Timestamp.from(Instant.now());
        user.setModifiedDate(timestamp.toString());

        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    public User findUserById(Long id){
        return userRepository.findUserByUserId(id).orElseThrow(() -> new ResourceNotFoundException("User Not found"));
    }

    public List<UserDTO> getAllUsers(){
        return userRepository.findAll(Sort.by("username"))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
