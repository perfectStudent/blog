package cn.wmkfe.blog.service.impl;

import cn.wmkfe.blog.dao.SysLogMapper;
import cn.wmkfe.blog.model.SysLog;
import cn.wmkfe.blog.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    @Transactional
    @Override
    public int saveSysLog(SysLog sysLog) {
        return sysLogMapper.save(sysLog);
    }

    @Override
    public SysLog getOneSysLog(int id) {
        return sysLogMapper.getOne(id);
    }

    @Override
    public List<SysLog> getListSysLog(Integer currentPage, Integer pageSize, SysLog sysLog) {
        return sysLogMapper.getList((currentPage-1)*pageSize,pageSize,sysLog);
    }

    @Override
    public int countSysLog(SysLog sysLog) {
        return sysLogMapper.count(sysLog);
    }

    @Override
    public List<SysLog> getAllSysLog() {
        return sysLogMapper.getAll();
    }
}
