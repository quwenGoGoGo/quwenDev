package quwen.db.service;

import org.springframework.stereotype.Service;
import quwen.db.domain.News;
import quwen.db.domain.NewsStory;

import java.util.List;

@Service
public interface StoryService {
    public List<NewsStory> findAll();
    public NewsStory findByStoryID(Long storyID);
    public void addNewsStory(NewsStory newsStory);
    public void updateNewsStory(NewsStory newsStory);
    public void deleteNewsStory(Long storyID);
}
