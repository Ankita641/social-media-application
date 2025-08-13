package com.learning.socialmediablog.social_media_blog_app.config;


import com.learning.socialmediablog.social_media_blog_app.service.Impl.SocialMediaCustomizedUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    //This code work for HTTP basic security like login/password
   /* @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.GET, "v1/api/**").permitAll())
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService(){

//        UserDetails ankita = User.builder().username("ankita").password("ankita").roles("USER").build();
//        UserDetails admin = User.builder().username("admin").password("admin").roles("USER", "ADMIN").build();

        UserDetails ankita = User.builder().username("ankita").password(passwordEncoder().encode("ankita")).roles("USER").build();
        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("USER", "ADMIN").build();

        return new InMemoryUserDetailsManager(ankita, admin);
    }*/





    //This code work for JDBC based authentication and authorization

    private final SocialMediaCustomizedUserDetailService socialMediaCustomizedUserDetailService;

    @Autowired
    public SecurityConfig(SocialMediaCustomizedUserDetailService socialMediaCustomizedUserDetailService) {
        this.socialMediaCustomizedUserDetailService = socialMediaCustomizedUserDetailService;
    }


  /*  @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
       *//* AuthenticationManagerBuilder authBuilder =  http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder
                .userDetailsService(socialMediaCustomizedUserDetailService)
                .passwordEncoder(passwordEncoder);*//*
        return authenticationConfiguration.getAuthenticationManager();
    }*/

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(socialMediaCustomizedUserDetailService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        //return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
        //return new CustomPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.GET, "v1/api/**").permitAll())
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
             /*   .authorizeHttpRequests(authorize -> authorize.requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/Ankita/**").hasRole("USER"))*/
               // .formLogin(Customizer.withDefaults());
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

}
