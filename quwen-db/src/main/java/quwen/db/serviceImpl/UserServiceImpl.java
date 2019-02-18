package quwen.db.serviceImpl;

import org.springframework.ui.Model;
import quwen.db.domain.User;
import quwen.db.repository.UserRepository;
import quwen.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;


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
}
