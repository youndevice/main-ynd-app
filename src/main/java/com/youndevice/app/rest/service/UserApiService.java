package com.youndevice.app.rest.service;

import com.youndevice.app.domain.Role;
import com.youndevice.app.domain.User;
import com.youndevice.app.repository.RoleRepository;
import com.youndevice.app.repository.UserRepository;
import com.youndevice.app.rest.dto.ApiResponseDTO;
import com.youndevice.app.rest.dto.UserDTO;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@Transactional
public class UserApiService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public ApiResponseDTO<User> saveUser(UserDTO userDTO) {

        User user = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmailId(), new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        HashSet<Role> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findByAuthority("ROLE_USER"));
        user.setRoles(roleSet);
        userRepository.save(user);
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();
        apiResponseDTO.setMessage("User Saved Successfully");
        return apiResponseDTO;
    }

    public User getUserFromEmail(String emailId){
        User user = userRepository.findByEmailId(emailId);
        Hibernate.initialize(user.getRoles());
        return user;
    }

}
