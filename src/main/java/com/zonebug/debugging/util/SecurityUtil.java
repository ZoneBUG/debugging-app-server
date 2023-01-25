package com.zonebug.debugging.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtil {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    private SecurityUtil() { }

    public static Optional<String> getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null) {
            logger.debug("Security Context에 인증 정보 없음");
            return Optional.empty();
        }

        String email = null;
        if(authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername();
        } else if(authentication.getPrincipal() instanceof String) {
           email = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(email);
    }

}
