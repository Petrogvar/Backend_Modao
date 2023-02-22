package com.SpringProject.core.configuration;



import com.SpringProject.core.Services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
  private CustomUserDetailsService customUserDetailsService;

  @Autowired
  public void setCustomUserDetailsService(CustomUserDetailsService customUserDetailsService){
    this.customUserDetailsService = customUserDetailsService;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.csrf().disable();
    http.authorizeHttpRequests((authz) -> authz
            .antMatchers("/user/new").permitAll())
        .authorizeHttpRequests((authz) -> authz
            .anyRequest().authenticated());
    http.httpBasic();
    return  http.build();                        
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().antMatchers("/user/new");
  }
  @Bean
    public PasswordEncoder passwordEncoder(){
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider(){
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    authenticationProvider.setUserDetailsService(customUserDetailsService);
    return authenticationProvider;
  }

}