package quwen.db.service;

import org.springframework.stereotype.Service;
import quwen.db.domain.Admin;

import java.util.List;

@Service
public interface AdminService {
    public List<Admin> getAllByUsername(String username);
}
