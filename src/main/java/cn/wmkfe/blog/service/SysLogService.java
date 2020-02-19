package cn.wmkfe.blog.service;

import cn.wmkfe.blog.model.SysLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogService {

    int saveSysLog(SysLog sysLog);

    SysLog getOneSysLog(int id);

    List<SysLog> getListSysLog(Integer currentPage, Integer pageSize,SysLog sysLog);

    int countSysLog(SysLog sysLog);

    List<SysLog> getAllSysLog();
}
