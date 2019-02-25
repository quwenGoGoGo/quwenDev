package quwen.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quwen.db.domain.Permission;

import java.util.List;
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    public List<Permission> findPermissionsByDeletedIsFalseAndRoleIdIn(Integer[] ids);
}
