package com.SpringProject.core.Services;

import com.SpringProject.core.Entity.Debt;
import com.SpringProject.core.Entity.Group;
import com.SpringProject.core.Entity.User;
import com.SpringProject.core.Entity.UserGroup;
import com.SpringProject.core.Repository.DebtRepository;
import com.SpringProject.core.Repository.GroupRepository;
import com.SpringProject.core.Repository.UserRepository;
import com.SpringProject.core.controllers.Error.NotFoundException;
import com.SpringProject.core.controllers.Error.UserNotGroupException;
import com.SpringProject.core.dto.DebtDto;
import com.SpringProject.core.Mapper.DebtMapperImpl;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DebtServiceImpl implements DebtService{

  private final DebtRepository debtRepository;
  private final UserRepository userRepository;
  private final GroupRepository groupRepository;

  @Override
  public List<DebtDto> GetDebtListByUserAndGroup(Long userId, Long groupId){
    Optional<User> optionalUser = userRepository.findById(userId);

    Optional<Group> optionalGroup = groupRepository.findById(groupId);
    if (optionalGroup.isEmpty() || optionalUser.isEmpty())
      throw new NotFoundException();
    boolean bool = true;
    for (UserGroup userGroup: optionalGroup.get().getUserGroupList()){
      if (Objects.equals(userGroup.getUser().getId(), userId)) {
        bool = false;
        break;
      }
    }
    if (bool)
      throw new UserNotGroupException();
    List<Debt> debtList = debtRepository.findAllByGroupAndUserFrom(optionalGroup.get(), optionalUser.get());
    return DebtMapperImpl.toDebtDtoList(debtList);
  }

}
