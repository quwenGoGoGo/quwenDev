package quwen.db.service;

import org.springframework.stereotype.Service;
import quwen.db.domain.News;

import java.util.List;
@Service
public interface NewsService {
    public List<News> findAllByStickIsTrue();
    List<News> getAllNews();
    void deleteNews(Long newsID);
    List<News> findSearch(News newsModel);
    void addNews(News news);
    News getNewsByID(Long newsID);
}
