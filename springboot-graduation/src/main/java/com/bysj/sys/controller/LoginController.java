package com.bysj.sys.controller;

import com.bysj.common.model.R;
import com.bysj.common.model.TreeNode;
import com.bysj.sys.entity.User;
import com.bysj.sys.entity.User_Role;
import com.bysj.sys.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 登录 控制器
 */
@Controller
@RequestMapping
public class LoginController {
    @Autowired
    private IUserService iUserServiceImpl;

    /**
     * 跳转到系统登录页面
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public R login(String username, String password) {

        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        return R.ok();
    }

    /**
     * 跳转到系统初始化页面
     * @return
     */
    @GetMapping("/index")
    public String index(HttpServletRequest request){
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //根据用户的角色类型查出用户的个人信息
        User_Role ur = iUserServiceImpl.selectUserRoleByUserType(user.getUserId());
        request.getSession().setAttribute("UR",ur);
        //设置消息铃铛跳转路径
        if(ur.getRoleName().equals("学生")){
            request.getSession().setAttribute("noticeUrl","student");
        }else if(ur.getRoleName().equals("指导老师")){
            request.getSession().setAttribute("noticeUrl","teacher");
        }else if(ur.getRoleName().equals("审题老师")){
            request.getSession().setAttribute("noticeUrl","examinteacher");
        }
        return "index";
    }
    /**
     * 跳转到系统主页
     * @return
     */
    @GetMapping("/main")
    public String main(){
        return "main";
    }
    /**
     * 根据用户id 获取资源菜单树
     * @return
     */
    @GetMapping("/menu")
    @ResponseBody
    public R menu(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<TreeNode> treeNodeList
                = iUserServiceImpl.getMenuTreeByUserId(user.getUserId());
        return R.ok("请求成功",treeNodeList);
    }
    /**
     * 退出
     * @return
     */
    @GetMapping("/logout")
    public String logout(){
        // 销毁会话
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }
}
