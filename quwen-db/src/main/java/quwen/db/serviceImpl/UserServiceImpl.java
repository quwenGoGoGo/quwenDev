package quwen.db.serviceImpl;

import org.springframework.ui.Model;
import quwen.db.domain.User;
import quwen.db.repository.UserRepository;
import quwen.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;


@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	@Override
	public User queryByOid(String openId) {
		
		return userRepository.findUserByWeixinOpenid(openId);
	
	}
	@Override
	public void saveAndFlush(User user) {
		
		userRepository.saveAndFlush(user);
		
	}

	@Override
	public void addUser(@Valid User user) {

	}

	@Override
	public void login(User user, Model model) {

	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserByID(Long userID) {
		return userRepository.findUserByUserID(userID);
	}

	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}
}
