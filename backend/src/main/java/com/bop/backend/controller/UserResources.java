package com.bop.backend.controller;

import com.bop.backend.model.Pot;
import com.bop.backend.model.User;
import com.bop.backend.service.UserPotService;
import com.bop.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class UserResources {

    @Autowired
    private UserService userService;
    @Autowired
    private UserPotService userPotService;

    @RequestMapping("/user/users")
    public String loginSuccess(){
        return "login successful";
    }

    @RequestMapping(value = "/user/telephone", method = RequestMethod.POST)
    public User findByTelephone(@RequestBody int telephone)
    {
        return userService.findByTelephone(telephone);
    }

    @RequestMapping(value = "/user/potlist", method = RequestMethod.POST)
    public List<Pot> getPotsByTelephone(@RequestBody int telephone)
    {
        return userPotService.getPotList(telephone);
    }

//    @RequestMapping(value = "/user/id/pots", method = RequestMethod.POST)
//    public List<Pot> getAllUserPots(@RequestBody int telephone) {
//        return userPotService.getPotsByTelephone(telephone);
//    }
}
