package com.SpringProject.core.Services;

import com.SpringProject.core.Repository.UserRepository;
import com.SpringProject.core.Entity.User;
import com.SpringProject.core.controllers.Error.ThereIsNoSuchUserException;
import com.SpringProject.core.controllers.Error.loginException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository usersRepository;
  @Override
  public User getUsers(Long id){
    User a = usersRepository.findById(id).get();
    /*if (a == null)
     throw new ThereIsNoSuchUserException();
    else {
      UsersEntity f = a.get();
      //a.setUsername("qweqweqwe");
      f.setGroup(null);
      return f;
    }*/
    return a;
  }
  @Override
  public Long createUsers(User user) {
    if (usersRepository.findByLogin(user.getLogin())!=null)
      throw  new loginException();
    else{
      if (user.getBank()==null)
        user.setBank("-");
      if (user.getPhone_number()==null)
        user.setPhone_number("-");
      if (user.getIdPicture()==null)
        user.setIdPicture(-1);
      return usersRepository.save(user).getId();
      }
  }

  @Override
  public void updateUsers(Long id, User usersTable) {
    User old = usersRepository.findById(id).get();
    old.setBank(usersTable.getBank());
    usersRepository.save(old);
  }

  @Override
  public void deleteUsers(Long id) {
    usersRepository.deleteById(id);
  }
  @Override
  public  Long findUser(User user) {
    User a = usersRepository.findByLoginAndPassword(user.getLogin(), user.getPassword());
    if (a == null)
      throw new ThereIsNoSuchUserException();
    else
      return a.getId();
  }
}