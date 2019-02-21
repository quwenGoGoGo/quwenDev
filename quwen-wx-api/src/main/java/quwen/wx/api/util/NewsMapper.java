package quwen.wx.api.util;

import quwen.db.domain.News;
import quwen.wx.api.dao.NewsVo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewsMapper {
    public NewsVo NewsPoToVo(News newsPo){
        NewsVo newsVo = new NewsVo();
        newsVo.setNewsID(newsPo.getNewsID());
        newsVo.setTitle(newsPo.getTitle());
        newsVo.setPicUrl(newsPo.getPicUrl());
        newsVo.setContent(newsPo.getContent());
        newsVo.setAuthor(newsPo.getAuthor());
        newsVo.setCateName(newsPo.getCategory().getCateName());
        newsVo.setStatus(newsPo.getStatus());
        newsVo.setStick(newsPo.isStick());
        newsVo.setCollected_count(newsPo.getCollected_count());
        newsVo.setComment_count(newsPo.getComment_count());
        return newsVo;
    }

    public List<NewsVo> NewsListPoToVo(List<News> newsPos){
        List<NewsVo> newsVos = new ArrayList<>();
        NewsMapper newsMapper = new NewsMapper();
        for(News newsPo : newsPos){
            newsVos.add(newsMapper.NewsPoToVo(newsPo));
        }
        return newsVos;
    }
}
