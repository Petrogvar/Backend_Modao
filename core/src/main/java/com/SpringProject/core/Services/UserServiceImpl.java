package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.InvitationFriend;
import com.SpringProject.core.Mapper.GroupMapperImpl;
import com.SpringProject.core.Repository.InvitationFriendRepository;
import com.SpringProject.core.Repository.InvitationInGroupRepository;
import com.SpringProject.core.Repository.UserRepository;
import com.SpringProject.core.Entity.User;
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
  public UserDto getUser(Long userId) {
    Optional<User> optionalUser = userRepository.findById(userId);
    if (optionalUser.isEmpty()) {
      throw new NotFoundException();
    }
    return UserMapperImpl.toUserDtoWithoutUuid(optionalUser.get());
  }

  public Long createUser(UserDto userDto) {
    User user = UserMapperImpl.toUser(userDto);
    SecureRandom random = new SecureRandom();
    String salt = BCrypt.gensalt(4, random);
    user.setPassword(BCrypt.hashpw(user.getPassword(), salt));
    UUID u = UUID.randomUUID();
    user.setUuid(Uid.toIDString(u.getMostSignificantBits())/* + "   "+ Uid.toIDString(u.getLeastSignificantBits())*/);

    if (userRepository.findByLogin(user.getLogin()) != null) {
      throw new LoginException();
    }
    if (user.getBank() == null) {
      user.setBank("-");
    }
    if (user.getPhoneNumber() == null) {
      user.setPhoneNumber("-");
    }
    if (user.getIdPicture() == null) {
      user.setIdPicture(-1);
    }
    return userRepository.save(user).getId();
  }

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
          GroupMapperImpl.toGroupDto(optionalUser.get().getUserGroupsList().get(i).getGroup()));
    }
    return groupDtoList;
  }

  @Override
  public void deleteUser(Long userId) {
    userRepository.deleteById(userId);
  }

}