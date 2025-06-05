package com.oner365.keycloak.controller;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.UserInfo;
import org.springframework.http.ResponseEntity;
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
     * @return ResponseEntity
     */
    @GetMapping("/userinfo")
    public ResponseEntity<UserInfo> getUserInfo(KeycloakAuthenticationToken authentication) {
        AccessToken accessToken = authentication.getAccount().getKeycloakSecurityContext().getToken();

        UserInfo userInfo = new UserInfo();
        userInfo.setPreferredUsername(accessToken.getPreferredUsername());
        userInfo.setEmail(accessToken.getEmail());
        userInfo.setGivenName(accessToken.getGivenName());
        userInfo.setFamilyName(accessToken.getFamilyName());

        return ResponseEntity.ok(userInfo);
    }

}
