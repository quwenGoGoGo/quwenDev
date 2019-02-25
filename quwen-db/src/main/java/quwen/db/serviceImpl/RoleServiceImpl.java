package quwen.db.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quwen.db.domain.Role;
import quwen.db.repository.RoleRepository;
import quwen.db.service.RoleService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Set<String> queryByIds(Integer[] roleIds) {
        Set<String> roles = new HashSet<String>();
        if(roleIds == null || roleIds.length == 0){
            return roles;
        }

        List<Role> roleList = roleRepository.findRolesByEnableIsTrueAndDeletedIsFalseAndIdIn(roleIds);


        for (Role role: roleList){
            roles.add(role.getName());
        }
        return roles;
    }
}
