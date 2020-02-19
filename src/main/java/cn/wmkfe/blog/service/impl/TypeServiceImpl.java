package cn.wmkfe.blog.service.impl;

import cn.wmkfe.blog.NotFoundException;
import cn.wmkfe.blog.dao.TypeMapper;
import cn.wmkfe.blog.model.Tag;
import cn.wmkfe.blog.model.Type;
import cn.wmkfe.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;
    @Transactional
    @Override
    public int saveType(Type type) {
        return typeMapper.save(type);
    }

    @Transactional
    @Override
    public int removeType(int[] ids) {
        return typeMapper.remove(ids);
    }

    @Transactional
    @Override
    public int updateType(Type type) {
        return typeMapper.update(type);
    }

    @Override
    public Type getType(int id) {
        Type one = typeMapper.getOne(id);
        if(one==null){
            throw new NotFoundException("不存在该类型");
        }
        return one;
    }

    @Override
    public List<Type> getTypeList(Integer currentPage, Integer pageSize,Type type) {
        List<Type> list=null;
        if(currentPage!=null && pageSize!=null) {
            list=typeMapper.getList((currentPage-1)*pageSize,pageSize,type);
        }else {
            list=typeMapper.getList(null,null,null);
        }
        return list;
    }

    @Override
    public int countType(Type type) {
        return typeMapper.count(type);
    }

    @Override
    public Type getTypeByName(String typeName) {
        return typeMapper.getByName(typeName);
    }

    @Override
    public List<Type> getAllType() {
        return typeMapper.getAll();
    }
}
