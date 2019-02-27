package quwen.db.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quwen.db.domain.NewsStory;
import quwen.db.repository.StoryRepository;
import quwen.db.service.StoryService;

import java.util.List;


@Service
public class StoryServiceImpl implements StoryService {

    @Autowired
    private StoryRepository storyRepository;

    @Override
    public List<NewsStory> findAll(){
        return storyRepository.findAll();
    }

    @Override
    public NewsStory findByStoryID(Long storyID){
        return storyRepository.findById(storyID).get();
    }

    @Override
    public void addNewsStory(NewsStory newsStory){
        storyRepository.save(newsStory);
    }

    @Override
    public void updateNewsStory(NewsStory newsStory){
        storyRepository.save(newsStory);
    }

    @Override
    public void deleteNewsStory(Long storyID){
        storyRepository.deleteById(storyID);
    }
}
