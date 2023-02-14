package com.SpringProject.core.controllers;

import com.SpringProject.core.Services.DebtService;
import com.SpringProject.core.dto.DebtDto;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debt")
public class DebtController {
  private final DebtService debtService;

  public DebtController(DebtService debtService) {
    this.debtService = debtService;
  }

  @GetMapping("/{userId}/{groupId}")
  public List<DebtDto> GetDebtListByUserAndGroup(@PathVariable Long userId,@PathVariable Long groupId){
    return debtService.GetDebtListByUserAndGroup(userId, groupId);
  }
}
