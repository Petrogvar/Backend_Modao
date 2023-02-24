package com.SpringProject.core.controllers;

import com.SpringProject.core.Services.CommonService;
import com.SpringProject.core.Services.DebtService;
import com.SpringProject.core.controllers.Error.NotRightException;
import com.SpringProject.core.dto.DebtDto;
import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debt")
public class DebtController {
  private final DebtService debtService;
  private final CommonService commonService;

  public DebtController(DebtService debtService, CommonService commonService) {
    this.debtService = debtService;
    this.commonService = commonService;
  }

  @GetMapping("/{userId}/{groupId}")
  public List<DebtDto> GetDebtListByUserAndGroup(@PathVariable Long userId,@PathVariable Long groupId){
    String userLogin = SecurityContextHolder.getContext().getAuthentication().getName();
    if (!commonService.userHaveRightGetDebt(userId, groupId, userLogin))
      throw new NotRightException();
    return debtService.GetDebtListByUserAndGroup(userId, groupId);
  }
}
