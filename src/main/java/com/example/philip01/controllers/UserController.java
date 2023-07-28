package com.example.philip01.controllers;

import com.example.philip01.helpers.Convert;
import com.example.philip01.models.ResponseObject;
import com.example.philip01.models.Users;
import com.example.philip01.repositories.UserRepository;
import org.apache.catalina.User;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
    @Value("${maxRetries}")
    private int maxRetries;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    UserRepository userRepository;
    @GetMapping("")
    List<Users> getAllUsers (){
        return userRepository.getAllUsers();
    }

    @GetMapping("/{userName}")
    ResponseEntity<ResponseObject> findByUserName(@PathVariable String userName) {
        logger.error(new ResponseObject("0000", "OK", "abc"));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("0000", "Found user successfully", userRepository.findByUserName(userName))
        );
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> createUser(@RequestBody Users newUser) {
        int retries = 0;
        ResponseObject response;
        System.out.println(maxRetries);
        while(retries < maxRetries) {
            try {
                if (newUser.getUserName() == null || newUser.getPassWord() == null) {
                    response = new ResponseObject("0002", "Missing or invalid data. Please try again", "");
                    logger.warn(response);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
                List<Users> foundUsers = userRepository.findByUserName(newUser.getUserName().trim());
                if (foundUsers.size() > 0) {
                    response = new ResponseObject("0001", "UserName already exists", "");
                    logger.warn(response);
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
                }
                newUser.setPassWord(Convert.convertString2SHA1(newUser.getPassWord()));
                System.out.println(newUser.getPassWord());
                response = new ResponseObject("0000", "Successfully registered", userRepository.saveUser(newUser));
                logger.info(response);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            catch (Exception e) {
                retries++;
            }
        }
        response = new ResponseObject("0005", "Registration failed. Please try again.", "");
        logger.error(response);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @DeleteMapping("/clearUsers")
    void clearUsers() {
        userRepository.clearAllUsers();
    }

    @PostMapping("/create-more-users")
    ResponseEntity<ResponseObject> createUsers() {
        userRepository.createMoreUsers();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("0000", "Insert Users successfully", "")
        );
    }
}
