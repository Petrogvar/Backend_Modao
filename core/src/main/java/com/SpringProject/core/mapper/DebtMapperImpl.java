package com.SpringProject.core.mapper;

import com.SpringProject.core.Entity.Debt;
import com.SpringProject.core.dto.DebtDto;
import java.util.ArrayList;
import java.util.List;

public class DebtMapperImpl {

public static DebtDto toDebtDto(Debt debt){
  DebtDto debtDto = new DebtDto();
  debtDto.setDebt(debt.getDebt());
  debtDto.setUserId(debt.getUserTo().getId());
  debtDto.setUsername(debt.getUserTo().getUsername());
  return debtDto;
}

  public static List<DebtDto> toDebtDtoList(List<Debt> debtList){
    List<DebtDto> debtDtoList = new ArrayList<>();
    for (int i=0; i<debtList.size(); i++){
      debtDtoList.add(toDebtDto(debtList.get(i)));
    }
    return debtDtoList;
  }

}
