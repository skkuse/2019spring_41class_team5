package com.dealistic.controllers;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dealistic.beans.*;
@Controller
public class UserRegistrationControlle {
  @RequestMapping(method = RequestMethod.POST, value="/register/user")
  @ResponseBody
  public UserRegistrationReply registeruser(@RequestBody User user) {
  System.out.println("In registeruser");
        UserRegistrationReply stdregreply = new UserRegistrationReply();           
        UsersList.getInstance().add(user);
        //We are setting the below value just to reply a message back to the caller
        stdregreply.setName(user.getName());
        stdregreply.setAge(user.getAge());
        stdregreply.setRegistrationNumber(user.getRegistrationNumber());
        stdregreply.setRegistrationStatus("Successful");
        return stdregreply;
}
}