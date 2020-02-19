package cn.wmkfe.blog.service.impl;

import cn.wmkfe.blog.dao.StatisticsMapper;
import cn.wmkfe.blog.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsMapper statisticsMapper;

    @Override
    public int updateCount() {
        return statisticsMapper.updateViewCount();
    }

    @Override
    public int getCount() {
        return statisticsMapper.getViewCount();
    }
}
