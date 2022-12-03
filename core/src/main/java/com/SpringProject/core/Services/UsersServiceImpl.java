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
  public Long createUsers(UsersEntity user) {
    if (usersRepository.findByLogin(user.getLogin())!=null)
      return -1L;
    else{
      if (user.getBank()==null)
        user.setBank("-");
      if (user.getPhone_number()==null)
        user.setPhone_number("-");
      if (user.getId_picture()==null)
        user.setId_picture(-1);
      return usersRepository.save(user).getId();
      }
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
  @Override
  public  Long findUser(UsersEntity user) {
    UsersEntity a = usersRepository.findByLoginAndPassword(user.getLogin(), user.getPassword());
    if (a == null)
      return -1L;
    else
      return a.getId();
  }
}