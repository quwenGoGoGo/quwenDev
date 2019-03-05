package quwen.wx.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quwen.core.util.ResponseUtil;
import quwen.db.domain.Collect;
import quwen.db.domain.Comment;
import quwen.db.domain.News;
import quwen.db.service.CollectService;
import quwen.db.service.CommentService;
import quwen.db.service.NewsService;
import quwen.db.service.UserService;
import quwen.wx.api.dao.CommentVo;
import quwen.wx.api.dao.NewsVo;
import quwen.wx.api.util.CommentMapper;
import quwen.wx.api.util.NewsMapper;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx/user")
@Validated
public class WxUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CollectService collectService;
    @Autowired
    private NewsService newsService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/collect")
    public Object getUserCollect(@NotNull String nickname){
        Map<String, Object> data = new HashMap<>();
        List<Collect> collects = collectService.findByUser_nickName(nickname);
        List<News> news = new ArrayList<>();
        NewsMapper newsMapper = new NewsMapper();
        for(Collect collect:collects){
            news.add(newsService.getNewsByID(collect.getNews().getNewsID()));
        }
        for (News eachNews:news) {
            eachNews.updateComment_count();
            eachNews.updateCollected_count();
        }
        List<NewsVo> newsList = newsMapper.NewsListPoToVo(news);
        data.put("newsList", newsList);
        return ResponseUtil.ok(data);
    }

    @GetMapping("/comment")
    public Object getUserComment(@NotNull String nickname){
        Map<String, Object> data = new HashMap<>();
        List<Comment> comments = commentService.findByUser_nickName(nickname);
        CommentMapper commentMapper = new CommentMapper();
        List<CommentVo> commentList = commentMapper.CommentListPoToVo(comments);
        data.put("commentList", commentList);
        return ResponseUtil.ok(data);
    }
}
