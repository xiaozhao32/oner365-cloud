package com.oner365.keycloak.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.web.controller.BaseController;

/**
 * API Admin Controller
 *
 * @author zhaoyong
 */
@RestController
@RequestMapping("/admin")
public class ApiAdminController extends BaseController {

    /**
     * Roles admin
     * 
     * @return String
     */
    @GetMapping
    public String adminEndpoint() {
        return "Hello Admin";
    }

    /**
     * UserInfo
     * 
     * @param authentication Keycloak Auth
     * @return Map<String, Object>
     */
    @GetMapping("/userinfo")
    public Map<String, Object> getUserInfo(HttpServletRequest request) {
        KeycloakPrincipal<?> principal = (KeycloakPrincipal<?>) request.getUserPrincipal();
        if (principal == null) {
            return null;
        }
        AccessToken accessToken = principal.getKeycloakSecurityContext().getToken();
        if (accessToken != null) {
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("userName", accessToken.getPreferredUsername());
            userInfo.put("email", accessToken.getEmail());
            userInfo.put("givenName", accessToken.getGivenName());
            userInfo.put("familyName", accessToken.getFamilyName());
            return userInfo;
        }
        return null;
    }

}
