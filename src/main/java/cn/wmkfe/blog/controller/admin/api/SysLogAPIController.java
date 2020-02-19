package cn.wmkfe.blog.controller.admin.api;

import cn.wmkfe.blog.annotation.Operation;
import cn.wmkfe.blog.model.SysLog;
import cn.wmkfe.blog.service.SysLogService;
import cn.wmkfe.blog.util.ConstantValue;
import cn.wmkfe.blog.util.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(value = "/admin")
public class SysLogAPIController {
    @Autowired
    private SysLogService sysLogService;

    @Operation("获取日志列表")
    @GetMapping("/logs")
    public Map<String ,Object> logs(@RequestParam(value = "page",required = false)String page, @RequestParam(value = "limit",required = false)String limit, SysLog sysLog){
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
        int count = sysLogService.countSysLog(sysLog);
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
        List<SysLog> listSysLog = sysLogService.getListSysLog(currentPage, pageSize, sysLog);
        map.put("code",200);
        map.put("count",count);
        map.put("msg", ConstantValue.SUCCESS);
        map.put("data",listSysLog);
        return map;
    }

}
