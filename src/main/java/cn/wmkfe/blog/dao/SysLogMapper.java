package cn.wmkfe.blog.dao;

import cn.wmkfe.blog.model.SysLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogMapper {
    int save(SysLog sysLog);


    SysLog getOne(int id);

    List<SysLog> getList(@Param("from")Integer from, @Param("pageSize") Integer pageSize, @Param("sysLog") SysLog sysLog);

    int count(SysLog sysLog);

    List<SysLog> getAll();
}
