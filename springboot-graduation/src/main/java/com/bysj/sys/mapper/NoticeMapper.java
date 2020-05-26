package com.bysj.sys.mapper;

import com.bysj.sys.entity.MyNotice;
import com.bysj.sys.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jack
 * @since 2020-03-23
 */
public interface NoticeMapper extends BaseMapper<Notice> {
    /**
     * 根据发送人Id 获取公告列表
     * @return
     */
    List<MyNotice> getDeliverNoticeList(@Param("id") String id);
    /**
     * 根据接收角色Id和组织Id 获取公告列表  (用于指导老师、审题老师获取)
     */
    List<MyNotice> getReceiveNoticeList(@Param("rollId") Integer rollId, @Param("collId") Integer collId);

    /**
     * 根据 组织Id、发送方id 获取公告列表  (用于学生获取)
     */
    List<MyNotice> getReceiveNoticeListForStudent(@Param("collId") Integer collId, @Param("resourceId") String resourceId);

}
