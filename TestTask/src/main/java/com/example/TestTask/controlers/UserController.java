package com.example.TestTask.controlers;

import com.example.TestTask.Entity.UserEntity;
import com.example.TestTask.Exceptions.UserAlreadyExistException;
import com.example.TestTask.Exceptions.UserInvalidException;
import com.example.TestTask.Exceptions.UserNotFoundException;
import com.example.TestTask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity createUser(@RequestBody UserEntity user) {
        try {
        userService.createUser(user);
            return ResponseEntity.ok("createUser its ok");
        }
        catch (UserAlreadyExistException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch(UserInvalidException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("error");
        }
    }

    @GetMapping()
    public ResponseEntity getUser(@RequestParam Long id){
        try {
            return ResponseEntity.ok(userService.getUser(id));
        }catch (UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/update/firstName")
    public ResponseEntity updateFirstName(@RequestBody UserEntity user) {
        try {
            userService.updateFirstName(user);
            return ResponseEntity.ok("update User its ok "+ user.getFirstName() );
        }
        catch (UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch(UserInvalidException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/update/fullname")
    public ResponseEntity updateFullName(@RequestBody UserEntity user) {
        try {
            userService.updateFullName(user);
            return ResponseEntity.ok("update User its ok "+ user.getFirstName() );
        }
        catch (UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch(UserInvalidException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/update/all")
    public ResponseEntity updateAll(@RequestBody UserEntity user) {
        try {
            userService.updateAll(user);
            return ResponseEntity.ok("update User its ok "+ user.getFirstName() );
        }
        catch (UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch(UserInvalidException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        try {
            return ResponseEntity.ok(userService.deleteUser(id));
        }


        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/usersByBirthdayRange")
    public ResponseEntity<?> getUsersByBirthdayRange(
            @RequestParam("startDate") String startDateStr,
            @RequestParam("endDate") String endDateStr) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            if (startDate.after(endDate)) {
                return ResponseEntity.badRequest().body("Start date cannot be after end date.");
            }

            List<UserEntity> users = userService.getUsersByBirthdayRange(startDate, endDate);
            return ResponseEntity.ok(users);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format. Expected format: yyyy-MM-dd.");
        }
    }




}

