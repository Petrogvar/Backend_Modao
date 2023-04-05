package com.SpringProject.core.Services;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final InvitationInGroupRepository invitationRepository;
  private final InvitationFriendRepository invitationFriendRepository;
  private  final DataVerification dataVerification;

  @Override
  public UserDto getUserMyInfo(Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      throw new NotFoundException();
    }
    User user = optionalUser.get();
    return UserMapperImpl.toUserDtoMyInfo(user);
  }

  @Override
  public UserDto getNewUuid(Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty())
      throw new NotFoundException();
    String uuid = Uid.getUuid();
    Optional<User> optionalUserTemp = userRepository.getByUuid(uuid);
    while(optionalUserTemp.isPresent()){
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
    List<UserDto> userDtoList = new ArrayList<>();
    int size = optionalUser.get().getFriends().size();
    for (int i=0; i<size; i++){
      userDtoList.add(UserMapperImpl.toUserDtoWithoutUuid(
          optionalUser.get().getFriends().get(i)));
    }
    return userDtoList;
  }

  @Override
  public UserDto getUser(Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
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


    user.setIdPicture(-1);
    user.setBank("-");
    user.setPhoneNumber("-");


    String uuid = Uid.getUuid();
    Optional<User> optionalUserTemp = userRepository.getByUuid(uuid);
    while(optionalUserTemp.isPresent()){
      uuid = Uid.getUuid();
      optionalUserTemp = userRepository.getByUuid(uuid);
    }
    user.setUuid(uuid);

    return userRepository.save(user).getId();
  }

  //хз
  @Override
  public void updateUser(Long userId, UserDto userDto) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
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
  public List<GroupDto> getGroups(Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      throw new NotFoundException();
    }
    List<GroupDto> groupDtoList = new ArrayList<>();
    int size = optionalUser.get().getUserGroupsList().size();
    for (int i = 0; i < size; i++) {
      groupDtoList.add(
          GroupMapperImpl.toGroupDtoWithoutUuid(optionalUser.get().getUserGroupsList().get(i).getGroup()));
    }
    return groupDtoList;
  }

  //хз

  @Override
  public void deleteUser(Long userId) {
    userRepository.deleteById(userId);
  }

}