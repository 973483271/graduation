package com.bysj.sys.controller;


import com.bysj.common.model.R;
import com.bysj.sys.service.INoticeService;
import com.bysj.sys.service.IUserMessageReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jack
 * @since 2020-03-25
 */
@Controller
@RequestMapping("/sys/notice")
public class NoticeController {
    @Autowired
    private INoticeService iNoticeServiceImpl;

    /**
     * 获取用户未读数据
     * @return
     */
    @GetMapping("/getNumOfNotRead")
    @ResponseBody
    public Integer getNumOfNotRead(){
        return iNoticeServiceImpl.getNumOfNotRead();
    }
}
