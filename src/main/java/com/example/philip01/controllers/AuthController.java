package com.example.philip01.controllers;

import com.example.philip01.models.ResponseObject;
import com.example.philip01.models.Users;
import com.example.philip01.repositories.UserRepository;
import com.example.philip01.helpers.Convert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {
    @Value("${maxRetries}")
    private int maxRetries;

    private static final Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    UserRepository userRepository;

    @PostMapping("")
    ResponseEntity<ResponseObject> loginUser(@RequestBody Users user) {
        int retries = 0;
        ResponseObject response;
        while(retries < maxRetries) {
            try {
                if (user.getUserName() == null || user.getPassWord() == null) {
                    response = new ResponseObject("0002", "Missing or invalid data. Please try again", "");
                    logger.warn(response);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
                else {
                    List<Users> foundUser = userRepository.findByUserName(user.getUserName().trim());
                    if (foundUser.size() > 0) {
                        String password = Convert.convertString2SHA1(user.getPassWord());
                        if (Objects.equals(password, foundUser.get(0).getPassWord())) {
                            response = new ResponseObject("0000", "Login successfully", foundUser);
                            logger.info(response);
                            return ResponseEntity.status(HttpStatus.OK).body(response);
                        }
                        else {
                            response = new ResponseObject("0004", "Incorrect password", "");
                            logger.warn(response);
                            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                        }
                    }
                    else {
                        response = new ResponseObject("0003", "Account not found", "");
                        logger.warn(response);
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                    }
                }
            }
            catch (Exception e) {
                retries += 1;
            }
        }
        response = new ResponseObject("0005", "Login failed. Please try again.", "");
        logger.error(response);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
    }
}
