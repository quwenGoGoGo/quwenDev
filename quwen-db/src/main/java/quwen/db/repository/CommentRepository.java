package quwen.db.repository;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import quwen.db.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByNews_NewsID(Long newsID);
    List<Comment> findByCommentContent(String commentContent);
    List<Comment> findByUser_UserID(Long userID);
    List<Comment> findAll(Specification<Comment> comment);
}
