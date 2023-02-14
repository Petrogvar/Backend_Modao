package com.SpringProject.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseDto {
  private Long userId;
  private String username;
  private Double coefficient;
  private Double transferAmount;
}
