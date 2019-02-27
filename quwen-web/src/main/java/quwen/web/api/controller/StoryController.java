package quwen.web.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import quwen.db.domain.Category;
import quwen.db.domain.NewsStory;
import quwen.db.service.StoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("story")
public class StoryController {
    @Autowired
    private  StoryService storyService;

    @GetMapping("toList")
    public String getStoryList(Model model){
        List<NewsStory> stories = storyService.findAll();
        model.addAttribute("stories",stories);
        return "story_list";
    }

    @RequestMapping("edit/{id}")
    public String edit(Model model, @PathVariable("id")Long id){
        if(id>0){
            model.addAttribute("isAdd",false);
            model.addAttribute("story",storyService.findByStoryID(id));
        }else{
            model.addAttribute("isAdd",true);
            model.addAttribute("story",new NewsStory());
        }
        return "story_edit";
    }

    @PostMapping("save")
    @ResponseBody
    public String save(@RequestParam HashMap<String,Object> map){
        if(map == null || map.size() ==0){
            return null;
        }
        NewsStory newsStory = new NewsStory();
        newsStory.setKeywords(map.get("keywords").toString());
        newsStory.setTheme(map.get("theme").toString());

        if(map.get("storyID")!=null && (Long.parseLong(map.get("storyID").toString()))>0){
            String storyID = map.get("storyID").toString();
            newsStory.setStoryID(Long.parseLong(storyID));
            storyService.updateNewsStory(newsStory);

        }else {
            storyService.addNewsStory(newsStory);
        }
        return "success";
    }

    @RequestMapping("del")
    @ResponseBody
    public String delByID(@RequestParam HashMap<String,String> map){
        String id=map.get("id");
        Long storyID = Long.parseLong(id);
        storyService.deleteNewsStory(storyID);
        return "success";
    }

    @RequestMapping("delAll")
    public void batchDeletes(HttpServletRequest request, HttpServletResponse response) {
        String items = request.getParameter("delitems");// System.out.println(items);
        String[] strs = items.split(",");

        for (int i = 0; i < strs.length; i++) {
            try {
                Long a = Long.parseLong(strs[i]);
                storyService.deleteNewsStory(a);
            } catch (Exception e) {
            }
        }
    }


}
