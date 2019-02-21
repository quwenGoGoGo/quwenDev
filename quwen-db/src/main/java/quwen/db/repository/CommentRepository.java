package quwen.db.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import quwen.db.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByNews_NewsID(Long newID);
}
