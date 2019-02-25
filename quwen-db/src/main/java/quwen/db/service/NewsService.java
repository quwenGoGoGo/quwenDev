package quwen.db.service;

import org.springframework.stereotype.Service;
import quwen.db.domain.Category;
import quwen.db.domain.News;
import quwen.db.domain.NewsStory;

import java.util.List;
@Service
public interface NewsService {
    public List<News> findAllByStickIsTrue();
    public List<News> findAllByStickIsFalse();
    public List<News> findNewsByCategory(Category category);
    public List<News> findNewsByStory(NewsStory newsStory);
    public List<News> findNewsByStoryAndOrderByCtime(NewsStory newsStory);
    public List<News> findNewsByStatusIsTrue();
    List<News> getAllNews();
    void deleteNews(Long newsID);
    List<News> findSearch(News newsModel);
    void addNews(News news);
    void updateNews(News news);
    News getNewsByID(Long newsID);
}
