package quwen.wx.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quwen.core.util.ResponseUtil;
import quwen.db.domain.*;
import quwen.db.service.*;
import quwen.wx.api.dao.CategoryVo;
import quwen.wx.api.dao.NewsStoryVo;
import quwen.wx.api.dao.NewsVo;
import quwen.wx.api.service.HomeCacheManager;
import quwen.wx.api.util.CategoryMapper;
import quwen.wx.api.util.NewsMapper;
import quwen.wx.api.util.StoryMapper;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.*;


@RestController
@RequestMapping("/wx/home")
@Validated
public class WxHomeController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StoryService storyService;

    @Autowired
    private CollectService collectService;

    @Autowired
    private UserService userService;

    private final static ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(9);

    private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(9, 9, 1000, TimeUnit.MILLISECONDS, WORK_QUEUE, HANDLER);
    @GetMapping("/index")
    public Object index(){
        if(HomeCacheManager.hasData(HomeCacheManager.INDEX)){
            return ResponseUtil.ok(HomeCacheManager.getCacheData(HomeCacheManager.INDEX));
        }

        Map<String, Object> data = new HashMap<>();
        NewsMapper newsMapper = new NewsMapper();
        CategoryMapper categoryMapper = new CategoryMapper();
        StoryMapper storyMapper = new StoryMapper();

//        Callable<List> categoryCallable = () -> categoryService.getAllCategory();
        //Callable<List>  stickNewsListCallable = () -> newsService.findAllByStickIsTrue();
       // Callable<List> newsListCallable = () -> newsService.findAllByStickIsFalse();

//        FutureTask<List> categoryTask = new FutureTask<>(categoryCallable);
       // FutureTask<List> stickTask = new FutureTask<>(stickNewsListCallable);
       // FutureTask<List> newsListTask = new FutureTask<>(newsListCallable);

//        executorService.submit(categoryTask);
        //executorService.submit(stickTask);
        //executorService.submit(newsListTask);

        List<CategoryVo> category = categoryMapper.CategoryListPoToVo(categoryService.getAllCategory());
        Iterator<CategoryVo> iterator1 = category.iterator();
        while (iterator1.hasNext()){
            CategoryVo categoryVo = iterator1.next();
            if(categoryVo.getCateName().equals("主题")){
                iterator1.remove();
            }
        }
        List<NewsStory> newsStories = storyService.findAll();
        Iterator<NewsStory> iterator = newsStories.iterator();
        while(iterator.hasNext()){
            NewsStory newsStory = iterator.next();
            if(newsStory.getStoryID()==1){
                iterator.remove();
            }
            if(newsStory.getNews().size()<3){
                iterator.remove();
            }
        }
        List<NewsStoryVo> stories = storyMapper.StoryListPoToVo(newsStories);

        List<News> news = newsService.findNewsByStatusIsTrue();
        for (News eachNews:news) {
            eachNews.updateComment_count();
            eachNews.updateCollected_count();
        }
        List<NewsVo> newsList = newsMapper.NewsListPoToVo(news);

        try{
            data.put("category", category);
            data.put("story", stories);
            data.put("newsList", newsList);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        HomeCacheManager.loadData(HomeCacheManager.INDEX, data);
        return ResponseUtil.ok(data);
    }

    @GetMapping("/cate")
    public Object ListByCate(@NotNull Long cateID){
        Map<String, Object> data = new HashMap<>();
        NewsMapper newsMapper = new NewsMapper();
        Category category = categoryService.getCategoryByID(cateID);
        List<News> newsPos = newsService.findNewsByCategory(category);
        for (News eachNews:newsPos) {
            eachNews.updateComment_count();
            eachNews.updateCollected_count();
        }
        List<NewsVo> newsVos = newsMapper.NewsListPoToVo(newsPos);

        data.put("newsList", newsVos);
        return ResponseUtil.ok(data);
    }

    @GetMapping("/story")
    public Object NewsStoryList(@NotNull Long storyID){
        Map<String, Object> data = new HashMap<>();
        NewsMapper newsMapper = new NewsMapper();
        NewsStory newsStory = storyService.findByStoryID(storyID);
        List<News> newsPos = newsService.findNewsByStoryAndOrderByCtime(newsStory);
        for (News eachNews:newsPos) {
            eachNews.updateComment_count();
            eachNews.updateCollected_count();
        }
        List<NewsVo> news = newsMapper.NewsListPoToVo(newsPos);

        data.put("newsStory", news);
        return ResponseUtil.ok(data);
    }

}
