package quwen.db.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class NewsStory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storyID;

    private String keywords;
    private String theme;
    private String picture;

    //cascade级联保存、更新、删除、刷新;延迟加载。当删除分类时，会级联删除该分类的所有新闻
    //拥有mappedBy注解的实体类为关系被维护端
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)

    private List<News> news;



    public Long getStoryID() {
        return storyID;
    }

    public void setStoryID(Long storyID) {
        this.storyID = storyID;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }
}
