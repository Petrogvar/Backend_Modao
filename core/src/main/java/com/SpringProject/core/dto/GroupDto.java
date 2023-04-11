package com.SpringProject.core.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDto {

  private Long id;
  private String groupName;
  private String description;
  private String uuid;
  private Integer typeGroup;
  private LocalDateTime time;
}
