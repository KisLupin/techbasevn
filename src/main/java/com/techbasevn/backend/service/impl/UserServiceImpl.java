package com.techbasevn.backend.service.impl;

import com.techbasevn.backend.cache.model.UserCache;
import com.techbasevn.backend.model.entities.User;
import com.techbasevn.backend.model.request.LoginRequest;
import com.techbasevn.backend.service.dao.UserService;
import com.techbasevn.backend.cache.repositories.CacheRepository;
import com.techbasevn.backend.common.BaseResponse;
import com.techbasevn.backend.enumeration.ErrorCode;
import com.techbasevn.backend.exception.RestApiException;
import com.techbasevn.backend.repositories.UserRepository;
import com.techbasevn.backend.security.webconfig.TokenProvider;
import com.techbasevn.backend.utils.Utils;
import lombok.AllArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final TokenProvider tokenProvider;
    private final CacheRepository<UserCache> userCacheRepository;

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        LOGGER.info("login");
        if (Utils.ObjectIsNull(loginRequest)){
            throw new RestApiException(ErrorCode.LOGIN_REQUEST_NOT_VALID);
        }
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (Utils.ObjectIsNull(user)){
            throw new RestApiException(ErrorCode.USERNAME_NOT_EXIST);
        }
        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RestApiException(ErrorCode.PASSWORD_INVALID);
        }
        String token = tokenProvider.createToken(user, loginRequest.getRememberMe());
        userCacheRepository
                .add(new UserCache(token, user.getId()));
        return ResponseEntity.ok(new BaseResponse(token));
    }
}
