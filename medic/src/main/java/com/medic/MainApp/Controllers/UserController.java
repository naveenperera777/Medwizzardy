package com.medic.MainApp.Controllers;
import com.medic.MainApp.Models.User;
import com.medic.MainApp.Response.MedicResponse;
import com.medic.MainApp.Response.ResponseMessages;
import com.medic.MainApp.Services.UserService;
import com.medic.MainApp.Utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Date;
import java.util.List;

@RestController
public class UserController extends ResponseUtils {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService ){
        this.userService = userService;
    }

    //Get details of all users
    @GetMapping("/users")
    public ResponseEntity getAllUsers(){
        List users = userService.getAllUsers();
        logger.info("users {}" ,users);
        if (!users.isEmpty()) {
            ResponseEntity responseEntity = successRetrieval(users);
            return responseEntity;
        }
        return FailedRetrieval(users);
    }

    //Get details of a user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity getUserById(@PathVariable String id){
        Object user = userService.getUserById(id);
        logger.info("user {}" , user);
        if (user != null){
            ResponseEntity responseEntity = successRetrieval(user);
            return responseEntity;
        }
        return FailedRetrieval(user);
    }

    //Save a user in the Database
    @PostMapping("/users")
    public void saveUser(@RequestBody User user){
        logger.info("userSave----->", user);
        userService.saveUser(user);
    }

    //Update user in the Database
    @PutMapping("/users/{id}")
    public void updateUser(@RequestBody User user, @PathVariable String id){
        logger.info("UserController--UpdateUser---->" , user);
        userService.updateUser(user);
    }

    @DeleteMapping("users/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id){
        logger.info("UserController--DeleteUser {}", id);
        userService.deleteUser(id);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }


}
