package com.SpringProject.core.Services;

import com.SpringProject.core.Repository.UsersRepository;
import com.SpringProject.core.Entity.UsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

  private final UsersRepository usersRepository;
  @Override
  public UsersEntity getUsers(Long id){
    return usersRepository.findById(id).get();
  }
  @Override
  public Long createUsers(UsersEntity usersTable) {
    return usersRepository.save(usersTable).getId();
  }

  @Override
  public void updateUsers(Long id, UsersEntity usersTable) {
    UsersEntity old = usersRepository.findById(id).get();
    old.setBank(usersTable.getBank());
    usersRepository.save(old);
  }

  @Override
  public void deleteUsers(Long id) {
    usersRepository.deleteById(id);
  }
}
