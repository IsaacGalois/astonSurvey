package com.aston.survey.configuration;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.authentication.method}")
    private String authenticationMethod;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        if (authenticationMethod.equals("IN_MEMORY")) {         //In memory auth
            auth.inMemoryAuthentication().withUser("astonAdmin").password("qwe123$!").roles("ADMIN");
        }
        else if(authenticationMethod.equals("DATABASE")) {    //Database auth todo: make USER class and some seeded defaults
            JdbcUserDetailsManager userDetailsService = new JdbcUserDetailsManager();
            userDetailsService.setDataSource(dataSource);
            PasswordEncoder encoder = new BCryptPasswordEncoder();

            auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
            auth.jdbcAuthentication().dataSource(dataSource);

            //create user with role USER if none exists (Maybe add later if greater auth is introduced)
//            if(!userDetailsService.userExists("user")) {
//                List<GrantedAuthority> authorities = new ArrayList<>();
//                authorities.add(new SimpleGrantedAuthority("USER"));
//                User userDetails = new User("user", encoder.encode("password"), authorities);
//
//                userDetailsService.createUser(userDetails);
//            }
        }
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        if (authenticationMethod.equals("NONE")) {

            //permit access to everything for everyone
            httpSecurity
                    .authorizeRequests().antMatchers("/**").permitAll()
                    .and()
                    .authorizeRequests().antMatchers("/console/**").permitAll();

        } else if (authenticationMethod.equals("IN_MEMORY")) {

            //create authentication for ADMIN and anything with the URL=/admin/**, /api/**, or /console/**
            httpSecurity
                    .authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                    .and()
                    .authorizeRequests().antMatchers("/api/**").access("hasRole('ROLE_ADMIN')")
                    .and()
                    .authorizeRequests().antMatchers("/console/**").access("hasRole('ROLE_ADMIN')");

        } else if(authenticationMethod.equals("DATABASE")) { //todo:test these lockdown conditions
            //create authentication for ADMIN and anything with the URL=/admin/** and /api/**
            httpSecurity
                    .authorizeRequests().antMatchers("/static/**").permitAll()
                    .and()
                    .authorizeRequests().antMatchers("/login**").permitAll()
                    .and()
                    .authorizeRequests().antMatchers("/admin/**").hasAuthority("ROLE_ADMIN").anyRequest().authenticated()
                    .and()
                    .authorizeRequests().antMatchers("/api/**").hasAuthority("ROLE_ADMIN").anyRequest().authenticated();
        }

        //use custom login page "/login" mapped to login.jsp by IndexController
        httpSecurity
                .formLogin().loginPage("/login").loginProcessingUrl("/login.do")                    //url->controller  url->action on jsp form
                .defaultSuccessUrl("/", true).failureUrl("/login?err=1")    //success and failure urls
                .usernameParameter("username").passwordParameter("password");                       //username and password names on login.jsp

        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }
}