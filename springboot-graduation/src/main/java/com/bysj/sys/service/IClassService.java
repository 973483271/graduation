package com.bysj.sys.service;

import com.bysj.sys.entity.Class;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jack
 * @since 2020-02-05
 */
public interface IClassService extends IService<Class> {
    List<Class> getStudentClass();
}
