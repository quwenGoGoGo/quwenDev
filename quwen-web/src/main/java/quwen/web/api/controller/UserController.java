package quwen.web.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import quwen.db.domain.Category;
import quwen.db.domain.User;
import quwen.db.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    public UserController ( UserService userService ){
        this.userService = userService;
    }
    @RequestMapping(value = "/login" ,method = RequestMethod.GET)
    public String login(@Valid User user, Model model){
        userService.login(user,model);
        return "hello";
    }

    @GetMapping("tolist")
    public String getUserList(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users",users);
        return "user-list";
    }

    @RequestMapping("edit/{id}")
    public String eidt(Model model, @PathVariable("id")Long id){
        if(id>0){
            model.addAttribute("isAdd",false);
            model.addAttribute("user",userService.getUserByID(id));
        }
        else{
            model.addAttribute("isAdd",true);
            model.addAttribute("user",new User());
        }
        return "user-edit";
    }

    @PostMapping("save")
    @ResponseBody
    public String save(@RequestParam HashMap<String,Object> map){
        if(map == null || map.size() ==0){
            return null;
        }
        User user = new User();
        user.setNickname((String)map.get("nickname"));
        user.setCollect_count(Integer.parseInt(map.get("collect_count").toString()));
        user.setComment_count(Integer.parseInt(map.get("comment_count").toString()));
        if(map.get("userID")!=null && (Long.parseLong(map.get("userID").toString()))>0){
            String userID = map.get("userID").toString();
            System.out.println("edit"+map.get("userID").toString());
            user.setUserID(Long.parseLong(userID));
            userService.updateUser(user);

        }else {
            userService.addUser(user);
        }
        return "success";
    }

    @RequestMapping("list")
    public List<User> getAllUser(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("userList",users);
        return null;
    }

    @RequestMapping("del")
    @ResponseBody
    public String delByID(@RequestParam HashMap<String,String> map){
        String userIDs=map.get("id");
        Long id = Long.parseLong(userIDs);
//        System.out.println("cateIDdel:"+id);
        userService.deleteUser(id);
        return "success";
    }

}
