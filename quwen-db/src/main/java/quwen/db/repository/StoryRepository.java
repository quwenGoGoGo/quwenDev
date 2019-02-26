package quwen.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import quwen.db.domain.NewsStory;

@Repository
public interface StoryRepository extends JpaRepository<NewsStory, Long>, JpaSpecificationExecutor<NewsStory> {
}
