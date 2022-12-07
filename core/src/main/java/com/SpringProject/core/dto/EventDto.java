package com.SpringProject.core.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDto {
  private String name;
  private Integer type;
  private Long group_id;
  private List<Long> participants_id;
  private List<Double> participants_k;
}
