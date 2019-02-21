package quwen.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import quwen.db.domain.Collect;
import quwen.db.domain.User;
import quwen.db.domain.News;

import java.util.List;

public interface CollectRepository extends JpaRepository<Collect, Long> {

    @Query("select u from User u where u.username=?1")
    public User findByUserName(String userName);

    @Query("select n from Collect n where n.news.newsID=?1 ")
    public List<Collect> findByNewsID(Long newsID);
}
