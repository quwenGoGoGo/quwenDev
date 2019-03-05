package quwen.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import quwen.db.domain.Collect;


import java.util.List;

public interface CollectRepository extends JpaRepository<Collect, Long> {


    List<Collect> findByNews_NewsID(Long newsID);
    List<Collect> findByUser_UserID(Long userID);
    List<Collect> findByNews_NewsIDAndUser_Nickname(Long newsID, String nickName);
    List<Collect> findByUser_Nickname(String nickname);

    @Override
    void delete(Collect collect);
}
