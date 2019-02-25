package quwen.db.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import quwen.db.domain.Comment;
import quwen.db.repository.CommentRepository;
import quwen.db.service.CommentService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getAllComment() {
        return commentRepository.findAll();
    }

    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentID) {
        commentRepository.deleteById(commentID);
    }

    @Override
    public Comment getCommentByID(Long commentID) {
        return commentRepository.findById(commentID).get();
    }

    @Override
    public List<Comment> getAllCommentByNewsID(Long newsID){return  commentRepository.findByNews_NewsID(newsID);}

    @Override
    public List<Comment> getAllCommentByUserID(Long userID){return commentRepository.findByUser_UserID(userID);}

    @Override
    public List<Comment> getAllCommentByCommentContent(String commentContent){return commentRepository.findByCommentContent(commentContent);}

    @Override
    public List<Comment> findSearch(Comment comment){
        List<Comment> result = commentRepository.findAll(new Specification<Comment>() {
        @Override
        public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            List<Predicate> list = new ArrayList<Predicate>();

            SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if(!StringUtils.isEmpty(comment.getCommentContent())){
                list.add(criteriaBuilder.like(root.get("commentContent").as(String.class),"%" + comment.getCommentContent() + "%"));
            }

            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }
    });
        return result;
    }
}
