package com.SpringProject.core.Services.Auth;

import com.SpringProject.core.Repository.UserRepository;
import com.SpringProject.core.controllers.Error.LoginException;
import com.SpringProject.core.dto.UserDto;
import com.SpringProject.core.dto.domain.JwtAuthentication;
import com.SpringProject.core.dto.domain.JwtRequest;
import com.SpringProject.core.dto.domain.JwtResponse;
import com.SpringProject.core.mapper.UserMapperImpl;
import io.jsonwebtoken.Claims;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.message.AuthException;
import liquibase.pro.packaged.L;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService  {

  private final UserRepository userRepository;
  private final Map<String, String> refreshStorage = new HashMap<>();
  private final JwtProvider jwtProvider;

  public JwtResponse login(@NonNull JwtRequest authRequest) {
    final UserDto userDto = UserMapperImpl.toUserDto(userRepository.getByLogin(authRequest.getLogin())
        .orElseThrow(() -> new LoginException()));
    if (userDto.getPassword().equals(authRequest.getPassword())) {
      final String accessToken = jwtProvider.generateAccessToken(userDto);
      final String refreshToken = jwtProvider.generateRefreshToken(userDto);
      refreshStorage.put(userDto.getLogin(), refreshToken);
      return new JwtResponse(accessToken, refreshToken);
    } else {
      throw new LoginException();
    }
  }

  public JwtResponse getAccessToken(@NonNull String refreshToken){
    if (jwtProvider.validateRefreshToken(refreshToken)) {
      final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
      final String login = claims.getSubject();
      final String saveRefreshToken = refreshStorage.get(login);
      if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
        final UserDto userDto = UserMapperImpl.toUserDto(userRepository.getByLogin(login)
            .orElseThrow(() -> new LoginException()));
        final String accessToken = jwtProvider.generateAccessToken(userDto);
        return new JwtResponse(accessToken, null);
      }
    }
    return new JwtResponse(null, null);
  }


  public JwtResponse refresh(@NonNull String refreshToken) {
    if (jwtProvider.validateRefreshToken(refreshToken)) {
      final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
      final String login = claims.getSubject();
      final String saveRefreshToken = refreshStorage.get(login);
      if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
        final UserDto userDto = UserMapperImpl.toUserDto(userRepository.getByLogin(login)
            .orElseThrow(() -> new LoginException()));
        final String accessToken = jwtProvider.generateAccessToken(userDto);
        final String newRefreshToken = jwtProvider.generateRefreshToken(userDto);
        refreshStorage.put(userDto.getLogin(), newRefreshToken);
        return new JwtResponse(accessToken, newRefreshToken);
      }
    }
    throw new LoginException();
  }

  public JwtAuthentication getAuthInfo() {
    return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
  }

}
