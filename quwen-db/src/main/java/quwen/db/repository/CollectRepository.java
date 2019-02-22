package quwen.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import quwen.db.domain.Collect;
import quwen.db.domain.User;
import quwen.db.domain.News;

import java.util.List;

public interface CollectRepository extends JpaRepository<Collect, Long> {


    List<Collect> findByNews_NewsID(Long newsID);
}
