package edu.skku.dealistic;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @RequestMapping("/")
    public String root_test() throws Exception{
        return "Hello Root(/)";
    }

    @RequestMapping("/demo")
    public String demo_test() throws Exception {
        return "Hello demo(/demo)";
    }

}
