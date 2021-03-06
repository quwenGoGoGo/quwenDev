package quwen.web.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import quwen.db.domain.Category;
import quwen.db.domain.Comment;
import quwen.db.domain.News;
import quwen.db.domain.User;
import quwen.db.service.CommentService;
import quwen.db.service.NewsService;
import quwen.db.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private CommentService commentService;



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
        for (User eachUser:users) {
            eachUser.updateComment_count();
            eachUser.updateCollected_count();
        }
        int user_count = users.size();
        model.addAttribute("user_count",user_count);
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
//        user.setComment_count(Integer.parseInt(map.get("comment_count").toString()));
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

    @RequestMapping("delAll")
    public void batchDeletes(HttpServletRequest request, HttpServletResponse response) {
        String items = request.getParameter("delitems");// System.out.println(items);
        String[] strs = items.split(",");

        for (int i = 0; i < strs.length; i++) {
            try {
                Long a = Long.parseLong(strs[i]);
                userService.deleteUser(a);
            } catch (Exception e) {
            }
        }
    }

    @RequestMapping("search")
    public String searchForNews(@RequestParam("searchByName")String name, Model model){
        User user = new User();
        user.setNickname(name);
        List<User> users = userService.findSearch(user);
        model.addAttribute("users",users);
        return "user-list";
    }

    @RequestMapping("welcome")
    public String welcome(Model model){
        List<User> users = userService.getAllUsers();
        int user_count = users.size();
        model.addAttribute("user_count",user_count);

        List<News> news = newsService.getAllNews();
        int news_count = news.size();
        model.addAttribute("news_count",news_count);

        List<Comment> comments = commentService.getAllComment();
        int comment_count = comments.size();
        model.addAttribute("comment_count",comment_count);
        return "welcome";
    }

}
