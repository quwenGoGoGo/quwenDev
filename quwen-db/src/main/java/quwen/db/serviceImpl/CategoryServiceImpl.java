package quwen.db.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quwen.db.domain.Category;
import quwen.db.repository.CategoryRepository;
import quwen.db.service.CategoryService;


import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category findByCateName(String cateName){
        return categoryRepository.findByCateName(cateName);
    }

    @Override
    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long cateID) {
        categoryRepository.deleteById(cateID);
    }

    @Override
    public Category getCategoryByID(Long cateID) {
        return categoryRepository.findById(cateID).get();
    }
}
