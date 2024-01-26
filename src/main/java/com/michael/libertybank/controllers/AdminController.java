package com.michael.libertybank.controllers;

import com.michael.libertybank.repository.UserRepository;
import com.michael.libertybank.services.AdminService;
import com.michael.libertybank.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
@AllArgsConstructor
public class AdminController {
    AdminService adminService;
    UserService userService;
    @DeleteMapping("/account_no/{accountNumber}")
    public ResponseEntity<?> deleteAccount(@PathVariable String accountNumber){
       var response = adminService.deleteAccount(accountNumber);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/accounts")
    public ResponseEntity<?> deleteAllAccounts(){
        var res= adminService.deleteAllAccounts();
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
    @DeleteMapping("/user/email")
    public String deleteUser(@RequestParam String email){
        return userService.deleteUser(email);
    }
}
