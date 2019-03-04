package quwen.db.service;

import org.springframework.ui.Model;
import quwen.db.domain.User;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;


@Service
public interface UserService {

	public User queryByOid(String openId);

	public void saveAndFlush(User user);

//	public void addUser(@Valid User user);

	User addUser(User user);

	public void login(User user, Model model);

	public List<User> getAllUser();

	List<User> getAllUsers();

	public User getUserByID(Long userID);

	User updateUser(User user);

	void deleteUser(Long userID);

	List<User> findSearch(User user);

	public User findByNickName(String nickName);

}
