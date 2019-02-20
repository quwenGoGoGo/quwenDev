package quwen.db.service;

import org.springframework.stereotype.Service;
import quwen.db.domain.News;

import java.util.List;
@Service
public interface NewsService {
    public List<News> findAllByStickIsTrue();
    public List<News> getAllNews();
    public void deleteNews(Long newsID);
    public List<News> findSearch(News newsModel);
    public void addNews(News news);
    public News findNewsByNewsID(Long id);

}
