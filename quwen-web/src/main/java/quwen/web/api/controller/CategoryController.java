package quwen.web.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import quwen.db.domain.Category;
import quwen.db.service.CategoryService;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("cate")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("index")
    public String index(){return "index";}

    @GetMapping("toList")
    public String geCategoryList(Model model){
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories",categories);
        return "cate_list";
    }

    @RequestMapping("edit/{id}")
    public String edit(Model model, @PathVariable("id")Long id){
        System.out.println(id);

        if(id>0){
            System.out.println(id);
            model.addAttribute("isAdd",false);
            model.addAttribute("category",categoryService.getCategoryByID(id));
        }else{
            model.addAttribute("isAdd",true);
            model.addAttribute("category",new Category());
        }
        System.out.println("edit");
        return "cate_edit";
    }

    @PostMapping("save")
    @ResponseBody
    public String save(@RequestParam HashMap<String,Object> map){
        if(map == null || map.size() ==0){
            return null;
        }
        Category category = new Category();
        category.setCateName((String)map.get("cateName"));
        if(map.get("cateID")!=null && (Long.parseLong(map.get("cateID").toString()))>0){
            String cateID = map.get("cateID").toString();
            System.out.println("edit"+map.get("cateID").toString());
            category.setCateID(Long.parseLong(cateID));
            categoryService.updateCategory(category);

        }else {
            categoryService.addCategory(category);
        }
        return "success";
    }

    @RequestMapping("list")
    public List<Category> getAllCategory(Model model){
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categoryList",categories);
        return null;
    }

    @RequestMapping("del")
    @ResponseBody
    public String delByID(@RequestParam HashMap<String,String> map){
        String cateIDs=map.get("id");
        Long id = Long.parseLong(cateIDs);
        System.out.println("cateIDdel:"+id);
        categoryService.deleteCategory(id);
        return "success";
    }
}
