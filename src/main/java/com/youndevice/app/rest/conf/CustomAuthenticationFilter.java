package com.youndevice.app.rest.conf;


import com.youndevice.app.domain.User;
import com.youndevice.app.rest.service.TokenAuthenticationService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomAuthenticationFilter extends GenericFilterBean {

    public static Map<String, User> validUserData = new HashMap<String, User>();

    private TokenAuthenticationService tokenAuthenticationService;

    protected CustomAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {

        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%  CustomAuthenticationFilter  %%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%  doFilter  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

        User user = null;
        HttpServletResponse httpServletResponse = (HttpServletResponse) res;
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        String authToken = httpServletRequest.getHeader("X-Auth-Token");
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        if (authToken != null && !authToken.isEmpty()) {
            Set<GrantedAuthority> authorities = new HashSet<>();
            user = tokenAuthenticationService.getUserDetails(authToken, validUserData);
            if (user != null) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authToken, "", user.getRoles());
                SecurityContextHolder.getContext().setAuthentication(token);
                chain.doFilter(httpServletRequest, httpServletResponse);
            } else {
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token.");
            }
        } else {
            SecurityContextHolder.getContext().setAuthentication(null);
            httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Authorization header needed");
            return;
        }
    }


}
