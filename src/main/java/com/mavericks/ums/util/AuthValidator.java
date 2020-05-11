/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mavericks.ums.util;

import com.mavericks.ums.dao.UserDao;
import com.mavericks.ums.model.User;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nabin
 */
public class AuthValidator {
    public static Map<String,String> validateForLogin(User user,UserDao dao) throws SQLException{
        Map<String,String> errors = new HashMap<>();
        User existingUser = dao.getUserByUsermame(user.getUsername());
        if(existingUser == null){
                errors.put("username","Username doesn't exist");
        }
        else{
            if(!existingUser.getPassword().equals(user.getPassword())){
                errors.put("password","Password didn't match");
            }
        }
        return errors;
    }
    public static Map<String,String> validateForRegister(User user,UserDao dao) throws SQLException{
        Map<String,String> errors = new HashMap<>();
        if(user.getUsername().length() < 6){
            errors.put("username","Username should be at least 6 characters long");
        }
        if(user.getPassword().length() < 8){
            errors.put("password","Password should be at least 8 characters length");
        }
        Pattern validEmailPattern =  Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher emailMatcher = validEmailPattern.matcher(user.getEmail());
        if(!emailMatcher.matches()){
            errors.put("email","Email is in incorrect format");
        }
        if(user.getFirstName().length() == 0){
            errors.put("firstName","Your first name is required");
        }
        if(user.getLastName().length() == 0){
            errors.put("lastName","Your last name is required");
        }
        Pattern validPhonePattern =  Pattern.compile("^\\d{10}$");
        Matcher phoneNumMatcher = validPhonePattern.matcher(String.valueOf(user.getPhoneNum()));
        if(! phoneNumMatcher.matches()){
            errors.put("phoneNum","Phone number is incorrect");
        }
        if(!errors.containsKey("username")){
            User existingUser = dao.getUserByUsermame(user.getUsername());
            if(existingUser != null){
                errors.put("username","Username is already taken");
            }
        }
        if(!errors.containsKey("email")){
            User existingUser = dao.getUserByEmail(user.getEmail());
            if(existingUser != null){
                errors.put("email","Email is already registered");
            }
        }
        return errors;
    }
    public static Map<String,String> validateForEditUser(User userPrev,User user,UserDao dao) throws SQLException{
        Map<String,String> errors = new HashMap<>();
        if(user.getUsername().length() < 6){
            errors.put("username","Username should be at least 6 characters long");
        }
        if(user.getPassword().length() < 8){
            errors.put("password","Password should be at least 8 characters length");
        }
        Pattern validEmailPattern =  Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher emailMatcher = validEmailPattern.matcher(user.getEmail());
        if(!emailMatcher.matches()){
            errors.put("email","Email is in incorrect format");
        }
        if(user.getFirstName().length() == 0){
            errors.put("firstName","Your first name is required");
        }
        if(user.getLastName().length() == 0){
            errors.put("lastName","Your last name is required");
        }
        Pattern validPhonePattern =  Pattern.compile("^\\d{10}$");
        Matcher phoneNumMatcher = validPhonePattern.matcher(String.valueOf(user.getPhoneNum()));
        if(!phoneNumMatcher.matches()){
            errors.put("phoneNum","Phone number is incorrect");
        }
        if(!errors.containsKey("username")){
            User existingUser = dao.getUserByUsermame(user.getUsername());
            if(existingUser != null && !existingUser.getUsername().equals(userPrev.getUsername())){
                errors.put("username","Username is already taken by another user");
            }
        }
        if(!errors.containsKey("email")){
            User existingUser = dao.getUserByEmail(user.getEmail());
            if(existingUser != null && !existingUser.getEmail().equals(userPrev.getEmail())){
                errors.put("email","Email is already registered for another user");
            }
        }
        return errors;
    }
    
}
