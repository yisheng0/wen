package com.yisheng.controller;

import com.yisheng.pojo.Article;
import com.yisheng.pojo.PageBean;
import com.yisheng.pojo.Result;
import com.yisheng.service.ArticleService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    //发布文章
    @PostMapping
    public Result add(@RequestBody  @Validated Article article) {
        articleService.add(article);
        return Result.success();
    };
    //更新文章
    @PutMapping
    public Result update(@RequestBody Article article){
        articleService.update(article);
        return Result.success();
    }
    //获取文章
    @GetMapping("/detail")
    public Result<Article> detail(@NotNull final Integer id) {
        final Article article = articleService.findById(id);
        return Result.success(article);
    }
    //删除文章
   @DeleteMapping
   public Result<String> delete(@NotNull final Integer id){
        articleService.delete(id);
       return Result.success();
   }
    //文章列表（分页查询）（条件分页）
    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ){
        PageBean<Article> pb = articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb) ;
    }
}
