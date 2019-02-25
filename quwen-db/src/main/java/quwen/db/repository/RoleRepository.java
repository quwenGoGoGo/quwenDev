package quwen.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quwen.db.domain.Role;

import java.util.List;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    public List<Role> findRolesByEnableIsTrueAndDeletedIsFalseAndIdIn(Integer[] ids);
}
