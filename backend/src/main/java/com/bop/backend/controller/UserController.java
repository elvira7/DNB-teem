package com.bop.backend.controller;


import com.bop.backend.model.User;
import com.bop.backend.model.UserPot;
import com.bop.backend.service.UserPotService;
import com.bop.backend.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

// rest annotation also convert/parse to/from json
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserPotService userPotService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User registerUser(@RequestBody Map<String, String> json) throws ServletException {
        User userFromJson = new User();

        try{
            userFromJson.setTelephone(Integer.parseInt(json.get("telephone")));
        }catch (NumberFormatException e){
            System.out.println("Please enter a valid phone number");
        }


        userFromJson.setPassword(json.get("password"));
        userFromJson.setFirstName(json.get("firstName"));
        userFromJson.setLastName(json.get("lastName"));
        userFromJson.setDnbCustomer(false);
        userFromJson.setScore(0d);
        userFromJson.setCreated(new Date());
        //System.out.println(json.get("referenceNumber"));

//        String temp = json.get("referenceNumber");
//
//        int reference = temp != null?Integer.parseInt(json.get("referenceNumber")):0;
//        //int reference = Integer.parseInt(json.get("referenceNumber").toString());
//        //System.out.println(reference);



        User userFromDb = userService.findByTelephone(userFromJson.getTelephone());
        if (userFromDb!=null){
            throw new ServletException("Phone number already in use");
        }

//        User luckyUser = userService.findByTelephone(reference);
//
//
//        if (luckyUser!=null){
//            System.out.println(luckyUser.getFirstName());
//            luckyUser.setScore(luckyUser.getScore()+100);
//        }


//        if (!"".equals(temp) || temp!=null){
        if (json.containsKey("referenceNumber")){
            String temp = json.get("referenceNumber").trim();
            if (!"".equals(temp) || temp!=null) {
                try {
                    int reference = Integer.parseInt(json.get("referenceNumber"));
                    try {
                        User luckyUser = userService.findByTelephone(reference);
                            System.out.println(luckyUser.getFirstName());
                            luckyUser.setScore(luckyUser.getScore()+100);
                    }
                    catch (Exception e) {
                        throw new Exception("User does not exist");
                    }
                }
                catch (Exception e) {
                    System.out.println(e.toString());
                    throw new NumberFormatException("Wrong input format for field reference");
                }
            }
        }

        return userService.save(userFromJson);
    }

    // json convert to object User
//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public User registerUser(@RequestBody User user) throws ServletException {
//        User userFromDb = userService.findByTelephone(user.getTelephone());
//        if (userFromDb!=null){
//            throw new ServletException("User already exists");
//        }
//        return userService.save(user);
//    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestBody Map<String, String> json) throws ServletException
    {
        if (json.get("telephone")==null || json.get("password")==null)
            throw new ServletException("Please fill in username and password");


        int telephone = Integer.parseInt(json.get("telephone"));
        String password = json.get("password");

        User user = userService.findByTelephone(telephone);


        if (user == null)
            throw new ServletException("User name not found");

        String pwd = user.getPassword();
        if (!password.equals(pwd))
            throw new ServletException("Invalid login. Please check your name and password");

        // change secret key to real string
        return Jwts.builder().setSubject(String.valueOf(telephone)).claim("roles", "user").setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secretkey").compact();

    }

    @RequestMapping(value = "/totalinvestment", method = RequestMethod.POST)
    public float getTotalInvestmentsById(@RequestBody Map<String, String> json) throws ServletException
    {
        List<UserPot> investments = userPotService.getUserPotListByTelephone(Integer.parseInt(json.get("telephone")));
        float myInvestments = 0;

        for (UserPot investment: investments) myInvestments += investment.getInvested();

        return myInvestments;

    }

    @RequestMapping(value = "/investments", method = RequestMethod.POST)
    public List<UserPot> getInvestmentsById(@RequestBody Map<String, String> json) throws ServletException
    {
        return userPotService.getUserPotListByTelephone(Integer.parseInt(json.get("telephone")));

    }




}
