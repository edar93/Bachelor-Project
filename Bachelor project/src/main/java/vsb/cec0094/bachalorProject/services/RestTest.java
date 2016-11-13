package vsb.cec0094.bachalorProject.services;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest")
public class RestTest{

    @RequestMapping(method = RequestMethod.GET, value="/test")
    @ResponseBody
    public String test(){
      return "Ahoj :)";
    };
}
