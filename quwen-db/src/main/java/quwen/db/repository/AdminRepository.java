package quwen.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quwen.db.domain.Admin;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    public List<Admin> getAllByUsername(String username);
}
