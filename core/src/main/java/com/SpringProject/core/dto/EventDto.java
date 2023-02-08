package com.SpringProject.core.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDto {

  private String name;
  private Long group_id;
  private Integer price;
  private List<Long> participants_id;
  private List<Double> participants_k;
}
