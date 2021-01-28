package com.techbasevn.backend.security.webconfig;

import com.techbasevn.backend.cache.model.UserCache;
import com.techbasevn.backend.common.Constant;
import com.techbasevn.backend.model.entities.User;
import com.techbasevn.backend.cache.repositories.CacheRepository;
import com.techbasevn.backend.enumeration.ErrorCode;
import com.techbasevn.backend.enumeration.UserType;
import com.techbasevn.backend.exception.RestApiException;
import com.techbasevn.backend.repositories.UserRepository;
import com.techbasevn.backend.security.jwt.JwtProperties;
import com.techbasevn.backend.utils.Utils;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Component
@AllArgsConstructor
public class TokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    public static final String AUTHORITIES_KEY = "auth";
    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;
    private final CacheRepository<UserCache> cacheRepository;

    public String createToken(User user, boolean rememberMe) {
        long plusTime = rememberMe ? jwtProperties.getTokenValidityInSecondsForRememberMe()
                : jwtProperties.getTokenValidityInSeconds();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (Utils.ObjectIsNull(user)) {
            return null;
        }
        grantedAuthorities.add(new SimpleGrantedAuthority(
                Utils.ObjectIsNull(user.getRole()) ? UserType.USER.name() : user.getRole().getCode())
        );
        return Jwts.builder()
                .setAudience(Constant.TokenAudience.API)
                .setExpiration(Date.from(Instant.now().plusSeconds(plusTime)))
                .claim(AUTHORITIES_KEY, grantedAuthorities)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                .compact();
    }

    public void setAuthenticationByToken(String token) {
        try {
            if (!StringUtils.hasText(token)) {
                return;
            }
            Claims claims = validateAndParseToken(token);
            if (Utils.ObjectIsNull(claims)) {
                return;
            }
            executeAuthentication(claims, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    private void executeAuthentication(Claims claims, String token) {
        UserCache userCache = cacheRepository
                .find(Constant.TOKEN_PREFIX + token, UserCache.class)
                .orElseThrow(() -> new RestApiException(ErrorCode.TOKEN_NOT_EXIST));
        UserDetails userDetails = null;
        String audience = claims.getAudience();
        if (Constant.TokenAudience.API.equals(audience)) {
            User user = this.userRepository.findById(userCache.getId())
                    .orElseThrow(() -> new RestApiException(ErrorCode.USER_NOT_EXIST));
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            ArrayList<LinkedHashMap<String, String>> temp = (ArrayList<LinkedHashMap<String, String>>) claims.get("auth");
            temp.forEach(t -> t.forEach((key, value) -> grantedAuthorities.add(new SimpleGrantedAuthority(value))));
            userDetails = new UserPrincipal(user, token, grantedAuthorities);
        }
        if (Utils.ObjectIsNull(userDetails)) {
            return;
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                SecurityUtils.getCurrentUserJWT(),
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public Claims validateAndParseToken(String token) {
        Claims claims = null;
        try {
            claims =
                    Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody();
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT token compact of handler are invalid: {}", e.getMessage());
        } catch (Exception ignored) {
        }
        return claims;
    }
}
