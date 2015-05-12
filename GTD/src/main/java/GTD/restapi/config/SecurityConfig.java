package GTD.restapi.config;

import GTD.restapi.util.filters.AuthenticationTokenProcessingFilter;
import GTD.restapi.util.security.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Created by Drugnanov on 14.12.2014.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { //

    @Autowired
    private AuthenticationTokenProcessingFilter authenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/api/v1/authenticate/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/persons")
                .permitAll()
                .antMatchers("/**")
                .authenticated()
                .and()
                .addFilterBefore(authenticationTokenFilter, BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    }

    private AuthenticationEntryPoint entryPoint() {
        CustomAuthenticationEntryPoint entryPoint = new CustomAuthenticationEntryPoint();
        return entryPoint;
    }
}
