package com.bysj.sys.service;

import com.bysj.common.model.R;
import com.bysj.sys.entity.MyNotice;
import com.bysj.sys.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jack
 * @since 2020-03-23
 */
public interface INoticeService extends IService<Notice> {
    /**
     * 根据用户Id获取发布通知列表
     */
    List<MyNotice> getDeliverNoticeListByUserId(String id);

    /**
     * 上传文件
     */
    List<String> uploadFile(MultipartFile[] files);

    /**
     * 发布通知
     */
    Integer deliverNotice(Notice notice, MultipartFile[] files);

    /**
     * 删除公告
     */
    Integer noticeDelete(Integer id);

    /**
     * 文件下载
     */
    void downloadFile(String fileName, HttpServletResponse response);

    /**
     * 根据id获取公告信息
     */
    MyNotice getNoticeOneDataById(Integer id);
    /**
     * 根据用户id获取接收通知列表
     */
    List<MyNotice> getReceiveNoticeListByUserId();
    /**
     * 获取未读消息数量
     */
    Integer getNumOfNotRead();
    /**
     *  查看发布通知（指导老师和管理员查看自己发布的通知，不用写进已读未读状态表）
     */
    MyNotice getDeliverNoticeOneDataById(Integer id);

}
