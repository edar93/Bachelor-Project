package vsb.cec0094.bachalorProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import vsb.cec0094.bachalorProject.dao.TestDao;
import vsb.cec0094.bachalorProject.models.Test;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/rest")
public class RestTest{

    @Autowired
    private TestDao testDao;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value="/test")
    @ResponseBody
    public ArrayList<String> test(){
      //return "Ahoj";
        ArrayList<String> array = new ArrayList<>();
        array.add("ahoj");
        array.add("svete");
        return array;
    };

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value="/testlist")
    @ResponseBody
    public List<Test> testList(){
        return testDao.getAllTest();
    };
}
