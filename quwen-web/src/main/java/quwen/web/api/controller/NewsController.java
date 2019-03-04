package quwen.web.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import quwen.db.domain.Category;
import quwen.db.domain.Collect;
import quwen.db.domain.News;
import quwen.db.domain.NewsStory;
import quwen.db.service.CategoryService;
import quwen.db.service.NewsService;
import quwen.db.service.StoryService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StoryService storyService;


    @GetMapping("index")
    public String index(){
        return "index";
    }

    @GetMapping("toList")
    public String getNewsList(Model model){
        List<News> news = newsService.getAllNews();
        for (News eachNews:news) {
            eachNews.updateComment_count();
            eachNews.updateCollected_count();
        }
        int news_count = news.size();
        model.addAttribute("news_count",news_count);
        model.addAttribute("news",news);
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categoryList",categories);
        return "news-list";
    }

    @GetMapping("news_story/{storyID}")
     public String getNewsStory(Model model, @PathVariable("storyID") Long storyID){
        NewsStory newsStory = storyService.findByStoryID(storyID);
        List<News> news = newsService.findNewsByStory(newsStory);
        model.addAttribute("news", news);
        return "news-list";
    }


    @RequestMapping("edit/{id}")
    public String edit(Model model, @PathVariable("id")Long id){

        if(id>0){
            model.addAttribute("isAdd",false);
            model.addAttribute("news",newsService.getNewsByID(id));
        }else{
            model.addAttribute("isAdd",true);
            model.addAttribute("news",new News());
        }
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categoryList",categories);
        return "news-edit";
    }

    @RequestMapping("add")
    @ResponseBody
    public String addNews(@RequestParam(value = "newsID") Long newsID,
            @RequestParam(value = "title") String title,
            @RequestParam(value = "cateName")String cateName,
            @RequestParam(value = "cover")MultipartFile file,
            @RequestParam(value = "content")String content,
            @RequestParam(value = "author")String author,
            @RequestParam(value = "switch") Integer status,
            @RequestParam(value = "stick") Integer stick,
            Model model) throws Exception{
        News news;
        if(newsID>0){
            news = newsService.getNewsByID(newsID);
            news.setNewsID(newsID);
            news.updateComment_count();
            news.updateCollected_count();
        } else{
            news = new News();
        }
        NewsStory newsStory = storyService.findByStoryID(Integer.toUnsignedLong(1));
        news.setNewsStory(newsStory);
        news.setTitle(title);
        Category category = categoryService.findByCateName(cateName);
        news.setCategory(category);
        news.setContent(content);
        news.setAuthor(author);
        news.setCtime(new Date());
        boolean isStatus=(status==1)?true:false;
        news.setStatus(isStatus);
        boolean isStick=(stick==1)?true:false;
        news.setStick(isStick);


        if(!file.isEmpty()){
            try{
                //获取文件名
                String fileName = file.getOriginalFilename();

                //获取当前路径
                String tomcat_path = System.getProperty( "user.dir" );

                //获取文件名的后缀
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                String filePath =tomcat_path + "\\quwen-wx-api\\src\\main\\resources\\static\\images\\";
                String newName = UUID.randomUUID()  + suffixName;
                String pathName = filePath + newName;
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(pathName)));
                out.write(file.getBytes());
                out.flush();
                out.close();
                //String picUrl = "http://localhost:8080/images/" + newName;//本地版
                String picUrl = "http://111.230.21.216:8080/images/" + newName;//服务器版
                file.transferTo(new File(pathName));
                news.setPicUrl(picUrl);
            }catch (FileNotFoundException e){
                e.printStackTrace();
                return "上传失败，"+ e.getMessage();
            }catch (IOException e){
                e.printStackTrace();
                return "上传失败，"+ e.getMessage();
            }
        }
        newsService.addNews(news);
        return "success";
    }

    @RequestMapping("delAll")
    public void batchDeletes(HttpServletRequest request, HttpServletResponse response) {
        String items = request.getParameter("delitems");// System.out.println(items);
        String[] strs = items.split(",");

        for (int i = 0; i < strs.length; i++) {
            try {
                Long a = Long.parseLong(strs[i]);
                newsService.deleteNews(a);
            } catch (Exception e) {
            }
        }
    }

    @RequestMapping("delete")
    public String deleteNews(Long id){
        newsService.deleteNews(id);
        return "redirect:/news/toList";
    }

    @RequestMapping("search")
    public String searchForNews(@RequestParam("searchByTitle")String stitle, @RequestParam("searchByCate")String scategory, Model model){
        Category category = categoryService.findByCateName(scategory);
        News newsModel = new News();
        newsModel.setTitle(stitle);
        newsModel.setCategory(category);
        List<News> news = newsService.findSearch(newsModel);
        model.addAttribute("news",news);
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categoryList",categories);
        return "news-list";
    }





}
