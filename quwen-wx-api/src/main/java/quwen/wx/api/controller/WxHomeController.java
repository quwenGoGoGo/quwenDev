package quwen.wx.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quwen.core.util.ResponseUtil;
import quwen.db.domain.Category;
import quwen.db.domain.News;
import quwen.db.service.CategoryService;
import quwen.db.service.NewsService;
import quwen.wx.api.dao.CategoryVo;
import quwen.wx.api.dao.NewsVo;
import quwen.wx.api.service.HomeCacheManager;
import quwen.wx.api.util.CategoryMapper;
import quwen.wx.api.util.NewsMapper;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


@RestController
@RequestMapping("/wx/home")
@Validated
public class WxHomeController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CategoryService categoryService;

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
        List<NewsVo> stickNews = newsMapper.NewsListPoToVo(newsService.findAllByStickIsTrue());
        List<NewsVo> newList = newsMapper.NewsListPoToVo(newsService.findAllByStickIsFalse());

        try{
            data.put("category", category);
            data.put("stickNews", stickNews);
            data.put("newsList", newList);
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
        List<NewsVo> news = newsMapper.NewsListPoToVo(newsService.findNewsByCategory(category));

        data.put("newsList", news);
        return ResponseUtil.ok(data);
    }
}
