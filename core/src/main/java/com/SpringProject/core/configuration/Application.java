//package com.SpringProject.core.configuration;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
//@Configuration
//public class Application {
//  @Bean
//  public FilterRegistrationBean characterEncodingFilterRegistrationBean() {
//    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//    characterEncodingFilter.setForceEncoding(true);
//    characterEncodingFilter.setEncoding("UTF-8");
//    registrationBean.setFilter(characterEncodingFilter);
//    registrationBean.addUrlPatterns("/*");
//    return registrationBean;
//  }
//}
