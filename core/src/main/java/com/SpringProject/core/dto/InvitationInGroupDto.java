package com.SpringProject.core.dto;

import javax.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvitationInGroupDto {
  private Long id;


  private String username;

  private Long userId;

  private String nameGroup;

  private Long groupId;

}
