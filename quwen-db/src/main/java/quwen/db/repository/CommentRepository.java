package quwen.db.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import quwen.db.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
