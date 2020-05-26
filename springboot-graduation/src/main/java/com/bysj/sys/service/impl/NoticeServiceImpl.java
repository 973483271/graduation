package com.bysj.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bysj.common.model.R;
import com.bysj.sys.entity.*;
import com.bysj.sys.mapper.*;
import com.bysj.sys.service.INoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.JSONPObject;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jack
 * @since 2020-03-23
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {
    @Value("${upload.file.path}")
    private String filePath;   //自定义存储路径

    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private CollegeMapper collegeMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TeacherCollegeMapper teacherCollegeMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ExaminteacherMapper examinteacherMapper;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private UserMessageReadMapper userMessageReadMapper;

    @Override
    public List<MyNotice> getDeliverNoticeListByUserId(String id) {

        List<MyNotice> notices = noticeMapper.getDeliverNoticeList(id);
        //用于存储notice
        List<MyNotice>  noticeList = new ArrayList<>();
        for(MyNotice notice:notices){
            //设置发送方名称
            if(id.equals("admin")){      //管理员
                notice.setResourceName("管理员");
            }else {                       //指导老师
                QueryWrapper qw = new QueryWrapper();
                qw.eq("tea_id",id);
                String teaName = teacherMapper.selectOne(qw).getTeaName();
                notice.setResourceName("指导老师 ("+teaName+")");
            }
            //查询接收方类型   1指导老师  2学生  3审题员 4 全部
            Integer receiverolId = notice.getReceiverollId();
            if(receiverolId==1){
                notice.setReceiverollName("指导老师");
            }else if(receiverolId==2){
                notice.setReceiverollName("学生");
            }else if(receiverolId==3){
                notice.setReceiverollName("审题老师");
            }else {
                notice.setReceiverollName("全体师生");
            }
            //设置部门名称
            if(notice.getReceivecollId()!=null) {       //指导老师发布通知没有部门
                //查询部门名称
                QueryWrapper qwColl = new QueryWrapper();
                qwColl.eq("coll_id",notice.getReceivecollId());
                if (notice.getReceivecollId() != 0) {
                    String collegeName = collegeMapper.selectOne(qwColl).getCollName();
                    notice.setReceivecollName(collegeName);
                } else {
                    notice.setReceivecollName("全体学院(部)");
                }
            }
            noticeList.add(notice);
        }
        return noticeList;
    }

    @Override
    public List<String> uploadFile(MultipartFile[] files) {
        List<String> pathName = new ArrayList<>();
        for(MultipartFile file:files) {
            String fileName = "";
            String path="";
            if (file.isEmpty()) {
                continue;     //文件为空则返回null
            } else {
                //获取文件原名
                String name = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
                File localFile = new File(filePath);
                //路劲不在则创建
                if (!localFile.exists()) {
                    localFile.mkdirs();
                }
                //获取后缀名
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                //拼接新的文件名
                fileName = UUID.randomUUID().toString() + name + suffix;
                 path = filePath + fileName;
                try {
                    File server_file = new File(path);
                    //存储
                    file.transferTo(server_file);
                } catch (Exception e) {
                    return null;      //上传异常返回null
                }
            }
            pathName.add(fileName);
        }
        return pathName;
    }

    @Override
    @Transactional
    public Integer deliverNotice(Notice notice,MultipartFile[] files) {
        //1.存储文件
        List<String> paths = uploadFile(files);
        if(paths==null){
            return -1;
        }
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //获取公告发送人id
        String resourceId =  user.getUserId();
        //设置发送人id
        notice.setResourceId(user.getUserId());
        //获取文件路径
        StringBuilder sb = new StringBuilder();
        for(int i =0;i<paths.size();i++){
            if(i==0) {
                sb.append(paths.get(i));
            }else {
                sb.append(","+paths.get(i));
            }
        }
        //设置文件路径
        if(sb.length()!=0){
            notice.setFilePath(sb.toString());
        }else {
            notice.setFilePath(null);
        }
        //2.存储公告信息
        Integer index=  noticeMapper.insert(notice);
        //3.若公告存储失败 ，删除上传的文件
        if(index!=1){
           for(int i=0;i<paths.size();i++){
               File file = new File(filePath + paths.get(i));
               if (file.exists())
                   file.delete();
           }
        }
        return index;
    }

    @Override
    @Transactional
    public Integer noticeDelete(Integer id) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("id",id);
        //获取通知文件路径
        String pathName = noticeMapper.selectOne(qw).getFilePath();
        //删除通知
        Integer index = noticeMapper.delete(qw);
        //判断删除通知操作是否成功，成功则删文件
        if(index==1) {
            if (pathName != null) {
                //切割字符串
                if (pathName.contains(",")) {
                    String[] pathStr = pathName.split(",");
                    //删除文件
                    for (Integer i = 0; i < pathStr.length; i++) {
                        File file = new File(filePath + pathStr[i]);
                        if (file.exists())
                            file.delete();
                    }
                } else {
                    File file = new File(filePath + pathName);
                    if (file.exists())
                        file.delete();
                }
            }
        }
        return index;
    }

    @Override
    public void downloadFile(String fileName,HttpServletResponse response) {
        //通过文件的保存文件夹路径加上文件的名字来获得文件
        File file = new File(filePath, fileName);
        //当文件存在
        if (file.exists()) {
            //首先设置响应的内容格式是force-download，那么你一旦点击下载按钮就会自动下载文件了
            response.setContentType("application/force-download");
            //通过设置头信息给文件命名，也即是，在前端，文件流被接受完还原成原文件的时候会以你传递的文件名来命名
            String filename =  file.getName().substring(36);
            try{
                response.addHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(filename, "UTF-8"));
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
            //进行读写操作
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                //从源文件中读
                int i = bis.read(buffer);
                while (i != -1) {
                    //写到response的输出流中
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //善后工作，关闭各种流
                try {
                    if (bis != null) {
                        bis.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    @Override
    public MyNotice getNoticeOneDataById(Integer id) {
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        MyNotice myNotice = getDeliverNoticeOneDataById(id); //根据消息id获取消息信息
        //标记该条消息已读
        UserMessageRead u = new UserMessageRead(); //创建消息状态对象
        u.setuId(user.getUserId());        //设置用户id

        QueryWrapper qwUMR = new QueryWrapper();
        qwUMR.eq("u_id",user.getUserId());
        UserMessageRead umr = userMessageReadMapper.selectOne(qwUMR);
        if(umr==null){                  //第一次查看消息 该用户已读消息标记为空，需要创建
            Map<String,Boolean> map = new HashMap<>();
            map.put(id.toString(),true);
            u.setmRead(JSONObject.fromObject(map).toString());         //map 转为json字符串格式
            userMessageReadMapper.insert(u);            //插入数据
        }else {        //消息状态表存在已读消息 标记
            Map<String,Boolean> map = JSONObject.fromObject(umr.getmRead()); //json字符串转map对象
            if(!map.containsKey(id.toString())){           //当前消息标记为未读
                map.put(id.toString(),true);                 //设置为已读
            }
            u.setmRead(JSONObject.fromObject(map).toString());
            //更新数据
            UpdateWrapper uw = new UpdateWrapper();
            uw.eq("u_id",user.getUserId());
            userMessageReadMapper.update(u,uw);
        }
        return myNotice;
    }

    @Override
    public List<MyNotice> getReceiveNoticeListByUserId() {
        //用于存储查询的消息列表
        List<MyNotice> notices = new ArrayList<>();
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //1.获取用户角色类型
        QueryWrapper qwUserType = new QueryWrapper();
        qwUserType.eq("user_id",user.getUserId());
        Integer userType = userMapper.selectOne(qwUserType).getUsertype();
        //判断用户类型，是指导老师或审题老师就走  getReceiveNoticeList 方法。
        //2.获取用户所属组织
        Integer collId = 0;
        if(userType==1||userType==3) {
            if (userType == 1) {  //指导老师
                QueryWrapper qw = new QueryWrapper();
                qw.eq("tea_id", user.getUserId());
                collId = teacherCollegeMapper.selectOne(qw).getCollId();
            } else if (userType == 3) { //审题老师
                collId = examinteacherMapper.getExamincationIdByExaminId(user.getUserId());
            }
        //获取接收通知集合
         notices = noticeMapper.getReceiveNoticeList(userType,collId);
        }else {           //学生
            String resourceId = null;
            //获取学生学院id
            collId = studentMapper.getCollIdByStudentId(user.getUserId());
            //获取学生指导老师工号
            QueryWrapper qwTea = new QueryWrapper();
            qwTea.eq("t_chooseStudent",user.getUserId());
            qwTea.eq("t_status",3);   //课题状态为完成的学生才能接收知道老师通知
            Topic t = topicMapper.selectOne(qwTea);
            if(t!=null){
                resourceId = t.gettMaketeacher();
            }
            //获取接收通知集合
           notices = noticeMapper.getReceiveNoticeListForStudent(collId,resourceId);
        }
        //用于存储notice
        List<MyNotice>  noticeList = new ArrayList<>();
        for(MyNotice notice:notices){
            //设置发送方名称
            if(notice.getResourceId().equals("admin")){      //管理员
                notice.setResourceName("管理员");
            }else {                       //指导老师
                QueryWrapper qw = new QueryWrapper();
                qw.eq("tea_id",notice.getResourceId());
                String teaName = teacherMapper.selectOne(qw).getTeaName();
                notice.setResourceName("指导老师 ("+teaName+")");
            }
            //查询接收方类型   1指导老师  2学生  3审题员 4 全部
            Integer receiverolId = notice.getReceiverollId();
            if(receiverolId==1){
                notice.setReceiverollName("指导老师");
            }else if(receiverolId==2){
                notice.setReceiverollName("学生");
            }else if(receiverolId==3){
                notice.setReceiverollName("审题老师");
            }else {
                notice.setReceiverollName("全体师生");
            }
            //设置该消息的已读/未读状态
            QueryWrapper qwUMR = new QueryWrapper();
            qwUMR.eq("u_id",user.getUserId());
            UserMessageRead umr = userMessageReadMapper.selectOne(qwUMR);
            if(umr!=null){
                String jsonStr = umr.getmRead();
                //将json字符串转成Map对象
                Map<String,Boolean> map = JSONObject.fromObject(jsonStr);
                if(map.containsKey(notice.getId().toString())){       //存在该key,说明该条消息已读
                    notice.setStatus(true);
                }else {
                    notice.setStatus(false);
                }
            }else {
                notice.setStatus(false);
            }
            noticeList.add(notice);
        }
        return noticeList;
    }

    @Override
    public Integer getNumOfNotRead() {
        Integer index =0;
        List<MyNotice> notices =  getReceiveNoticeListByUserId();
        for(MyNotice notice:notices){
            if(notice.getStatus()==false){
              index++;
            }
        }
        return  index;
    }

    @Override
    public MyNotice getDeliverNoticeOneDataById(Integer id) {
        //shiro框架  查出当前用户对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        MyNotice myNotice = new MyNotice();
        List<String> pathList = new ArrayList<>();//存储文件路径

        QueryWrapper qw = new QueryWrapper();
        qw.eq("id",id);
        Notice notice = noticeMapper.selectOne(qw);
        //设置标题
        myNotice.setTitleName(notice.getTitleName());
        //设置正文
        myNotice.setTextName(notice.getTextName());
        //设置文件路径
        String p = notice.getFilePath();
        if(p!=null){
            String[] pathName =p.split(",");
            for(Integer i =0;i<pathName.length;i++){
                pathList.add(pathName[i]);
            }
        }
        myNotice.setPathName(pathList);
        return myNotice;
    }
}