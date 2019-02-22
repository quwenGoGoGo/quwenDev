package quwen.db.service;

import org.springframework.stereotype.Service;
import quwen.db.domain.Category;
import quwen.db.domain.News;

import java.util.List;
@Service
public interface NewsService {
    public List<News> findAllByStickIsTrue();
    public List<News> findAllByStickIsFalse();
    public List<News> findNewsByCategory(Category category);
    List<News> getAllNews();
    void deleteNews(Long newsID);
    List<News> findSearch(News newsModel);
    void addNews(News news);
    void updateNews(News news);
    News getNewsByID(Long newsID);
}
