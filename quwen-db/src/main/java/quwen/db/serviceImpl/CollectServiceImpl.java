package quwen.db.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quwen.db.domain.Collect;
import quwen.db.domain.News;
import quwen.db.domain.User;
import quwen.db.repository.CollectRepository;
import quwen.db.service.CollectService;

import java.util.List;

@Service
public class CollectServiceImpl implements CollectService{
   @Autowired
   private CollectRepository collectRepository;

   @Override
   public Collect getCollectByID(long collectID){
       return collectRepository.findById(collectID).get();
   }
   @Override
    public List<Collect> getAllCollect(){
       return collectRepository.findAll();
   }
   @Override
   public Collect addCollect(Collect collect){
       return collectRepository.save(collect);
   }
   @Override
    public Collect updateCollect(Collect collect){
       return collectRepository.save(collect);
   }
   @Override
    public void deleteCollectByID(Long collectID){
       collectRepository.deleteById(collectID);
   }

    @Override
    public User findUserByName(String username){
       return collectRepository.findByUserName(username);
    }
    @Override
    public List<Collect> findByNewsID(Long newsID){
       return collectRepository.findByNewsID(newsID);
    }


}