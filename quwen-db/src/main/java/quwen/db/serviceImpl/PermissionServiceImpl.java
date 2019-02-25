package quwen.db.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quwen.db.domain.Permission;
import quwen.db.repository.PermissionRepository;
import quwen.db.service.PermissionService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Set<String> queryByRoleIds(Integer[] ids) {
        Set<String> permissions = new HashSet<>();
        if(ids == null || ids.length == 0){
            return permissions;
        }
        List<Permission> permissionList = permissionRepository.findPermissionsByDeletedIsFalseAndRoleIdIn(ids);

        for(Permission permission: permissionList){
            permissions.add(permission.getPermission());
        }
        return permissions;
    }
}
