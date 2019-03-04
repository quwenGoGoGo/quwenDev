package quwen.db.repository;

import org.springframework.data.jpa.domain.Specification;
import quwen.db.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User , Long>{
	public User findUserByWeixinOpenid(String id);
	public User saveAndFlush(User user);
	public User findUserByUserID(Long userID);
	List<User> findAll(Specification<User> comment);
	public User findByNickname(String nickName);

}
