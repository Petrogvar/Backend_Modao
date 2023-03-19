
//package com.SpringProject.core.Services;
//
//
//import com.SpringProject.core.Repository.UserRepository;
//import com.SpringProject.core.controllers.Error.UserNotGroupException;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//  private final UserRepository userRepository;
//
//  public CustomUserDetailsService(UserRepository userRepository) {
//    this.userRepository = userRepository;
//  }
//
//
////  @Override
////  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////    com.SpringProject.core.Entity.User user = userRepository.findByLogin(username);
////    if (user == null){
////      throw new UserNotGroupException();
////    }
////    UserDetails userDetails = User.builder()
////        .username(user.getLogin())
////        .password(user.getPassword())
////        .roles("user")
////        .build();
////    return userDetails;
////  }
//}
