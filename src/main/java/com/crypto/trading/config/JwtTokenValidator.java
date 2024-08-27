package com.crypto.trading.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        if (jwt != null) {
            jwt = jwt.substring(7);

            try {
                // Convert the secret key to a SecretKey object
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

                // Parse the JWT and extract the claims
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)          // Set the signing key
                        .build()                     // Build the JwtParser
                        .parseClaimsJws(jwt)         // Parse the JWT
                        .getBody();                  // Extract the claims

                // You can add the claims to the request context if needed
                //request.setAttribute("claims", claims);

                String email = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                Authentication auth = new UsernamePasswordAuthenticationToken(email, authoritiesList);



            } catch (ExpiredJwtException e) {
                throw new RuntimeException("Token has expired");
            } catch (SignatureException e) {
                throw new RuntimeException("Invalid JWT signature");
            } catch (Exception e) {
                throw new RuntimeException("Invalid token...");
            }
        }


    }
}
