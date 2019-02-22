package quwen.db.domain;


import javax.persistence.*;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cateID;

    //标题唯一且不为空
    @Column(unique = true, nullable = false)
    private String cateName;


    //cascade级联保存、更新、删除、刷新;延迟加载。当删除分类时，会级联删除该分类的所有新闻
    //拥有mappedBy注解的实体类为关系被维护端
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)

    private List<News> news;

    public Long getCateID() {
        return cateID;
    }

    public void setCateID(Long cateID) {
        this.cateID = cateID;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }


    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }
}
