package com.bysj.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bysj.sys.entity.Class;
import com.bysj.sys.mapper.ClassMapper;
import com.bysj.sys.service.IClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jack
 * @since 2020-02-05
 */
@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements IClassService {

    @Autowired
    private ClassMapper classMapper;
    @Override
    public List<Class> getStudentClass() {
        QueryWrapper qwClass = new QueryWrapper();
        return classMapper.selectList(qwClass);
    }
}
