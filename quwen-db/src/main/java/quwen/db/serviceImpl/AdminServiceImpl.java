package quwen.db.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quwen.db.domain.Admin;
import quwen.db.repository.AdminRepository;
import quwen.db.service.AdminService;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Override
    public List<Admin> getAllByUsername(String username) {
        return adminRepository.getAllByUsername(username);
    }
}
