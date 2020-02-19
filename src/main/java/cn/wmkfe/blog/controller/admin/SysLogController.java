package cn.wmkfe.blog.controller.admin;

import cn.wmkfe.blog.annotation.Operation;
import cn.wmkfe.blog.model.Link;
import cn.wmkfe.blog.model.SysLog;
import cn.wmkfe.blog.service.SysLogService;
import cn.wmkfe.blog.util.ConstantValue;
import cn.wmkfe.blog.util.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    @Operation("系统日志页面")
    @GetMapping("/logs.html")
    public String linkPage(){
        return "admin/systemSetting/logs";
    }
}
