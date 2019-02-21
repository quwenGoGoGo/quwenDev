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

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false, fetch = FetchType.LAZY)
    //可选属性optional=false,表示分类不能为空。删除新闻，不影响分类
    @JoinColumn(name = "cate_id")
    //设置在category表中的关联字段(外键)
    private Category category;
    private String author;
    private Date ctime;
    private Integer share_count;
    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Collect> collects;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    private String title;

    private String picUrl;

    private String content;

    private Integer view_count;

    private Integer collected_count;

    private Integer comment_count;
    //设置是否置顶
    @Column(columnDefinition = "bit default 0")
    private boolean stick =false;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;
   private LocalDateTime updateTime;
    //是否删除
    @Column(columnDefinition = "bit default 0")
    private Boolean deleted;

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

    public Integer getShare_count() {
        return share_count;
    }

    public void setShare_count(Integer share_count) {
        this.share_count = share_count;
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

    public Integer getView_count() {
        return view_count;
    }

    public void setView_count(Integer view_count) {
        this.view_count = view_count;
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

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
