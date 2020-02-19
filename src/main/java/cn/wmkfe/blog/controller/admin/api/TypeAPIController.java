package cn.wmkfe.blog.controller.admin.api;

import cn.wmkfe.blog.annotation.Operation;
import cn.wmkfe.blog.model.Type;
import cn.wmkfe.blog.service.TypeService;
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
public class TypeAPIController {

    //后台
    @Autowired
    private TypeService typeService;

    //获取分类列表
    @Operation("查询分类列表")
    @GetMapping("/types")
    @ResponseBody
    public Map<String,Object> types(@RequestParam("page")String page, @RequestParam("limit")String limit, Type type){
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
        int count = typeService.countType(type);

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
        List<Type> typeList = typeService.getTypeList(currentPage, pageSize, type);
        map.put("code",200);
        map.put("count",count);
        map.put("msg",ConstantValue.SUCCESS);
        map.put("data",typeList);
        return map;
    }

    //添加分类
    @Operation("添加分类")
    @ResponseBody
    @PostMapping("/types")
    public Map<String,Object> addTypes(@Valid Type type){
        Map<String,Object> map=new HashMap<>();
        Type typeByName = typeService.getTypeByName(type.getTypeName());
        if(typeByName!=null){
            map.put("code",202);
            map.put("msg","此数据已存在");
            map.put("data",null);
        }else{
            typeService.saveType(type);
                map.put("code",200);
                map.put("msg",ConstantValue.SUCCESS);
                map.put("data",null);
        }
        return map;
    }

    //删除分类
    @Operation("删除分类")
    @ResponseBody
    @DeleteMapping("/types/{ids}")
    public Map<String,Object> deleteTypes(@PathVariable(value = "ids")String[] ids){
        Map<String,Object> map=new HashMap<>();
        int[] arr =new int [ids.length];
        for (int i=0;i<ids.length;i++) {
            ids[i].trim();
            if(ids[i]!=null&&!"".equals(ids[i])){
                arr[i]=Integer.valueOf(ids[i]);
            }
        }
        typeService.removeType(arr);
            map.put("code",200);
            map.put("msg",ConstantValue.SUCCESS);
            map.put("data",null);
        return map;
    }

    //根据id查询分类
    @Operation("根据id查询分类")
    @ResponseBody
    @GetMapping("/types/{id}")
    public Map<String,Object> findById(@PathVariable(value = "id")String id){
        Map<String,Object> map=new HashMap<>();
        Type type=null;
        id.trim();
        if(id!=null&!"".equals(id)){
             type = typeService.getType(Integer.valueOf(id));
        }
        map.put("code",202);
        map.put("msg","未找到相关数据");

        if (type!=null){
            map.put("code",200);
            map.put("msg",ConstantValue.SUCCESS);
            map.put("data",type);
        }
        return map;
    }

    //更新分类
    @Operation("更新分类")
    @ResponseBody
    @PutMapping("/types")
    public Map<String,Object> updateTypes(@Valid Type type){
        Map<String,Object> map=new HashMap<>();
        typeService.updateType(type);
            map.put("code",200);
            map.put("msg",ConstantValue.SUCCESS);
            map.put("data",null);
        return map;
    }


}
