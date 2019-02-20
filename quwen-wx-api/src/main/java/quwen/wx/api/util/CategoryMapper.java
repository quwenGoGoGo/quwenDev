package quwen.wx.api.util;

import quwen.db.domain.Category;
import quwen.wx.api.dao.CategoryVo;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {
    public CategoryVo CategoryPoToVo(Category categoryPo){
        CategoryVo categoryVo = new CategoryVo();
        categoryVo.setCateID(categoryPo.getCateID());
        categoryVo.setCateName(categoryPo.getCateName());
        return categoryVo;
    }

    public List<CategoryVo> CategoryListPoToVo(List<Category> categoriePos){
        List<CategoryVo> categoryVos = new ArrayList<>();
        CategoryMapper categoryMapper = new CategoryMapper();
        for(Category categoryPo:categoriePos){
            categoryVos.add(categoryMapper.CategoryPoToVo(categoryPo));
        }
        return categoryVos;

    }
}
