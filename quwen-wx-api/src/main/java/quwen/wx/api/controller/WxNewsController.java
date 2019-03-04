package quwen.wx.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import quwen.core.util.ResponseUtil;
import quwen.db.domain.Collect;
import quwen.db.domain.Comment;
import quwen.db.domain.News;
import quwen.db.domain.User;
import quwen.db.service.CollectService;
import quwen.db.service.CommentService;
import quwen.db.service.NewsService;
import quwen.db.service.UserService;
import quwen.wx.api.dao.CommentVo;
import quwen.wx.api.dao.NewsVo;
import quwen.wx.api.util.CommentMapper;
import quwen.wx.api.util.NewsMapper;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/wx/news")
@Validated
public class WxNewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CollectService collectService;

    @GetMapping("/detail")
    public Object detail(@NotNull Long id, @NotNull String nickname){
        Map<String, Object> data = new HashMap<>();
        NewsMapper newsMapper = new NewsMapper();
        News news = newsService.getNewsByID(id);
        NewsVo info = newsMapper.NewsPoToVo(news);
        if(news.getCollects().size()>0){
            info.setCollect(true);
        }
        else {
            info.setCollect(false);
        }

        List<Comment> comments = commentService.getAllCommentByNewsID(id);
        CommentMapper commentMapper = new CommentMapper();
        List<CommentVo> commentList = commentMapper.CommentListPoToVo(comments);

        data.put("info", info);
        data.put("commentList", commentList);
        return ResponseUtil.ok(data);
    }

    @GetMapping("/comment")
    public Object getComment(@NotNull Long id){
        Map<String, Object> data = new HashMap<>();
        List<Comment> comments = commentService.getAllCommentByNewsID(id);
        CommentMapper commentMapper = new CommentMapper();
        List<CommentVo> commentList = commentMapper.CommentListPoToVo(comments);
        data.put("commentList", commentList);
        return ResponseUtil.ok(data);
    }

    @RequestMapping("/addComment")
    public Object addComment(@NotNull String content, @NotNull String nickname, @NotNull Long news_id){
        User user = userService.findByNickName(nickname);
        News news = newsService.getNewsByID(news_id);
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setNews(news);
        comment.setCommentContent(content);
        comment.setCommentDate(new Date());
        commentService.addComment(comment);
        return ResponseUtil.ok();
    }

    @GetMapping("/addCollect")
    public Object addCollect(@NotNull Long newsID, @NotNull String nickName){
        User user = userService.findByNickName(nickName);
        News news = newsService.getNewsByID(newsID);
        Collect collect = new Collect();
        collect.setNews(news);
        collect.setUser(user);
        collect.setTime(new Date());
        collectService.addCollect(collect);
        return ResponseUtil.ok();
    }

    @GetMapping("cancelCollect")
    public Object cancelCollect(@NotNull Long newsID, @NotNull String nickName){
        List<Collect> collect = collectService.hasCollect(newsID, nickName);
        for(Collect collect1:collect){
            collectService.delete(collect1);
        }
        return ResponseUtil.ok();
    }

}
