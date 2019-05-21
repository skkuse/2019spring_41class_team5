package com.dealistic.controllers;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dealistic.beans.User;
import com.dealistic.beans.UsersList;
@Controller
public class UserRetrieveController {
  @RequestMapping(method = RequestMethod.GET, value="/student/allstudent")
  @ResponseBody
  public List<User> getAllStudents() {
  return UsersList.getInstance().getUserRecords();
  }
}