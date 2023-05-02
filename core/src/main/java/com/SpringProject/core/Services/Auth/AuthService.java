package com.SpringProject.core.Services.Auth;

import com.SpringProject.core.Entity.User;
import com.SpringProject.core.Repository.UserRepository;
import com.SpringProject.core.controllers.Error.AuthException;
import com.SpringProject.core.controllers.Error.BadRequestException;
import com.SpringProject.core.controllers.Error.LoginException;
import com.SpringProject.core.controllers.Error.NotFoundException;
import com.SpringProject.core.dto.UserDto;
import com.SpringProject.core.dto.domain.JwtAuthentication;
import com.SpringProject.core.dto.domain.JwtRequest;
import com.SpringProject.core.dto.domain.JwtResponse;
import com.SpringProject.core.Mapper.UserMapperImpl;
import io.jsonwebtoken.Claims;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService  {

  private final UserRepository userRepository;
  private final Map<String, String> refreshStorage = new HashMap<>();
  private final JwtProvider jwtProvider;

  public JwtResponse login(@NonNull JwtRequest authRequest) {
    Optional<User> optionalUser = userRepository.getByLogin(authRequest.getLogin());
    if (!optionalUser.isPresent())
      throw new AuthException();
    if(authRequest.getLogin() == null || authRequest.getPassword()==null){
      throw new LoginException();
    }
    UserDto userDto = UserMapperImpl.toUserDto(optionalUser.get());
    if (BCrypt.checkpw(authRequest.getPassword(), optionalUser.get().getPassword())) {
      final String accessToken = jwtProvider.generateAccessToken(userDto);
      final String refreshToken = jwtProvider.generateRefreshToken(userDto);
      refreshStorage.put(userDto.getLogin(), refreshToken);
      return new JwtResponse(userDto.getId() ,accessToken, refreshToken);
    } else {
      throw new AuthException();
    }
  }

  public JwtResponse getAccessToken(@NonNull String refreshToken){
    if (jwtProvider.validateRefreshToken(refreshToken)) {
      final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
      final String login = claims.getSubject();
      final String saveRefreshToken = refreshStorage.get(login);
      if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
        final UserDto userDto = UserMapperImpl.toUserDto(userRepository.getByLogin(login)
            .orElseThrow(() -> new NotFoundException()));
        final String accessToken = jwtProvider.generateAccessToken(userDto);
        return new JwtResponse(null, accessToken, null);
      }
    }
    return new JwtResponse(null, null, null);
  }


  public JwtResponse refresh(@NonNull String refreshToken) {
    if (jwtProvider.validateRefreshToken(refreshToken)) {
      final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
      final String login = claims.getSubject();
      final String saveRefreshToken = refreshStorage.get(login);
      if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
        final UserDto userDto = UserMapperImpl.toUserDto(userRepository.getByLogin(login)
            .orElseThrow(() -> new NotFoundException()));
        final String accessToken = jwtProvider.generateAccessToken(userDto);
        final String newRefreshToken = jwtProvider.generateRefreshToken(userDto);
        refreshStorage.put(userDto.getLogin(), newRefreshToken);
        return new JwtResponse(null, accessToken, newRefreshToken);
      }
    }
    throw new LoginException();
  }

  public JwtAuthentication getAuthInfo() {
    return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
  }

}
