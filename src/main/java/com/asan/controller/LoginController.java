package com.asan.controller;

import com.asan.model.ApplicationUser;
import com.asan.model.ApplicationUserRole;
import com.asan.service.ApplicationRoleService;
import com.asan.service.ApplicationUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.asan.security.SecurityConstants.EXPIRATION_TIME;
import static com.asan.security.SecurityConstants.SECRET;

@RestController
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private ApplicationUserService applicationUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ApplicationRoleService applicationRoleService;

    @PostMapping("/login-url")
    public ResponseEntity<String> login(@RequestBody ApplicationUser login) throws ServletException {


        if (login.getUsername() == null || login.getPassword() == null) {
            throw new ServletException("Please fill in username and password");
        }

        String username = login.getUsername();
        String password = login.getPassword();

        ApplicationUser userDB = applicationUserService.findByUsername(username);

        if (userDB == null) {
            throw new ServletException("User not found with username: " + username);
        }

        String pwdDB = userDB.getPassword();


        if (!bCryptPasswordEncoder.matches(password, userDB.getPassword())) {
            throw new ServletException("Invalid login. Please check your name and password.");
        }

        String token = Jwts.builder()
                .setSubject(userDB.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        if (token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        HttpHeaders responseHeaders = new HttpHeaders();

        responseHeaders.set("token", token);
        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<ApplicationUser> signUp(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        logger.info("-> {}" + user);

        List<ApplicationUserRole> roles = Arrays.asList(applicationRoleService.findByRole("ROLE_USER"));
        user.setRoles(roles);
        ApplicationUser applicationUser = applicationUserService.save(user);
        return new ResponseEntity<>(applicationUser, HttpStatus.CREATED);

    }
}


