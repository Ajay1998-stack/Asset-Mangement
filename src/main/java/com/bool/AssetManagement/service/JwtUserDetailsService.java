package com.bool.AssetManagement.service;

import com.bool.AssetManagement.domain.DataAccessObject;
import com.bool.AssetManagement.repository.AuthDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthDetailsRepository authDetailsRepository;
    @Autowired
     private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String regNo) throws UsernameNotFoundException {
//        if ("javainuse".equals(username)) {
//            return new User("javainuse", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
//                    new ArrayList<>());
//        } else {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//    }
        DataAccessObject dataAccessObject = authDetailsRepository.findByRegNo(regNo);
        if (dataAccessObject == null) {
            throw new UsernameNotFoundException("RegNo not found: " + regNo);
        }
        return new org.springframework.security.core.userdetails.User(dataAccessObject.getRegNo(), bCryptPasswordEncoder.encode(dataAccessObject.getPassword()),
                new ArrayList<>());
    }
}
