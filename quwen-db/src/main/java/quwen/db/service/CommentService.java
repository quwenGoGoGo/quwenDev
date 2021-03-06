package quwen.db.service;

import quwen.db.domain.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAllComment();

    Comment addComment(Comment comment);

    Comment updateComment(Comment comment);

    void deleteComment(Long commentID);

    Comment getCommentByID(Long commentID);

    List<Comment> getAllCommentByNewsID(Long newsID);

    List<Comment> getAllCommentByUserID(Long userID);

    List<Comment> getAllCommentByCommentContent(String commentContent);

    List<Comment> findSearch(Comment comment);

    List<Comment> findSearch_user(Comment comment);

    List<Comment> findByUser_nickName(String nickname);
}
