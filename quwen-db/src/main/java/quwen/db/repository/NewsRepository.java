package quwen.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import quwen.db.domain.News;

import java.util.List;
@Repository
public interface NewsRepository extends JpaRepository<News, Long>,JpaSpecificationExecutor<News> {
    public List<News> findAllByStickIsTrue();

    public List<News> findAllByDeletedIsFalse();
}
