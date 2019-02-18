package quwen.db.service;

import org.springframework.ui.Model;
import quwen.db.domain.User;
import org.springframework.stereotype.Service;

import javax.validation.Valid;


@Service
public interface UserService {
	
	public User queryByOid(String openId);
	
	public void saveAndFlush(User user);

	public void addUser(@Valid User user);

	public void login(User user, Model model);
	
}
