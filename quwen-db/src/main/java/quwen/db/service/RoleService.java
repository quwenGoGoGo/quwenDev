package quwen.db.service;

import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public interface RoleService {
    public Set<String> queryByIds(Integer[] ids);
}
