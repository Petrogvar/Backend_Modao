package com.SpringProject.core.Services;

import com.SpringProject.core.Repository.UserRepository;
import com.SpringProject.core.Entity.User;
import com.SpringProject.core.controllers.Error.NotFoundException;
import com.SpringProject.core.controllers.Error.LoginException;
import com.SpringProject.core.dto.UserDto;
import com.SpringProject.core.mapper.UserMapperImpl;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository usersRepository;

  @Override
  public UserDto getUser(Long userId) {
    Optional<User> optionalUser = usersRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      throw new NotFoundException();
    } else {
      User user = optionalUser.get();
      return UserMapperImpl.toUserDto(user);
    }
  }

  public Long createUser(UserDto userDto) {
    User user = UserMapperImpl.toUser(userDto);
    if (usersRepository.findByLogin(user.getLogin()) != null) {
      throw new LoginException();
    } else {
      if (user.getBank() == null) {
        user.setBank("-");
      }
      if (user.getPhoneNumber() == null) {
        user.setPhoneNumber("-");
      }
      if (user.getIdPicture() == null) {
        user.setIdPicture(-1);
      }
      return usersRepository.save(user).getId();
    }
  }


  @Override
  public void updateUser(Long userId, UserDto userDto) {
    Optional<User> optionalUser = usersRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      throw new NotFoundException();
    } else {
      User user = optionalUser.get();
      user.setBank(userDto.getBank());
      user.setPhoneNumber(userDto.getPhoneNumber());
      user.setUsername(userDto.getUsername());
      user.setIdPicture(userDto.getIdPicture());
      usersRepository.save(user);
    }
  }

  @Override
  public void deleteUser(Long userId) {
    usersRepository.deleteById(userId);
  }

  @Override
  public Long authorizationUser(UserDto userDto) {
    User user = usersRepository.findByLoginAndPassword(userDto.getLogin(), userDto.getPassword());
    if (user == null) {
      throw new NotFoundException();
    } else {
      return user.getId();
    }
  }
}