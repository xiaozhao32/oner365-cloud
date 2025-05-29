package com.oner365.keycloak.controller;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.web.controller.BaseController;

/**
 * API User Controller
 *
 * @author zhaoyong
 */
@RestController
@RequestMapping("/user")
public class ApiUserController extends BaseController {

    /**
     * Roles user
     * 
     * @return String
     */
    @GetMapping
    @RolesAllowed("USER")
    public String userEndpoint() {
        return "Hello User";
    }

}
