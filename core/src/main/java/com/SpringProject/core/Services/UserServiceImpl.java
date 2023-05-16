package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.Token;
import com.SpringProject.core.Entity.UserGroup;
import com.SpringProject.core.Mapper.GroupMapperImpl;
import com.SpringProject.core.Repository.InvitationFriendRepository;
import com.SpringProject.core.Repository.InvitationInGroupRepository;
import com.SpringProject.core.Repository.UserRepository;
import com.SpringProject.core.Entity.User;
import com.SpringProject.core.Services.h.DataVerification;
import com.SpringProject.core.Services.h.Uid;
import com.SpringProject.core.controllers.Error.NotFoundException;
import com.SpringProject.core.controllers.Error.LoginException;
import com.SpringProject.core.dto.GroupDto;
import com.SpringProject.core.dto.UserDto;
import com.SpringProject.core.Mapper.UserMapperImpl;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final InvitationInGroupRepository invitationRepository;
  private final InvitationFriendRepository invitationFriendRepository;
  private final DataVerification dataVerification;

  @Override
  public UserDto getUserMyInfo(Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (!optionalUser.isPresent()) {
      throw new NotFoundException();
    }
    User user = optionalUser.get();
    return UserMapperImpl.toUserDtoMyInfo(user);
  }

  @Override
  public UserDto getNewUuid(Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (!optionalUser.isPresent()) {
      throw new NotFoundException();
    }
    String uuid = Uid.getUuid();
    Optional<User> optionalUserTemp = userRepository.getByUuid(uuid);
    while (optionalUserTemp.isPresent()) {
      uuid = Uid.getUuid();
      optionalUserTemp = userRepository.getByUuid(uuid);
    }
    optionalUser.get().setUuid(uuid);
    userRepository.save(optionalUser.get());
    return UserMapperImpl.toUserDtoMyInfo(optionalUser.get());
  }

  @Override
  public List<UserDto> getListFriends(Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (!optionalUser.isPresent()) {
      throw new NotFoundException();
    }
    List<UserDto> userDtoList = new ArrayList<>();
    for (User friend : optionalUser.get().getFriends()) {
      userDtoList.add(UserMapperImpl.toUserDtoWithoutUuid(friend));
    }
    return userDtoList;
  }

  @Override
  public UserDto getUser(Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (!optionalUser.isPresent()) {
      throw new NotFoundException();
    }
    return UserMapperImpl.toUserDtoWithoutUuid(optionalUser.get());
  }

  public Long createUser(UserDto userDto) {
    User user = UserMapperImpl.toUser(userDto);

    dataVerification.login(user.getLogin());
    if (userRepository.findByLogin(user.getLogin()) != null) {
      throw new LoginException();
    }
    dataVerification.password(user.getPassword());
    dataVerification.isValidUsername(user.getUsername());
    SecureRandom random = new SecureRandom();
    String salt = BCrypt.gensalt(4, random);
    user.setPassword(BCrypt.hashpw(user.getPassword(), salt));

    Token token = new Token();
    token.setUser(user);
    user.setToken(token);

    user.setIdPicture(-1);
    user.setBank("-");
    user.setPhoneNumber("-");
    user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    String uuid = Uid.getUuid();
    Optional<User> optionalUserTemp = userRepository.getByUuid(uuid);
    while (optionalUserTemp.isPresent()) {
      uuid = Uid.getUuid();
      optionalUserTemp = userRepository.getByUuid(uuid);
    }
    user.setUuid(uuid);

    return userRepository.save(user).getId();
  }

  @Override
  public void exitUser(Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (!optionalUser.isPresent()) {
      throw new NotFoundException();
    }
    optionalUser.get().getToken().setRegistrationToken(null);
    optionalUser.get().getToken().setRegistrationToken(null);
    optionalUser.get().getToken().setTime(null);
  }

  //хз
  @Override
  public void updateUser(Long userId, UserDto userDto) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (!optionalUser.isPresent()) {
      throw new NotFoundException();
    }
    User user = optionalUser.get();
    user.setBank(userDto.getBank());
    user.setPhoneNumber(userDto.getPhoneNumber());
    user.setUsername(userDto.getUsername());
    user.setIdPicture(userDto.getIdPicture());
    userRepository.save(user);
  }

  @Override
  public List<GroupDto> getGroups(Long userId, Integer type) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (!optionalUser.isPresent()) {
      throw new NotFoundException();
    }
    //проверка type
    List<GroupDto> groupDtoList = new ArrayList<>();
    for (UserGroup userGroup : optionalUser.get().getUserGroupsList()) {
      if (type == 2 || Objects.equals(userGroup.getGroup().getType(), type)) {
        groupDtoList.add(
            GroupMapperImpl.toGroupDtoWithoutUuid(userGroup.getGroup()));
      }
    }
    return groupDtoList;
  }

  @Override
  public void deleteUser(Long userId) {
    userRepository.deleteById(userId);
  }

}