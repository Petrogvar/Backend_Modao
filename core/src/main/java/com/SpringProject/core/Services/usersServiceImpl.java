package com.SpringProject.core.Services;

import com.SpringProject.core.Repository.usersRepository;
import com.SpringProject.core.Entity.usersEntity;
import com.SpringProject.core.dto.usersDto;
import javax.persistence.Entity;
import liquibase.pro.packaged.E;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class usersServiceImpl implements usersService {

  private final usersRepository usersRepository;
  @Override
  public usersEntity getUsers(Long id){
    return usersRepository.findById(id).get();
  }
  @Override
  public Long create(usersEntity usersTable) {
    return usersRepository.save(usersTable).getId();
  }
  /* private usersEntity toEntity(usersDto userTable){
    usersEntity a = new usersEntity();
    a.setUsername(userTable.username);
    a.setPassword(userTable.password);
    a.setBank(userTable.bank);
    a.setPhone_number(userTable.phone_number);
    a.setId_picture(userTable.id_picture);
    return a;
  }*/
}
