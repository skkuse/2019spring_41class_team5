package edu.skku.dealistic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommonController2 {

    @RequestMapping("/2")
    public @ResponseBody String root_test() throws Exception{
        return "Hello Root2(/)";
    }

    @RequestMapping("/demo2")
    public @ResponseBody String demo_test() throws Exception{
        return "Hello demo2(/demo)";
    }

}
