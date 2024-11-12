package com.paras.SmartContactManager.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.bind.annotation.GetMapping;

import com.paras.SmartContactManager.servicesImp.SecurityCustomUserDetailService;

// import jakarta.security.auth.message.config.AuthConfigProvider;

@Configuration
public class SecurityCongig {

    // @Bean
    // public UserDetailsService userDetailsService()
    // {
    //     UserDetails user1= User
    //                    .withDefaultPasswordEncoder()
    //                    .username("disha")
    //                    .password("d@123")
    //                    .roles("ADMIN")
    //                    .build();
    //            UserDetails user2= User
    //                    .withDefaultPasswordEncoder()
    //                    .username("Paras")
    //                    .password("p@123")
    //                    .roles("ADMIN")
    //                    .build();
            
        
    //     return new InMemoryUserDetailsManager(user1,user2);
    // }
    @Autowired
    private OAuthAuthenticationSuccessHandler handler;

    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        return daoAuthenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {

         http.authorizeHttpRequests(authorize->{authorize.requestMatchers("/user/**").authenticated();
        authorize.anyRequest().permitAll();} );
        
        http.formLogin(formLogin->{
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/profile");
           // formLogin.failureForwardUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
        });
        http.csrf(AbstractHttpConfigurer::disable);
        

        http.logout(logoutForm->{
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });
        http.oauth2Login(oauth-> {
            oauth.loginPage("/login");
            oauth.successHandler(handler); 
        });
        return http.build();
        
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }


}
