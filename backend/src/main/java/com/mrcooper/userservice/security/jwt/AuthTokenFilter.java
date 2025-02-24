package com.mrcooper.userservice.security.jwt;

import com.mrcooper.userservice.security.user.MRCooperUserDetails;
import com.mrcooper.userservice.security.user.MRCooperUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;


public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    MRCooperUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            // Construct full request URL safely
            String fullUrl = request.getRequestURL().toString();
            String queryString = request.getQueryString();
            if (queryString != null && !queryString.isBlank()) {
                fullUrl += "?" + queryString;
            }
            System.out.println("🔹 Full Request URL: " + fullUrl);
            System.out.println("🔹 HTTP Method: " + request.getMethod());

            //  Log request headers safely
            System.out.println("🔹 Incoming Request Headers:");
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String header = headerNames.nextElement();
                    String headerValue = request.getHeader(header);
                    System.out.println(header + ": " + (headerValue != null ? headerValue : "NULL"));
                }
            } else {
                System.out.println("No headers found.");
            }

            //  Parse JWT token
            String jwt = parseJwt(request);
            System.out.println(" Received JWT Token: " + jwt);

            if (StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)) {
                String email = jwtUtils.getUserNameFromToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                //  Extract roles from JWT instead of relying on `userDetails`
                List<SimpleGrantedAuthority> authorities = jwtUtils.getRolesFromToken(jwt);

                System.out.println("Extracted User: " + email);
                System.out.println(" Extracted Roles: " + authorities);

                var authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //  Set authentication in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            System.err.println(" Token Validation Failed: " + e.getMessage());
            throw new ServletException(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }



    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7);
        }
        return null;
    }
}
