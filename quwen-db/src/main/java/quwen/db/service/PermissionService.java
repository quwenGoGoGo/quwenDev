package quwen.db.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface PermissionService {
    public Set<String> queryByRoleIds(Integer[] ids);
}
