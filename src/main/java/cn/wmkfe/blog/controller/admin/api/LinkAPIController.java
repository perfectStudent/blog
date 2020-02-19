package cn.wmkfe.blog.controller.admin.api;

import cn.wmkfe.blog.annotation.Operation;
import cn.wmkfe.blog.model.Link;
import cn.wmkfe.blog.service.LinkService;
import cn.wmkfe.blog.util.ConstantValue;
import cn.wmkfe.blog.util.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(value = "/admin")
public class LinkAPIController {
    @Autowired
    private LinkService linkService;



    @Operation("获取链接列表")
    @GetMapping("/links")
    public Map<String ,Object> links(@RequestParam(value = "page",required = false)String page, @RequestParam(value = "limit",required = false)String limit, Link link){
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
        int count = linkService.countLink(link);
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
        List<Link> listLink = linkService.getListLink(currentPage, pageSize, link);
        map.put("code",200);
        map.put("count",count);
        map.put("msg", ConstantValue.SUCCESS);
        map.put("data",listLink);
        return map;
    }



    //添加链接
    @Operation("添加链接")
    @PostMapping("/links")
    public Map<String,Object> addLinks(@Valid Link link){
        Map<String,Object> map=new HashMap<>();
        Link byNameLink = linkService.getByNameLink(link.getWebsiteName());
        if(byNameLink!=null){
            map.put("code",202);
            map.put("msg","该昵称已存在");
            map.put("data",null);
        }else {
            link.setCreateTime(new Date());
            linkService.saveLink(link);
            map.put("code",200);
            map.put("msg",ConstantValue.SUCCESS);
            map.put("data",null);
        }
        return map;
    }

    //删除链接
    @Operation("删除链接")
    @DeleteMapping("/links/{ids}")
    public Map<String,Object> deleteLinks(@PathVariable(value = "ids")String[] ids){
        Map<String,Object> map=new HashMap<>();
        int[] arr =new int [ids.length];
        for (int i=0;i<ids.length;i++) {
            ids[i].trim();
            if(ids[i]!=null&&!"".equals(ids[i])){
                arr[i]=Integer.valueOf(ids[i]);
            }
        }
        linkService.removeLink(arr);
            map.put("code",200);
            map.put("msg",ConstantValue.SUCCESS);
            map.put("data",null);
        return map;
    }

    //根据id查询链接
    @Operation("根据id查询链接")
    @GetMapping("/links/{id}")
    public Map<String,Object> findById(@PathVariable(value = "id")String id){
        Map<String,Object> map=new HashMap<>();
        Link Link=null;
        if(id!=null&!"".equals(id)){
            Link = linkService.getOneLink(Integer.valueOf(id));
        }
        map.put("code",202);
        map.put("msg","未找到相关数据");
        if (Link!=null){
            map.put("code",200);
            map.put("msg",ConstantValue.SUCCESS);
            map.put("data",Link);
        }
        return map;
    }

    //更新链接
    @Operation("更新链接")
    @PutMapping("/links")
    public Map<String,Object> updateLinks(@Valid Link link){
        Map<String,Object> map=new HashMap<>();
        linkService.updateLink(link);
            map.put("code",200);
            map.put("msg",ConstantValue.SUCCESS);
            map.put("data",null);
        return map;
    }
}
