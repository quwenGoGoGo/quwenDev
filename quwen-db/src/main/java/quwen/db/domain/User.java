package quwen.db.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    //微信授权id
	@Column(unique = true)
	private String weixinOpenid;

	//微信昵称
	@Column(unique = true, nullable = false)
	private String nickname;

	//微信头像
	private String avatar;

	//用户性别
	private Byte gender;

	//最后一次登录时间
	private LocalDateTime lastLoginTime;

	//最后一次登录ip
	private String lastLoginIp;

	//用户评论量，默认为0
	@Column(columnDefinition = "INT default 0")
	private Integer comment_count = 0;

	//用户收藏量，默认为0
	@Column(columnDefinition = "INT default 0")
	private Integer collect_count = 0;

	//用户数量，默认为0
	@Column(columnDefinition = "INT default 0")
	private Integer user_count = 0;

	//关联表comments
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Comment> comments;

	//用户表关联收藏表，一个用户对应多条收藏纪录
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Collect> collects;


	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}


	public Byte getGender() {
		return gender;
	}

	public void setGender(Byte gender) {
		this.gender = gender;
	}

	public LocalDateTime getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(LocalDateTime lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getWeixinOpenid() {
		return weixinOpenid;
	}

	public void setWeixinOpenid(String weixinOpenid) {
		this.weixinOpenid = weixinOpenid;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Collect> getCollects() {
		return collects;
	}

	public void setCollects(List<Collect> collects) {
		this.collects = collects;
	}

	public Integer getComment_count() {
		return comment_count;
	}

	public void setComment_count(Integer comment_count) {
		this.comment_count = comment_count;
	}

	public Integer getCollect_count() {
		return collect_count;
	}

	public void setCollect_count(Integer collect_count) {
		this.collect_count = collect_count;
	}

	public void updateComment_count() {
		this.comment_count = this.comments.size();
	}

	public void updateCollected_count(){
		this.collect_count = this.collects.size();
	}


	public Integer getUser_count() {
		return user_count;
	}

	public void setUser_count(Integer user_count) {
		this.user_count = user_count;
	}
}
