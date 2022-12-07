package com.SpringProject.core.dto;

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
  private String phone_number;
  private String bank;
  private Integer idPicture;
  private List<Long> groups;
  //private List<Long> event;
}
