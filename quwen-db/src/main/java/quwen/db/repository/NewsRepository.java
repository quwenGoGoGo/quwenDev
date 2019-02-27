package quwen.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import quwen.db.domain.Category;
import quwen.db.domain.News;
import quwen.db.domain.NewsStory;

import java.util.List;
@Repository
public interface NewsRepository extends JpaRepository<News, Long>,JpaSpecificationExecutor<News> {
    public List<News> findAllByStickIsTrue();

    public List<News> findAllByStickIsFalse();

    public News findNewsByNewsID(Long newsID);

    public List<News> findNewsByCategory(Category category);

    public List<News> findNewsByNewsStory(NewsStory newsStory);

}
