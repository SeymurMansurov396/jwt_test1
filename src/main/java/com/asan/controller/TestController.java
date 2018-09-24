package com.asan.controller;

import com.asan.model.ApplicationUser;
import com.asan.model.ApplicationUserRole;
import com.asan.repository.ApplicationRoleRepository;
import com.asan.repository.ApplicationUserRepository;
import com.asan.service.ApplicationRoleService;
import com.asan.service.ApplicationUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.asan.security.SecurityConstants.EXPIRATION_TIME;
import static com.asan.security.SecurityConstants.SECRET;

@RestController
@RequestMapping("/test")
public class TestController {

    private Logger logger= LoggerFactory.getLogger(TestController.class);

    @Autowired
    private ApplicationUserService applicationUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ApplicationRoleService applicationRoleService;

    @GetMapping("/hello")
    public ResponseEntity<String> test(){

        logger.info("hello word");
        return new ResponseEntity<>("Hello word", HttpStatus.OK);
    }


    @GetMapping("/admin_page")
    public String admin_page(){

        List<ApplicationUserRole> roles= (List<ApplicationUserRole>) applicationUserService
                                         .findByUsername("asan_user")
                                          .getRoles();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "Admin Page: "+auth.getAuthorities();
    }



    @PostMapping("/generate_jwt")
    public String generateTestJWT(@RequestBody ApplicationUser user) {
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return  token;
    }
}
