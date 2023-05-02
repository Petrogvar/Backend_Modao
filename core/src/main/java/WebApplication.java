import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.SpringProject.core"})
public class WebApplication{
  public static void main(String[] args){ SpringApplication.run(WebApplication.class, args);}
}

