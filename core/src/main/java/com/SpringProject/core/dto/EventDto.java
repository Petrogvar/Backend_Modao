package com.SpringProject.core.dto;

import com.SpringProject.core.dto.my.CustomPairIdCoefficient;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventDto {

  private Long id;

  private String name;
  private Long groupId;
  private Double price;
  private Integer type;
  private String description;

  private CustomPairIdCoefficient customPairIdCoefficientPaying;
  private List<CustomPairIdCoefficient> customPairIdCoefficientList;

  private LocalDateTime time;
  private Integer status;
  private Long userPayingId;
  private String usernamePaying;
  private String usernameCreator;
  private Long userCreatorId;
  private List<ExpenseDto> expenseDtoList;
  private Long deleteId;
  public EventDto(Long id, String eventName, String description,
      Double price, Integer type, LocalDateTime time, Long deleteId, Integer status) {
    this.id = id;
    this.name = eventName;
    this.description =description;
    this.price = price;
    this.type = type;
    this.time = time;
    this.deleteId = deleteId;
    this.status =status;
  }
}
