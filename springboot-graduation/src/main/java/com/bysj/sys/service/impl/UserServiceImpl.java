package com.bysj.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bysj.common.model.TreeNode;
import com.bysj.common.util.MD5Util;
import com.bysj.common.util.TreeUtil;
import com.bysj.sys.entity.*;
import com.bysj.sys.mapper.*;
import com.bysj.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.net.ResourceManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jack
 * @since 2020-01-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ExaminteacherMapper examinteacherMapper;

    /**
     * 根据用户类型查询用户角色名称、个人信息 （rolename,userid,username）
     */
    @Override
    public User_Role selectUserRoleByUserType(String username) {
        User_Role ur = new User_Role();
        //根据用户名查出用户类型
        QueryWrapper<User> queryWrapperUser = new QueryWrapper<>();
        queryWrapperUser.eq("user_id",username);
        User user = userMapper.selectOne(queryWrapperUser);
        Integer usertype = user.getUsertype();
        //根据用户类型查出角色信息
        QueryWrapper<Role> wrapperRole = new QueryWrapper<>();
        wrapperRole.eq("role_id",usertype);

        Role role = roleMapper.selectOne(wrapperRole);
        ur.setRoleName(role.getRolename());
        //根据用户类型查找对应的用户个人信息
        if(usertype==0){
            //设置查询条件
            QueryWrapper<Admin> wrapperAdmin = new QueryWrapper<>();
            wrapperAdmin.eq("admin_id",username);

            Admin admin = adminMapper.selectOne(wrapperAdmin);
            ur.setUserName(admin.getAdminName());
            ur.setUserId(admin.getAdminId());
        }else if(usertype==1){
            QueryWrapper<Teacher> wrapperTea = new QueryWrapper<>();
            wrapperTea.eq("tea_id",username);

            Teacher teacher = teacherMapper.selectOne(wrapperTea);
            ur.setUserName(teacher.getTeaName());
            ur.setUserId(teacher.getTeaId());
        }else if(usertype==2){
            QueryWrapper<Student> wrapperStu = new QueryWrapper<>();
            wrapperStu.eq("stu_id",username);

            Student student = studentMapper.selectOne(wrapperStu);
            ur.setUserName(student.getStuName());
            ur.setUserId(student.getStuId());
            ur.setStudentClass(student.getStuClass());
        }else{
            QueryWrapper<Examinteacher> wrapperExaTea = new QueryWrapper<>();
            wrapperExaTea.eq("examintea_id",username);

            Examinteacher examinteacher = examinteacherMapper.selectOne(wrapperExaTea);
            ur.setUserName(examinteacher.getExaminteaName());
            ur.setUserId(examinteacher.getExaminteaId());
        }
        return ur;
    }

    @Override
    public List<TreeNode> getMenuTreeByUserId(String id) {
        // 查询用户拥有的菜单资源
        List<Menu> menuList = userMapper.selectMenuList(id);
        if(menuList.isEmpty()){
            return new ArrayList<>();
        }
        // 存储父id是0的节点的id
        List<Integer> nodeIds = new ArrayList<>();
        List<TreeNode> treeNodeList = new ArrayList<>();
        for (Menu menu : menuList) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(menu.getResourceId());
            treeNode.setName(menu.getName());
            treeNode.setParentId(menu.getParentId());
            treeNode.setUrl(menu.getUrl());
            treeNode.setIcon(menu.getIcon());
            treeNodeList.add(treeNode);
            if(treeNode.getParentId() == 0) {
                //记录根节点id
                nodeIds.add(treeNode.getId());
            }
        }
        TreeUtil treeUtil = new TreeUtil(treeNodeList);
        List<TreeNode> treeNodeData = new ArrayList<>();
        for (Integer nodeId : nodeIds) {
            treeNodeData.add(treeUtil.generateTree(nodeId));
        }
        return treeNodeData;
    }

    @Override
    public Integer updateUserData(String adminName, String adminTelphone, String adminEmail) {
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //根据用户对象获取用户类型
        QueryWrapper<User> queryWrapperUser = new QueryWrapper<>();
        queryWrapperUser.eq("user_id",user.getUserId());
        Integer userType = userMapper.selectOne(queryWrapperUser).getUsertype();
        //根据用户类型修改对应用户信息
        if(userType==0){
            QueryWrapper<Admin> queryWrapperAdmin = new QueryWrapper<>();
            queryWrapperAdmin.eq("admin_id",user.getUserId());
            //设置用户更新后的信息
            Admin admin = new Admin();
            admin.setAdminName(adminName);
            admin.setAdminTelphone(adminTelphone);
            admin.setAdminEmail(adminEmail);
            Integer index = adminMapper.update(admin,queryWrapperAdmin);
            return index;
        }else if(userType==1){
            QueryWrapper<Teacher> queryWrapperTeacher = new QueryWrapper<>();
            queryWrapperTeacher.eq("tea_id",user.getUserId());
            //设置用户更新后的信息
            Teacher teacher = new Teacher();
            teacher.setTeaName(adminName);
            teacher.setTeaTelphone(adminTelphone);
            teacher.setTeaEmail(adminEmail);
            Integer index = teacherMapper.update(teacher,queryWrapperTeacher);
            return index;
        }else if(userType==2){
            QueryWrapper<Student> queryWrapperStudent = new QueryWrapper<>();
            queryWrapperStudent.eq("stu_id",user.getUserId());
            //设置用户更新后的信息
            Student student = new Student();
            student.setStuName(adminName);
            student.setStuTelphone(adminTelphone);
            student.setStuEmail(adminEmail);
            Integer index = studentMapper.update(student,queryWrapperStudent);
            return index;
        }else {
            QueryWrapper<Examinteacher> queryWrapperExaminteacher = new QueryWrapper<>();
            queryWrapperExaminteacher.eq("examintea_id",user.getUserId());
            //设置用户更新后的信息
            Examinteacher examinteacher = new Examinteacher();
            examinteacher.setExaminteaName(adminName);
            examinteacher.setExaminteaTelphone(adminTelphone);
            examinteacher.setExaminteaEmail(adminEmail);
            Integer index = examinteacherMapper.update(examinteacher,queryWrapperExaminteacher);
            return index;
        }
    }

    @Override
    public Object getUsersDataById(String id) {
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //根据用户对象获取用户类型
        QueryWrapper<User> queryWrapperUser = new QueryWrapper<>();
        queryWrapperUser.eq("user_id",user.getUserId());
        Integer userType = userMapper.selectOne(queryWrapperUser).getUsertype();

        //创建用户个人信息对象
        UsersData usersData = new UsersData();
        //根据用户类型查询用户信息
        if(userType==0){
            QueryWrapper queryWrapperAdmin = new QueryWrapper();
            queryWrapperAdmin.eq("admin_id",id);
            Admin admin =  adminMapper.selectOne(queryWrapperAdmin);
            usersData.setUsersId(admin.getAdminId());
            usersData.setUsersName(admin.getAdminName());
            usersData.setUsersEmail(admin.getAdminEmail());
            usersData.setUsersTelphone(admin.getAdminTelphone());
            return usersData;
        }else if(userType==1){
            QueryWrapper queryWrapperTeacher = new QueryWrapper();
            queryWrapperTeacher.eq("tea_id",id);
            Teacher teacher =  teacherMapper.selectOne(queryWrapperTeacher);
            usersData.setUsersId(teacher.getTeaId());
            usersData.setUsersName(teacher.getTeaName());
            usersData.setUsersEmail(teacher.getTeaEmail());
            usersData.setUsersTelphone(teacher.getTeaTelphone());
            return usersData;
        }else if(userType==2){
            QueryWrapper queryWrapperStudent = new QueryWrapper();
            queryWrapperStudent.eq("stu_id",id);
            Student student =  studentMapper.selectOne(queryWrapperStudent);
            usersData.setUsersId(student.getStuId());
            usersData.setUsersName(student.getStuName());
            usersData.setUsersEmail(student.getStuEmail());
            usersData.setUsersTelphone(student.getStuTelphone());
            return usersData;
        }else {
            QueryWrapper queryWrapperExaminTeacher = new QueryWrapper();
            queryWrapperExaminTeacher.eq("examintea_id",id);
            Examinteacher examinteacher =  examinteacherMapper.selectOne(queryWrapperExaminTeacher);
            usersData.setUsersId(examinteacher.getExaminteaId());
            usersData.setUsersName(examinteacher.getExaminteaName());
            usersData.setUsersEmail(examinteacher.getExaminteaEmail());
            usersData.setUsersTelphone(examinteacher.getExaminteaTelphone());
            return usersData;
        }
    }

    @Override
    public Integer updateUserPassword(String id,String comfirmPassword) {
        QueryWrapper queryWrapperUser = new QueryWrapper();
        queryWrapperUser.eq("user_id",id);
        User user = new User();
        user.setPassword(MD5Util.md5_public_salt(comfirmPassword));
       Integer index =  userMapper.update(user,queryWrapperUser);
       return index;
    }

    @Override
    public User confirmOldPassword(String oldPassword) {
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //设置查询用户条件
        QueryWrapper queryWrapperOldPass = new QueryWrapper();
        queryWrapperOldPass.eq("user_id",user.getUserId());
        queryWrapperOldPass.eq("password", MD5Util.md5_public_salt(oldPassword));

        User userSelect = userMapper.selectOne(queryWrapperOldPass);
        return userSelect;
    }

    @Override
    public List<Quartz> getProcessControlData() {
        return userMapper.getProcessControlData();
    }

    @Override
    @Transactional
    public Integer setProcessControlData(Quartz quartz) {
        //20 学生拟题 sys:student:assignTopic
        //19 学生选题 sys:student:chooseTopic
        //16 导师出题 sys:teacher:assignTopic
        //17 导师审核学生拟题  sys:teacher:examin
        //25 导师审核学生选题  sys:teacher:examinStudentChooseTopic
        //14 审题老师审核 sys:admin:check

        //设置定时任务表
        if(quartz.getqCron()==null||quartz.getqCron()==""){
            quartz.setqCron(null);
        }
        Integer index =  userMapper.setProcessControlData(quartz);
        //设置权限表
        if(quartz.getqCron()==null||quartz.getqCron()=="") {  //代表关闭
            if(quartz.getqName().equals("出题")){
                index+= userMapper.updateResource(20,null);
                index+= userMapper.updateResource(16,null);
                index+= userMapper.updateResource(17,null);
            }else if(quartz.getqName().equals("选题")){
                index+= userMapper.updateResource(19,null);
                index+= userMapper.updateResource(25,null);
                index=index+1;
            }else {
                index+= userMapper.updateResource(14,null);
                index=index+2;
            }
        }else {               //开启
            if(quartz.getqName().equals("出题")){
                index+= userMapper.updateResource(20,"sys:student:assignTopic");
                index+= userMapper.updateResource(16,"sys:teacher:assignTopic");
                index+= userMapper.updateResource(17,"sys:teacher:examin");
            }else if(quartz.getqName().equals("选题")){
                index+= userMapper.updateResource(19,"sys:student:chooseTopic");
                index+= userMapper.updateResource(25,"sys:teacher:examinStudentChooseTopic");
                index=index+1;
            }else {
                index+= userMapper.updateResource(14,"sys:admin:check");
                index=index+2;
            }
        }
        return index;
    }

    @Override
    public Integer getRemainingTime(String taskName) {
        List<Quartz> quartzs  = userMapper.getProcessControlData();
        Integer c =0;
        String deadline="";
        if(taskName.equals("出题")) {
            //获取出题剩余时间
            deadline = quartzs.get(0).getqCron();
        }else if(taskName.equals("审题")) {
            deadline = quartzs.get(1).getqCron();
        }else {
            deadline = quartzs.get(2).getqCron();
        }

        if(deadline!=null){
           String deTime = deadline+" "+"23:59:59";
           SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           try {
               Date date = format.parse(deTime); //转成时间格式
               long a = date.getTime();           //获取截止时间秒数
               long b = new Date().getTime();   //获取现在时间秒数
                c=(int)((a - b)/1000);     //剩余秒数
           } catch (ParseException e) {
               e.printStackTrace();
           }
        }else{
           return c;
        }
        return c;
    }
}
