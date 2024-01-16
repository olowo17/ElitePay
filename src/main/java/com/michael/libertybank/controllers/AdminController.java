package com.michael.libertybank.controllers;

import com.michael.libertybank.services.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
@AllArgsConstructor
public class AdminController {
    AdminService adminService;
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
}
