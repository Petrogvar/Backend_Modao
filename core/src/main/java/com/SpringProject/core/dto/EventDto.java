package com.SpringProject.core.dto;

import com.SpringProject.core.dto.my.CustomPairIdCoefficient;
import java.time.Instant;
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
  private CustomPairIdCoefficient customPairIdCoefficientPaying;
  private List<CustomPairIdCoefficient> customPairIdCoefficientList;
  private String usernameCreator;
  private Long userCreatorId;
  private Instant time;
  private List<ExpenseDto> expenseDtoList;
}
