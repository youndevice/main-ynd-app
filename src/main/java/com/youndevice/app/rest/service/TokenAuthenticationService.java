package com.youndevice.app.rest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youndevice.app.domain.AuthenticationToken;
import com.youndevice.app.domain.User;
import com.youndevice.app.dto.ResponseDTO;
import com.youndevice.app.repository.AuthenticationTokenRepository;
import com.youndevice.app.repository.UserRepository;
import com.youndevice.app.rest.constants.RestConstants;
import com.youndevice.app.rest.dto.UserAuthentication;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
@Transactional
public class TokenAuthenticationService {

    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";
    public static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    AuthenticationTokenRepository authenticationTokenRepository;

    @Autowired
    UserRepository userRepository;

    public String generateNewToken() {
        return UUID.randomUUID().toString();
    }

    public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%  Token Auth Service  %%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%  addAuthentication  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        final User user = authentication.getDetails();
        // user.setExpires(System.currentTimeMillis() + TEN_DAYS);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String tokenValue = UUID.randomUUID().toString();
        authenticationTokenRepository.save(new AuthenticationToken(tokenValue, user.getEmailId()));
        response.addHeader(AUTH_HEADER_NAME, tokenValue);
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put(AUTH_HEADER_NAME, tokenValue);
        responseMap.put(RestConstants.FIRST_NAME, user.getFirstName());
        responseMap.put(RestConstants.LAST_NAME, user.getLastName());
        ResponseDTO<Map> responseDTO = new ResponseDTO<>();
        responseDTO.setData(responseMap);
        responseDTO.setStatus(Boolean.TRUE);
        try {
            response.getOutputStream().write(objectMapper.writeValueAsBytes(responseDTO));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUserDetails(String authToken, Map<String, User> validUserData) {


        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%  CustomAuthenticationFilter  %%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%  getUserDetails  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        User user = validUserData.get(authToken);
        if (user == null) {
            AuthenticationToken authenticationToken = authenticationTokenRepository.findByTokenValue(authToken);
            if (authenticationToken != null) {
                user = userRepository.findByEmailId(authenticationToken.getEmailId());
                Hibernate.initialize(user.getRoles());
                validUserData.put(authToken, user);
            }
        }
        return user;
    }
}
