package com.youndevice.app.rest.service.apiServices;


import com.youndevice.app.domain.AuthenticationToken;
import com.youndevice.app.rest.dto.ApiResponseDTO;
import com.youndevice.app.service.repo.AuthenticationTokenRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationApiService {

    @Autowired
    AuthenticationTokenRepoService authenticationTokenRepoService;

    public ApiResponseDTO performLogoutRelatedOperation(String authToken) {
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO<>("User logged out successfully", Boolean.TRUE, null);
        AuthenticationToken authenticationToken = authenticationTokenRepoService.findByTokenValue(authToken);
        if (authenticationToken != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
            authenticationTokenRepoService.delete(authenticationToken);
        } else {
            apiResponseDTO = new ApiResponseDTO<>("User could not be logged out", Boolean.FALSE, null);
        }
        return apiResponseDTO;
    }
}
