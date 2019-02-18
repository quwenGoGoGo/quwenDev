package quwen.db.service;


import org.springframework.stereotype.Service;
import quwen.db.domain.Category;


import java.util.List;

@Service
public interface CategoryService {

    Category findByCateName(String cateName);

    List<Category> getAllCategory();

    Category addCategory(Category category);

    Category updateCategory(Category category);

    void deleteCategory(Long cateID);

    Category getCategoryByID(Long cateID);


}
