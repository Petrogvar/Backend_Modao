package com.SpringProject.core.Services.Auth;

import com.SpringProject.core.Entity.Token;
import com.SpringProject.core.Entity.User;
import com.SpringProject.core.Repository.UserRepository;
import com.SpringProject.core.Services.Firebase.Firebase;
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
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService  {

  private final UserRepository userRepository;
  private final Map<String, String> refreshStorage = new HashMap<>();
  private final JwtProvider jwtProvider;
  private  final Firebase firebase;

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
      Token token = optionalUser.get().getToken();
      token.setRefreshToken(refreshToken);
      if (authRequest.getDeviceToken() != null && authRequest.getPackageName() != null &&
          authRequest.getAppVersion()!= null) {
        ResponseEntity<String> responseEntity = firebase.registerServer(
            authRequest.getDeviceToken(), authRequest.getPackageName(),
            authRequest.getAppVersion());
        JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        token.setRegistrationToken(jsonObject.getString("token"));
      }
      token.setTime(new Timestamp(System.currentTimeMillis()));
     //refreshStorage.put(userDto.getLogin(), refreshToken);
      userRepository.save(optionalUser.get());
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
      Optional<User> optionalUser = userRepository.getByLogin(login);
      if (!optionalUser.isPresent())
        throw new AuthException(); //aaaa
      if (saveRefreshToken != null && optionalUser.get().getToken().getRefreshToken().equals(refreshToken)) {
        final UserDto userDto = UserMapperImpl.toUserDto(optionalUser.get());
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
      Optional<User> optionalUser = userRepository.getByLogin(login);
      if (!optionalUser.isPresent())
        throw new AuthException(); //aaaa
      String saveRefreshToken = optionalUser.get().getToken().getRefreshToken();
      if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
        final UserDto userDto = UserMapperImpl.toUserDto(optionalUser.get());
        final String accessToken = jwtProvider.generateAccessToken(userDto);
        final String newRefreshToken = jwtProvider.generateRefreshToken(userDto);
        Token token = optionalUser.get().getToken();
        //firebase.registerServer()
        token.setRefreshToken(newRefreshToken);
        token.setTime(new Timestamp(System.currentTimeMillis()));
        userRepository.save(optionalUser.get());
        return new JwtResponse(null, accessToken, newRefreshToken);
      }
    }
    throw new LoginException();
  }

  public JwtAuthentication getAuthInfo() {
    return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
  }

}
