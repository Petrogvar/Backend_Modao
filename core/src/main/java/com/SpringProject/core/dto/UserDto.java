package com.SpringProject.core.dto;


import com.SpringProject.core.dto.my.CustomPairIdName;
import java.util.AbstractMap;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

  private Long id;
  private String username;
  private String login;
  private String password;
  private String uuid;
  private String phoneNumber;
  private String bank;
  private Integer idPicture;

}
