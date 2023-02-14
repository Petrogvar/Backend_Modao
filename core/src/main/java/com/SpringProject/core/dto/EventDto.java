package com.SpringProject.core.dto;

import com.SpringProject.core.dto.my.CustomPairIdCoefficient;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDto {

  private Long id;
  private String name;
  private Long groupId;
  private Integer price;
  private Long userPayingId;
  private String usernamePaying;
  private List<CustomPairIdCoefficient> customPairIdCoefficientList;
  private List<ExpenseDto> expenseDtoList;
}
