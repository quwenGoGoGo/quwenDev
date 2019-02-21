package quwen.db.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Collect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collectID;

    private Date time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private News news;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public Long getCollectID() {
        return collectID;
    }

    public void setCollectID(Long collectID) {
        this.collectID = collectID;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date colTime) {
        this.time = time;
    }
}

