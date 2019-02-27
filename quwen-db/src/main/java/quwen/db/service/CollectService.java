package quwen.db.service;

import org.springframework.stereotype.Service;
import quwen.db.domain.Collect;

import java.util.List;

@Service
public interface CollectService {

    Collect getCollectByID(long collectID);
    List<Collect> getAllCollect();
    Collect addCollect(Collect collect);
    Collect updateCollect(Collect collect);
    void deleteCollectByID(Long collectID);
    List<Collect> findByNews_NewsID(Long newsID);
    List<Collect> getAllCollectByUserID(Long userID);
}

