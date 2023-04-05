package com.SpringProject.core.Services.h;

import com.SpringProject.core.controllers.Error.Invalid.InvalidEventException;
import com.SpringProject.core.controllers.Error.Invalid.InvalidGroupException;
import com.SpringProject.core.controllers.Error.Invalid.InvalidLoginException;
import com.SpringProject.core.controllers.Error.Invalid.InvalidNameException;
import com.SpringProject.core.controllers.Error.Invalid.InvalidPasswordException;
import com.SpringProject.core.dto.EventDto;
import com.SpringProject.core.dto.GroupDto;
import com.SpringProject.core.dto.my.CustomPairIdCoefficient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DataVerificationImpl implements DataVerification{

  @Override
  public void password(String password) {
// Проверяем, что пароль имеет длину не менее 8 символов
    if (password.length() < 8) {
      throw new InvalidPasswordException("Пароль слишком короткий");
    }

    // Проверяем, что пароль содержит как минимум одну цифру
    if (!password.matches(".*\\d.*")) {
      throw new InvalidPasswordException("Пароль должен содержать как минимум одну цифру");
    }

    // Проверяем, что пароль содержит как минимум одну букву в верхнем регистре
    if (!password.matches(".*[A-Z].*")) {
      throw new InvalidPasswordException("Пароль должен содержать как минимум одну букву в верхнем регистре");
    }

    // Проверяем, что пароль содержит как минимум одну букву в нижнем регистре
    if (!password.matches(".*[a-z].*")) {
      throw new InvalidPasswordException("Пароль должен содержать как минимум одну букву в нижнем регистре");
    }
  }

  @Override
  public void login(String login) {
      // Проверяем длину логина
      if (login.length() < 6 || login.length() > 20) {
        throw new InvalidLoginException("Логин должен быть от 6 до 20 символов");
      }

      // Проверяем наличие только букв и цифр
      if (!login.matches("^[a-zA-Z0-9]*$")) {
        throw new InvalidLoginException("Логин должен содержать только буквы и цифры");
      }

      // Проверяем, что логин не начинается с цифры
      if (Character.isDigit(login.charAt(0))) {
        throw new InvalidLoginException("Логин не может начинаться с цифры");
      }
  }

  @Override
  public void group(GroupDto groupDto) {
    if (groupDto == null) {
      throw new InvalidGroupException("Group cannot be null", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    if (!isValidName(groupDto.getGroupName())) {
      throw new InvalidGroupException("Invalid group name", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    if (!isValidName(groupDto.getDescription())) {
      throw new InvalidGroupException("Invalid group description", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    Integer typeGroup = groupDto.getTypeGroup();
    if (typeGroup == null || (typeGroup != 0 && typeGroup != 1)) {
      throw new InvalidGroupException("Invalid group type", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  private static boolean isValidName(String name) {
    if (name == null || name.isEmpty()) {
      return false;
    }

    String regex = "^[a-zA-Z0-9_.а-яА-ЯёЁ ]{1,20}$";


    return name.matches(regex);
  }

  private static boolean isValidDescription(String description) {
    if (description == null || description.isEmpty()) {
      return false;
    }
    String regex = "^[a-zA-Z0-9_.а-яА-ЯёЁ ]{1,50}$";

    return description.matches(regex);
  }



  @Override
  public void isValidUsername(String username) {
    // Используем регулярное выражение для проверки имени пользователя
    // В данном случае мы разрешаем только буквы (в любом регистре), цифры, символы '_', '-', '.', а также русские буквы
    // Имя пользователя также должно быть длиной от 1 до 20 символов
    String regex = "^[a-zA-Z0-9_.а-яА-ЯёЁ ]{1,20}$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(username);
    if (!matcher.matches()){
      throw new InvalidNameException("InvalidUsername");
    }
  }

  @Override
  public void event(EventDto eventDto) {
    if (eventDto == null) {
      throw new InvalidEventException("event cannot be null", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    if (!isValidName(eventDto.getName())) {
      throw new InvalidEventException("Invalid event name", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    if (!isValidName(eventDto.getDescription())) {
      throw new InvalidEventException("Invalid event description", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    if(eventDto.getType()!=0 && eventDto.getType()!=1){
      throw new InvalidEventException("Invalid event type", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    if(eventDto.getPrice()<=0){
      throw new InvalidEventException("Invalid event price", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    List<Long> userIdList = new ArrayList<>();
    userIdList.add(eventDto.getCustomPairIdCoefficientPaying().getId());
    if (eventDto.getCustomPairIdCoefficientPaying().getCoefficient()<0)
      throw new InvalidEventException("Coefficient < 0", HttpStatus.UNPROCESSABLE_ENTITY);
    for(CustomPairIdCoefficient user : eventDto.getCustomPairIdCoefficientList()){
      if (user.getCoefficient()<0)
        throw new InvalidEventException("Coefficient < 0", HttpStatus.UNPROCESSABLE_ENTITY);
      userIdList.add(user.getId());
    }

    Set<Long> setUserId = new HashSet<>(userIdList);
    if(setUserId.size()<userIdList.size()){
      throw new InvalidEventException("Invalid usersId (event)", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }
}
