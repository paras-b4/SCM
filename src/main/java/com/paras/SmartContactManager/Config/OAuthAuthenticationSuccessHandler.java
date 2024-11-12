package com.paras.SmartContactManager.Config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.paras.SmartContactManager.helper.AppConstants;

import com.paras.SmartContactManager.model.user;
import com.paras.SmartContactManager.repsitories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements  AuthenticationSuccessHandler {

    Logger logger= LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);
    @Autowired
    private UserRepo repo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
                logger.info("OAuthAuthenticationSuccessHandler");
                new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

                var oauth2AuthenicationToken=(OAuth2AuthenticationToken) authentication;
                String authorizedClientRegistrationId=oauth2AuthenicationToken.getAuthorizedClientRegistrationId();
                logger.info(authorizedClientRegistrationId);
                var oauthUser=(DefaultOAuth2User) authentication.getPrincipal();
                oauthUser.getAttributes().forEach((key,value)->{
                    logger.info(key+ " + "+value);

                });

                user user=new user();
                user.setUserId(UUID.randomUUID().toString());
                user.setRolelist(List.of(AppConstants.ROLE_USER));
                user.setEmailVerified(true);
                user.setEnabled(true);
                user.setPassword("dummy");
                if(authorizedClientRegistrationId.equalsIgnoreCase("google"))
                {
                    user.setEmail(oauthUser.getAttribute("email").toString());
                    user.setProfilepic(oauthUser.getAttribute("picture").toString());
                    user.setName(oauthUser.getAttribute("name").toString());
                    user.setProviderUserId(oauthUser.getName());
                    user.setAbout("this account is created with google");
                }
                else 
                {
                    logger.info("OAuthAuthenicationSuccessHandler : Unknown provider");

                }

                
               
            // DefaultOAuth2User user =(DefaultOAuth2User)authentication.getPrincipal();
            // // logger.info(user.getName());
            // // user.getAttributes().forEach((key,value)->{
            // //     logger.info("{}=>{}",key,value);
            // // });
            // // logger.info(user.getAuthorities().toString());
            // String email=user.getAttribute("email").toString();
            // String name=user.getAttribute("name").toString();
            // String picture=user.getAttribute("picture").toString();

            // user user1=new user();
            // user1.setEmail(email);
            // user1.setName(name);
            // user1.setProfilepic(picture);
            // user1.setPassword("password");
            // user1.setUserId(UUID.randomUUID().toString());
            // user1.setProvider(Providers.Google);
            // user1.setEnabled(true);
            // user1.setEmailVerified(true);
            // user1.setProviderUserId(user.getName());
            // user1.setRolelist(List.of(AppConstants.ROLE_USER));
            // user1.setAbout("this account is created using google");
            
            user user2=repo.findByEmail(user.getEmail()).orElse(null);
            if(user2==null)
            {
                repo.save(user);
               
            }
            

            
    }

}
