package quwen.db.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import quwen.db.domain.Category;
import quwen.db.domain.Collect;
import quwen.db.domain.News;
import quwen.db.domain.NewsStory;
import quwen.db.repository.NewsRepository;
import quwen.db.service.NewsService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsRepository newsRepository;
    @Override
    public List<News> findAllByStickIsTrue() {

        return newsRepository.findAllByStickIsTrue();

    }

    @Override
    public List<News> getAllNews(){
        return newsRepository.findAll();
    }

    @Override
    public void deleteNews(Long newsID){
        newsRepository.deleteById(newsID);
    }



    @Override
    public List<News> findSearch(News newsModel){

        List<News> result = newsRepository.findAll(new Specification<News>() {
            @Override
            public Predicate toPredicate(Root<News> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();

                SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                if(!StringUtils.isEmpty(newsModel.getTitle())){
                    list.add(criteriaBuilder.like(root.get("title").as(String.class),"%" + newsModel.getTitle() + "%"));
                }
                if(newsModel.getCategory()!=null&&newsModel.getCategory().getCateID()!=null){
                    list.add(criteriaBuilder.equal(root.get("category").as(Category.class), newsModel.getCategory()));
                }


                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        });
        return result;
    }

    @Override
    public void addNews(News news){
        newsRepository.save(news);
    }

    @Override
    public News getNewsByID(Long newsID){
        return newsRepository.findById(newsID).get();
    }

    @Override
    public List<News> findAllByStickIsFalse(){
        return newsRepository.findAllByStickIsFalse();
    }


    @Override
    public List<News> findNewsByCategory(Category category){
        return newsRepository.findNewsByCategory(category);
    }

    @Override
    public void updateNews(News news){
        newsRepository.save(news);
    }

    @Override
    public List<News> findNewsByStory(NewsStory newsStory){
        return newsRepository.findNewsByNewsStory(newsStory);
    }

    @Override
    public List<News> findNewsByStatusIsTrue(){
        return newsRepository.findNewsByStatusIsTrue();
    }

    public List<News> findNewsByStoryAndOrderByCtime(NewsStory newsStory){
        return newsRepository.findNewsByNewsStoryOrderByCtimeDesc(newsStory);
    }


}
