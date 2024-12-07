package com.portfolio.my_note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.portfolio.my_note.model.User;
import com.portfolio.my_note.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/checkingLogin")
    public String postLogin(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            HttpSession session) {

        if (userService.checkUsername(username)){

            User userInDatabase = userService.getUser(username);

            if (password.length() > 7){
                if (password.equals(userInDatabase.getPassword())){
                
                    session.setAttribute("user", userInDatabase);
                    return "redirect:/home";
                }else {
                    System.out.println("redirect:/login?error=invalidCredentials");
                    return "redirect:/login?error=invalidCredentials";
                }
            }else {
                return "redirect:/login?error=invalidCredentials";
            }
        }else {
            return "redirect:/login?error=invalidCredentials";
        }
    }


    @GetMapping("/signup")
    public String getSignup(){
        return "signup";
    }


    @PostMapping("/checkingSignup")
    public String postSignup(@RequestParam("username")String username,
                            @RequestParam("newPassword")String newPassword, 
                            @RequestParam("confirmPassword")String confirmPassword, 
                            HttpSession session){

        if (newPassword.equals(confirmPassword)){

            if (confirmPassword.length() > 7){

                if (userService.checkUsername(username)){
                    return "redirect:/signup?error=userExists";
                }else {
    
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(confirmPassword);
    
                    session.setAttribute("user", user);
                    userService.save(user);
    
                    return "redirect:/login";
                }
            }else {
                return "redirect:/signup?error=passwordsNotSecure";
            }

        }else {
            return "redirect:/signup?error=passwordsNotMatch";
        }
        
    }
    
    

}
