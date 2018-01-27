package com.bop.backend.controller;

import com.bop.backend.model.Pot;
import com.bop.backend.model.User;
import com.bop.backend.model.UserPot;
import com.bop.backend.service.PotService;
import com.bop.backend.service.UserPotService;
import com.bop.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class PotResources {

    @Autowired
    private PotService potService;
    @Autowired
    private UserPotService userPotService;
//    @Autowired
//    private UserService userService;
//    @RequestMapping("/user/users")
//    public String loginSuccess(){
//        return "login successful";
//    }

    @RequestMapping(value = "/pot/id", method = RequestMethod.POST)
    public Pot findByPotId(@RequestBody int potId) {
        return potService.findByPotId(potId);
    }

    @RequestMapping(value = "/pot/id/name", method = RequestMethod.POST)
    public Pot setNameToPot(@RequestBody Map<String, String> json ){

        Integer potId = Integer.parseInt(json.get("potId"));
        String name = json.get("name");

        Pot pot = potService.findByPotId(potId);
        if (pot!=null){
            pot.setName(name);
            potService.save(pot);
        }
        return pot;
    }

    @RequestMapping(value = "/pot/userlist", method = RequestMethod.POST)
    public List<User> getMemberList(@RequestBody int potId)
    {
        return userPotService.getUserList(potId);
    }

    @RequestMapping(value = "/pot/userpot", method = RequestMethod.POST)
    public UserPot getUserPot(@RequestBody Map<String, String> json)
    {

        Integer potId = Integer.parseInt(json.get("potId"));
        Integer userId = Integer.parseInt(json.get("userId"));

        return userPotService.findByUserIdPotId(userId, potId);
    }
// /rest/pot/id
}

