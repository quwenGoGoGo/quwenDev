package quwen.web.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import quwen.db.domain.Collect;
import quwen.db.domain.News;
import quwen.db.domain.User;
import quwen.db.service.CollectService;
import quwen.db.service.NewsService;
import quwen.db.service.UserService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("col")
public class CollectController {
    @Autowired
    private CollectService collectService;

    @Autowired
    private NewsService newsService;

    @Autowired
    UserService userService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("list")
    public String collectList(Model model) {
        List<Collect> collects=collectService.getAllCollect();
        model.addAttribute("collects",collects);
        return "collect-list";
    }


    @GetMapping("toList/{newsid}")
    public String getNewsCollect(Model model, @PathVariable("newsid") Long newsid){
        System.out.println(newsid);
        model.addAttribute("newsID", newsid);
        List<Collect> collects = collectService.findByNews_NewsID(newsid);
        model.addAttribute("collects", collects);
        return "collect-list";
    }

    @GetMapping("toListUser/{userid}")
    public String getAllCollects(Model model, @PathVariable("userid") Long userid) {
        System.out.println(userid);
        model.addAttribute("userID", userid);
        List<Collect> collects = collectService.getAllCollectByUserID(userid);
        model.addAttribute("collects", collects);
        return "collect_list_user";
    }

    @RequestMapping("edit/{id}/{newsID}")
    public String edit(Model model, @PathVariable("id") Long id ,@PathVariable("newsID") Long newsID) {
        System.out.println(id);
        if (id > 0) {
            System.out.println(id);
            model.addAttribute("isAdd", false);
            model.addAttribute("collect", collectService.getCollectByID(id));
        } else {
            model.addAttribute("isAdd", true);
            model.addAttribute("collect", new Collect());
            model.addAttribute("newsID", newsID);
        }
        System.out.println("edit");
        return "collect-edit";

    }

    @PostMapping("save")
    @ResponseBody
    public String save(@ModelAttribute Collect collect, @RequestParam(value = "newsID") Long newsID){
        if(collect == null)
            return "fail";

        System.out.println(collect.getCollectID());

        News news = newsService.getNewsByID(newsID);
        collect.setNews(news);

        if(collect.getCollectID()!= null && collect.getCollectID() > 0)
            collectService.updateCollect(collect);
        else
            collectService.addCollect(collect);

        return "redirect:/col/list";
    }



    @RequestMapping("editUser/{id}/{userID}")
    public String editUser(Model model,@PathVariable("id") Long id, @PathVariable("userID") Long userID){
        System.out.println(id);

        if(id > 0){
            model.addAttribute("isadd", false);
            model.addAttribute("collect", collectService.getCollectByID(id));
            System.out.println(id);
        }
        else{
            model.addAttribute("isadd", true);
            model.addAttribute("collect", new Collect());
            model.addAttribute("userID", userID);
        }
        System.out.println("edit");

        return "collect_edit_user";
    }

    @PostMapping("saveuser")
    @ResponseBody
    public String saveUser(@ModelAttribute Collect collect,
                           @RequestParam(value = "userID") Long userID){
        if(collect == null)
            return "fail";

        System.out.println(collect.getCollectID());

        User user = userService.getUserByID(userID);
        collect.setUser(user);

        if(collect.getCollectID() != null && collect.getCollectID() > 0)
            collectService.updateCollect(collect);
        else
            collectService.addCollect(collect);

        return "redirect:/col/list";
    }

    @RequestMapping("del")
    @ResponseBody
    public String delByID(@RequestParam HashMap<String,String> map){
        String colIDs=map.get("id");
        Long id = Long.parseLong(colIDs);
        System.out.println("colIDdel:"+id);
        collectService.deleteCollectByID(id);
        return "success";
    }

    @RequestMapping("delAll")
    public void batchDeletes(HttpServletRequest request, HttpServletResponse response) {
        String items = request.getParameter("delitems");// System.out.println(items);
        String[] strs = items.split(",");

        for (int i = 0; i < strs.length; i++) {
            try {
                Long a = Long.parseLong(strs[i]);
                collectService.deleteCollectByID(a);
            } catch (Exception e) {
            }
        }
    }


}
