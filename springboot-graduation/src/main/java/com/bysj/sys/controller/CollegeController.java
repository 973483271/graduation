package com.bysj.sys.controller;


import com.bysj.common.model.R;
import com.bysj.sys.entity.College;
import com.bysj.sys.entity.College_Education;
import com.bysj.sys.service.ICollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 学院 控制器
 *
 * @author jack
 * @since 2020-02-04
 */
@Controller
@RequestMapping("/sys/college")
public class CollegeController {

    @Autowired
    private ICollegeService iCollegeServiceImpl;
    /**
     *获取学院（部）和专业信息
     */
    @GetMapping("/getEduAndColData")
    @ResponseBody
    public R getCollegeAndEducation(Model model){
     List<College_Education> colleges =  iCollegeServiceImpl.getCollegeAndEducation();
     return R.ok().put("data",colleges);
    }

}
