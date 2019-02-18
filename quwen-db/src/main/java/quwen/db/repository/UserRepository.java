package quwen.db.repository;

import quwen.db.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User , Long>{
	public User findUserByWeixinOpenid(String id);
	public User saveAndFlush(User user);
}
