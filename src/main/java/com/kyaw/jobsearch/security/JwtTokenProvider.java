package com.kyaw.jobsearch.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.token}")
    private String token;
    @Value("${app.jwt.issuer}")
    private String issuer;
    @Value("${app.jwt.limit}")
    private int limit;

    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generate(String username, Collection<? extends GrantedAuthority> roles) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, limit);

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(cal.getTime())
                .setIssuer(issuer)
                .setSubject(username)
                .claim("rol", roles)
                .signWith(key)
                .compact();
    }

    public String userName(HttpServletRequest req) {
        String requestToken = req.getHeader(token);

        if (!StringUtils.isEmpty(requestToken)) {
            return Jwts.parserBuilder()
                    .requireIssuer(issuer)
                    .setSigningKey(key)
                    .build().parseClaimsJws(requestToken)
                    .getBody().getSubject();
        }

        return null;
    }

}
