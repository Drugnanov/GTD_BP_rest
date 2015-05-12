package GTD.restapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Drugnanov on 7.12.2014.
 */
@Configuration
@EnableWebMvc
//@ComponentScan(basePackages="GTD.restapi")
public class WebApplicationConfig extends WebMvcConfigurerAdapter {

  //ToDo Upravit maintenence pro konkrétní datum
//  @Value("${gtd.api.maintenence.start}")
//  private int maintenanceStartTime;
//  @Value("${gtd.api.maintenence.end}")
//  private int maintenanceEndTime;
//
//  @Override
//  public void addInterceptors(InterceptorRegistry registry) {
//    MaintenenceInterceptor maintenenceInterceptor = new MaintenenceInterceptor();
//    maintenenceInterceptor.setMaintenanceStartTime(maintenanceStartTime);
//    maintenenceInterceptor.setMaintenanceEndTime(maintenanceEndTime);
//    registry.addInterceptor(maintenenceInterceptor);
//  }
}
