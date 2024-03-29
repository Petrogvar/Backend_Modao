package com.SpringProject.core.Mapper;

import com.SpringProject.core.Entity.Debt;
import com.SpringProject.core.dto.DebtDto;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DebtMapperImpl {

public static DebtDto toDebtDto(Debt debt){
  DebtDto debtDto = new DebtDto();
  DecimalFormat df = new DecimalFormat("#.##");
  debtDto.setDebt(df.format(debt.getDebt()));
  debtDto.setUserId(debt.getUserTo().getId());
  debtDto.setUsername(debt.getUserTo().getUsername());
  return debtDto;
}

  public static List<DebtDto> toDebtDtoList(List<Debt> debtList){
    List<DebtDto> debtDtoList = new ArrayList<>();
    for (Debt debt : debtList) {
      debtDtoList.add(toDebtDto(debt));
    }
    return debtDtoList;
  }

}
