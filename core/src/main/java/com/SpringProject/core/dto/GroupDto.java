package com.SpringProject.core.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDto {

  private Long id;
  private String groupName;
  private String description;
  private Integer typeGroup;
  private List<Long> userIdList;
}
