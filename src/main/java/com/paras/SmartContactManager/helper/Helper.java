package com.paras.SmartContactManager.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {
        
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
            OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal) oauth2Token.getPrincipal();
            String clientId = oauth2Token.getAuthorizedClientRegistrationId();

            if ("google".equalsIgnoreCase(clientId)) {
                System.out.println("Getting email from Google");
                // Retrieve and return the email attribute
                return principal.getAttribute("email");
            }
        } else {
            System.out.println("Getting data from local database");
            return authentication.getName();  // Return username if not OAuth2
        }

        return "no user found"; // Return null if no email was found or authentication type is not recognized
    }
    
}
