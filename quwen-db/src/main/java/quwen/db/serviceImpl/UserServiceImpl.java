package quwen.db.serviceImpl;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import quwen.db.domain.User;
import quwen.db.repository.UserRepository;
import quwen.db.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	public User addUser(User user) {
		return userRepository.save(user);
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

	@Override
	public void deleteUser(Long userID) {
		 userRepository.deleteById(userID);
	}

	@Override
	public List<User> findSearch(User user) {
		List<User> result = userRepository.findAll(new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();

				SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				if(!StringUtils.isEmpty(user.getNickname())){
					list.add(criteriaBuilder.like(root.get("nickname").as(String.class),"%" + user.getNickname() + "%"));
				}

				Predicate[] p = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(p));
			}
		});
		return result;
	}

}
