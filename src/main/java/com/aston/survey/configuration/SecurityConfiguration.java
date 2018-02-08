package com.aston.survey.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Value("${spring.security.authentication.method}")
    private String authenticationMethod;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //In memory authentication (DEV)
        if(authenticationMethod.equals("IN_MEMORY")) {
            auth.inMemoryAuthentication().withUser("astonAdmin").password("qwe123$!").roles("ADMIN");
        }
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        if (authenticationMethod.equals("IN_MEMORY")) {
            httpSecurity

                    //create authentication for ADMIN and anything with the URL=/admin/** or /console/**
                    .authorizeRequests().antMatchers("/statsHub/**").access("hasRole('ROLE_ADMIN')")
                    .and()
                    .authorizeRequests().antMatchers("/stats/**").access("hasRole('ROLE_ADMIN')")
                    .and()
                    .authorizeRequests().antMatchers("/console/**").access("hasRole('ROLE_ADMIN')");

//                .and()
//                .authorizeRequests().antMatchers("/").access("hasRole");

            //default login page
            httpSecurity.formLogin();
        }

        //use custom login page "/login" mapped to login.jsp by IndexController
        httpSecurity
                .formLogin().loginPage("/login").loginProcessingUrl("/login.do") //url->controller    url->action on jsp form
                .defaultSuccessUrl("/", true).failureUrl("/login?err=1")              //success and failure urls
                .usernameParameter("username").passwordParameter("password");   //username and password names on login.jsp

        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }
}