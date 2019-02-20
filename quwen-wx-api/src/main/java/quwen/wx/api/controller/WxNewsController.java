package quwen.wx.api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quwen.core.util.ResponseUtil;
import quwen.db.domain.News;
import quwen.db.service.NewsService;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wx/news")
@Validated
public class WxNewsController {
    @Autowired
    private NewsService newsService;
    @GetMapping("/detail")
    public Object detail(@NotNull Long id){
        Map<String, Object> data = new HashMap<>();
        News info = newsService.findNewsByNewsID(id);

        data.put("info", info);
        return ResponseUtil.ok(data);
    }
}
