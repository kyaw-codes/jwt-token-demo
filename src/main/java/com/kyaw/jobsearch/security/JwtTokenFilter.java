package com.kyaw.jobsearch.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider provider;
    @Autowired
    private JwtUserDetailsService service;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        // check token
        String username = provider.userName(httpServletRequest);

        SecurityContext security = SecurityContextHolder.getContext();

        if (!StringUtils.isEmpty(username)) {
            UserDetails user = service.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());

            security.setAuthentication(auth);
        } else {
            // delete authentications
            security.setAuthentication(null);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
