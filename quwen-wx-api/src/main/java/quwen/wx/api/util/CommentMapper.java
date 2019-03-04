package quwen.wx.api.util;

import quwen.db.domain.Comment;
import quwen.db.domain.News;
import quwen.wx.api.dao.CommentVo;
import quwen.wx.api.dao.NewsVo;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {
    public CommentVo CommentPoToVo(Comment comment){
        CommentVo commentVo = new CommentVo();
        commentVo.setCommentID(comment.getCommentID());
        commentVo.setCommentContent(comment.getCommentContent());
        commentVo.setCommentDate(comment.getCommentDate());
        commentVo.setNickname(comment.getUser().getNickname());
        commentVo.setAvatar(comment.getUser().getAvatar());
        return commentVo;
    }

    public List<CommentVo> CommentListPoToVo(List<Comment> commentsPos){
        List<CommentVo> commentVos = new ArrayList<>();
        CommentMapper commentMapper = new CommentMapper();
        for(Comment commentPo : commentsPos){
            commentVos.add(commentMapper.CommentPoToVo(commentPo));
        }
        return commentVos;
    }
}
