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

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
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

    @GetMapping("list")
    public String collectList(Model model) {
        List<Collect> collects=collectService.getAllCollect();
        model.addAttribute("collects",collects);
        return "collect-list";
    }
//    @GetMapping("ulist")
//    public String uCollectList(Model model) {
//        List<Collect> collects=collectService.getAllCollect();
//        model.addAttribute("collects",collects);
//        return "ucollect-list";
//    }

    @GetMapping("toList/{newsid}")
    public String getNewsCollects(Model model, @PathVariable("newsid") Long newsid){
        System.out.println(newsid);
        List<Collect> collects = collectService.findByNewsID(newsid);
        model.addAttribute("collects", collects);
        return "collect-list";
    }

    @RequestMapping("edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        System.out.println(id);
        if (id > 0) {
            System.out.println(id);
            model.addAttribute("isAdd", false);
            model.addAttribute("collect", collectService.getCollectByID(id));
        } else {
            model.addAttribute("isAdd", true);
            model.addAttribute("collect", new Collect());
        }
        System.out.println("edit");
        return "collect-edit";

    }

//    @PostMapping("save")
//    @ResponseBody
//    public String save(@ModelAttribute Collect collect){
//        if(collect == null)
//            return "fail";
//
//        System.out.println(collect.getCollectID());
//
//        if(collect.getCollectID()!= null && collect.getCollectID() > 0)
//           collectService.updateCollect(collect);
//        else
//            collectService.addCollect(collect);
//
//        return "redirect:/col/list";
//    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    //{"userName":"gogo","collectID":"1","time":"2019-02-19 19:01:39.0"}
//    @PostMapping("save")
//    @ResponseBody
//    public String save(@RequestParam HashMap<String,Object> map)throws ParseException{
//        if(map == null || map.size() ==0){
//            return null;
//        }
//        Collect collect = new Collect();
//        collect.setCollectID(Long.parseLong((String)map.get("collectID")));
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        collect.setTime(simpleDateFormat.parse((String)map.get("time")));
//        if((simpleDateFormat.parse((String)map.get("time")))!=null && (Long.parseLong(map.get("collectID").toString()))>0){
//            String colID = map.get("collectID").toString();
//            System.out.println("edit"+map.get("collectID").toString());
//            collect.setCollectID(Long.parseLong(colID));
//            collectService.updateCollect(collect);

//        }else {
//            collectService.addCollect(collect);
//        }
//        return "ok";
//    }

    @RequestMapping("add")
    @ResponseBody
    public String addNews(
                          @RequestParam(value = "collectID",defaultValue="0") Long collectID,
                          @RequestParam(value = "user.username") String username,
                          @RequestParam(value = "time") Date time,
                          @RequestParam(value = "new.newsID",defaultValue="0")Long newsID,
                          Model model) throws Exception{

        Collect collect=new Collect();
        collect.setCollectID(collectID);
        collect.setTime(time);
        User user =collectService.findUserByName(username);
        News news=newsService.findNewsByNewsID(newsID);
        collect.setUser(user);
        collect.setNews(news);
        collectService.addCollect(collect);
        return "success";
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

}
