package quwen.db.service;

import org.springframework.stereotype.Service;
import quwen.db.domain.Collect;
import quwen.db.domain.User;
import quwen.db.domain.News;

import java.util.List;

@Service
public interface CollectService {

    Collect getCollectByID(long collectID);
    List<Collect> getAllCollect();
    Collect addCollect(Collect collect);
    Collect updateCollect(Collect collect);
    void deleteCollectByID(Long collectID);
    User findUserByName(String username);
    List<Collect> findByNewsID(Long newsID);

}

