package com.SpringProject.core.Services.h;

import com.SpringProject.core.dto.EventDto;
import com.SpringProject.core.dto.GroupDto;
import java.util.List;

public interface DataVerification {

  void password(String password);

  void login(String password);

  void group(GroupDto groupDto);

  void isValidUsername(String username);

  void event(EventDto eventDto);
}
