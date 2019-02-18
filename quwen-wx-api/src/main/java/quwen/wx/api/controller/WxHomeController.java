package quwen.wx.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quwen.core.util.ResponseUtil;
import quwen.db.service.NewsService;
import quwen.wx.api.service.HomeCacheManager;

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

    private final static ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(9);

    private final static RejectedExecutionHandler HANDLER = new ThreadPoolExecutor.CallerRunsPolicy();

    private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(9, 9, 1000, TimeUnit.MILLISECONDS, WORK_QUEUE, HANDLER);
    @GetMapping("/index")
    public Object index(){
        if(HomeCacheManager.hasData(HomeCacheManager.INDEX)){
            return ResponseUtil.ok(HomeCacheManager.getCacheData(HomeCacheManager.INDEX));
        }

        Map<String, Object> data = new HashMap<>();

        Callable<List>  stickNewsListCallable = () -> newsService.findAllByStickIsTrue();

        FutureTask<List> stickTask = new FutureTask<>(stickNewsListCallable);

        executorService.submit(stickTask);

        try{
            data.put("stickNews", stickTask.get());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        HomeCacheManager.loadData(HomeCacheManager.INDEX, data);
        return ResponseUtil.ok(data);
    }
}
