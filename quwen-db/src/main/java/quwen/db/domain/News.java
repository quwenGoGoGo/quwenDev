package quwen.db.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newsID;

    //新闻标题,唯一且不为空
    @Column(unique = true, nullable = false)
    private String title;

    //新闻图片
    private String picUrl;

    //新闻内容，设置数据库格式为text
    @Column(columnDefinition = "text")
    private String content;

    //新闻来源
    private String author;

    //新闻发布时间，设置格式为日期
    @Temporal(TemporalType.DATE)
    private Date ctime;

    //新闻关键字
    private String keywords;

    //新闻主题描述
    private String description;

    //新闻收藏量，默认为0
    @Column(columnDefinition = "INT default 0")
    private Integer collected_count = 0;

    //新闻评论量，默认为0
    @Column(columnDefinition = "INT default 0")
    private Integer comment_count = 0;

    //设置是否置顶
    @Column(columnDefinition = "bit default 0")
    private boolean stick = false;

    //设置是否为推荐
    @Column(columnDefinition = "INT default 0")
    private Integer status = 0;

    //新闻表中设置分类外键，多对一的关系
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false, fetch = FetchType.LAZY)
    //可选属性optional=false,表示分类不能为空。删除新闻，不影响分类
    @JoinColumn(name = "cate_id")
    //设置在category表中的关联字段(外键)
    private Category category;

    //新闻表关联收藏表，一条新闻对应多条收藏纪录
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Collect> collects;

    //新闻表关联评论表，一条新闻对应多条评论纪录
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> comments;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public List<Collect> getCollects() {
        return collects;
    }

    public void setCollects(List<Collect> collects) {
        this.collects = collects;
    }

    public boolean isStick() {
        return stick;
    }

    public void setStick(boolean stick) {
        stick = stick;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getNewsID() {
        return newsID;
    }

    public void setNewsID(Long newsID) {
        this.newsID = newsID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCollected_count() {
        return collected_count;
    }

    public void setCollected_count(Integer collected_count) {
        this.collected_count = collected_count;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }

    public boolean stick() {
        return stick;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
