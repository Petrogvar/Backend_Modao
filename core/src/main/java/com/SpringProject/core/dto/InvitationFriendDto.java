package com.SpringProject.core.dto;

import javax.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvitationFriendDto {
  private Long id;
  private String username;
  private Long userId;
}