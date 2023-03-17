package com.zonebug.debugging.security.util;

import com.zonebug.debugging.domain.user.User;
import com.zonebug.debugging.security.user.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtil {

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

    private SecurityUtil() { }

    public static Optional<User> getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null) {
            logger.debug("Security Context에 인증 정보 없음");
            return Optional.empty();
        }

        User currentUser = null;
        if(authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            currentUser = userDetails.getUser();
        }

        return Optional.ofNullable(currentUser);
    }

}
