package quwen.web.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import quwen.db.domain.Comment;
import quwen.db.domain.News;
import quwen.db.domain.User;
import quwen.db.service.CommentService;
import quwen.db.service.NewsService;
import quwen.db.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    private UserService userService;
    private NewsService newsService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("all")
    public String getAllCmt(Model model){
        List<Comment> lists = commentService.getAllComment();
        model.addAttribute("cmts", lists);
        return "comment_list";
    }

    @GetMapping("toList/{newsid}")
    public String getAllComment(Model model, @PathVariable("newsid") Long newsid){
        System.out.println(newsid);
        List<Comment> list = commentService.getAllCommentByNewsID(newsid);
        model.addAttribute("cmts", list);
        return "comment_list";
    }

    @GetMapping("toListUser/{userid}")
    public String getAllComments(Model model, @PathVariable("userid") Long userid){
        System.out.println(userid);
        List<Comment> list = commentService.getAllCommentByUserID(userid);
        model.addAttribute("cmts", list);
        return "comment_list";
    }

    @RequestMapping("edit/{id}")
    public String edit(Model model,@PathVariable("id") Long id){
        System.out.println(id);

        if(id > 0){
            model.addAttribute("isAdd", false);
            model.addAttribute("comment", commentService.getCommentByID(id));
        }
        else{
            model.addAttribute("isAdd", true);
            model.addAttribute("comment", new Comment());
        }
        System.out.println("edit");

        return "comment_edit";
    }

    @PostMapping("save")
    @ResponseBody
    public String save(@ModelAttribute Comment comment){
        if(comment == null)
            return "fail";

        System.out.println(comment.getCommentID());

        if(comment.getCommentID() != null && comment.getCommentID() > 0)
            commentService.updateComment(comment);
        else
            commentService.addComment(comment);

        return "redirect:/comments/all";
    }

    @RequestMapping("delete")
    @ResponseBody//ajax需要加这个注解来异步获取数据
    public String delComment(@RequestParam HashMap<String,String> map) {
        String commentID=map.get("id");
        Long id = Long.parseLong(commentID);
        System.out.println("id is:" + commentID);
        commentService.deleteComment(id);
        return "redirect:/comments/all";
    }

    @RequestMapping("delAll")
    public void batchDeletes(HttpServletRequest request, HttpServletResponse response) {
        String items = request.getParameter("delitems");// System.out.println(items);
        String[] strs = items.split(",");

        for (int i = 0; i < strs.length; i++) {
            try {
                Long a = Long.parseLong(strs[i]);
                commentService.deleteComment(a);
            } catch (Exception e) {
            }
        }
    }

    @RequestMapping("search")
    public String searchForNews(@RequestParam("searchByContent")String content, Model model){
        Comment comment = new Comment();
        comment.setCommentContent(content);
        List<Comment> comments = commentService.findSearch(comment);
        model.addAttribute("cmts",comments);
        return "comment_list";
    }
}
