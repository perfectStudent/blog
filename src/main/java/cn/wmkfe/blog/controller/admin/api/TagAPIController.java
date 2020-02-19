package cn.wmkfe.blog.controller.admin.api;

import cn.wmkfe.blog.annotation.Operation;
import cn.wmkfe.blog.model.Tag;
import cn.wmkfe.blog.service.TagService;
import cn.wmkfe.blog.util.ConstantValue;
import cn.wmkfe.blog.util.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(value = "/admin")
public class TagAPIController {

    //后台
    @Autowired
    private TagService tagService;
    //获取标签列表
    @Operation("标签列表")
    @GetMapping("/tags")
    public Map<String,Object> tags(@RequestParam("page")String page, @RequestParam("limit")String limit, Tag tag){
        Map<String,Object> map=new HashMap<>();
        Integer currentPage=1;
        Integer pageSize=10;
        if(page !=null && !"".equals(page)){
            currentPage=Integer.valueOf(page);
        }
        if(limit !=null && !"".equals(limit)){
            pageSize=Integer.valueOf(limit);
        }

        //获取总条数
        int count = tagService.countTag(tag);

        PageSupport pageSupport=new PageSupport();
        pageSupport.setPageSize(pageSize);
        pageSupport.setCurrentPageNo(currentPage);
        pageSupport.setTotal(count);

        if(currentPage<0){
            currentPage=1;
        }else if (currentPage>pageSupport.getPageCount()){
            currentPage=pageSupport.getPageCount();
        }
        pageSupport.setCurrentPageNo(currentPage);
        List<Tag> tagList = tagService.getTagList(currentPage, pageSize, tag);
        map.put("code",200);
        map.put("count",count);
        map.put("msg",ConstantValue.SUCCESS);
        map.put("data",tagList);
        return map;
    }

    //添加标签
    @Operation("添加标签")
    @PostMapping("/tags")
    public Map<String,Object> addTags(@Valid Tag tag){
        Map<String,Object> map=new HashMap<>();
        Tag tagByName = tagService.getTagByName(tag.getTagName());
        if(tagByName!=null){
            map.put("code",202);
            map.put("msg","此数据已存在");
            map.put("data",null);
        }else{
            int total = tagService.saveTag(tag);
            if (total>0){
                map.put("code",200);
                map.put("msg",ConstantValue.SUCCESS);
                map.put("data",null);
            }
        }
        return map;
    }

    //删除标签
    @Operation("删除标签")
    @DeleteMapping("/tags/{ids}")
    public Map<String,Object> deleteTags(@PathVariable(value = "ids")String[] ids){
        Map<String,Object> map=new HashMap<>();
        int[] arr =new int [ids.length];
        for (int i=0;i<ids.length;i++) {
            if(ids[i]!=null&&!"".equals(ids[i])){
                arr[i]=Integer.valueOf(ids[i]);
            }
        }
        tagService.removeTag(arr);
            map.put("code",200);
            map.put("msg",ConstantValue.SUCCESS);
            map.put("data",null);
        return map;
    }

    //根据id查询标签
    @Operation("根据id查询标签")
    @GetMapping("/tags/{id}")
    public Map<String,Object> findById(@PathVariable(value = "id")String id){
        Map<String,Object> map=new HashMap<>();
        Tag tag=null;
        if(id!=null&!"".equals(id)){
             tag = tagService.getTag(Integer.valueOf(id));
        }
        map.put("code",202);
        map.put("msg","未找到相关数据");
        if (tag!=null){
            map.put("code",200);
            map.put("msg",ConstantValue.SUCCESS);
            map.put("data",tag);
        }
        return map;
    }

    //更新标签
    @Operation("更新标签")
    @PutMapping("/tags")
    public Map<String,Object> updateTags(@Valid Tag tag){
        Map<String,Object> map=new HashMap<>();
        tagService.updateTag(tag);
        map.put("code",200);
        map.put("msg",ConstantValue.SUCCESS);
        map.put("data",null);
        return map;
    }



}
