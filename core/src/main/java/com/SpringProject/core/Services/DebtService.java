package com.SpringProject.core.Services;

import com.SpringProject.core.dto.DebtDto;
import java.util.List;

public interface DebtService {
  public List<DebtDto> GetDebtListByUserAndGroup(Long userId, Long groupId);
}
