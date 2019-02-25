package quwen.wx.api.util;

import quwen.db.domain.NewsStory;
import quwen.wx.api.dao.NewsStoryVo;

import java.util.ArrayList;
import java.util.List;

public class StoryMapper {
    public NewsStoryVo StoryPoToVo(NewsStory newsStory){
        NewsStoryVo newsStoryVo = new NewsStoryVo();
        newsStoryVo.setStoryID(newsStory.getStoryID());
        newsStoryVo.setKeywords(newsStory.getKeywords());
        newsStoryVo.setPicture(newsStory.getPicture());
        newsStoryVo.setTheme(newsStory.getTheme());
        return newsStoryVo;
    }

    public List<NewsStoryVo> StoryListPoToVo(List<NewsStory> newsStories){
        List<NewsStoryVo> newsStoryVos = new ArrayList<>();
        StoryMapper storyMapper = new StoryMapper();
        for(NewsStory newsStory:newsStories){
            newsStoryVos.add(storyMapper.StoryPoToVo(newsStory));
        }
        return newsStoryVos;
    }
}
