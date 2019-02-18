package quwen.web.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import quwen.db.domain.User;
import quwen.db.service.UserService;

import javax.validation.Valid;

@Controller
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
}
