package com.openclassrooms.paymybuddy.controler;

import com.openclassrooms.paymybuddy.model.AuthentificationProvider;
import com.openclassrooms.paymybuddy.model.CustomOAuth2User;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private UserService userService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        User user = userService.findUserByEmail(email);
        if(email != null) {
            if(user == null) {
                System.out.println("New User");
                userService.saveNewUserAfterOAuthLoginSuccess(email, oAuth2User.getName(), AuthentificationProvider.GITHUB);
            } else {
                System.out.println("Existing user attempt to login");
                userService.updateUserAfterOAuthLoginSuccess(user, oAuth2User.getName(), AuthentificationProvider.GITHUB);
            }
        } else {
            throw new RuntimeException("Email not found from OAuth2 provider");
        }


        super.onAuthenticationSuccess(request, response, authentication);
    }
}
