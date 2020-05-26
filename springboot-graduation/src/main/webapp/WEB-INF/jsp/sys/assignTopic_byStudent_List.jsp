<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>毕设选题系统 - 显示学生个人拟题信息</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${ctx}/static/lib/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${ctx}/static/lib/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/static/lib/bootstrap-table/bootstrap-table.css" rel="stylesheet">
    <link href="${ctx}/static/css/custom.css" rel="stylesheet">
    <style>
        td{
            white-space:nowrap;
            overflow:hidden;
            text-overflow:ellipsis;
        }
    </style>
</head>
<body class="content_col">

<div class="page-title" style="float: left">
    <div class="title_left" >
        <h3>学生拟题</h3>
    </div>
    <div style="position: absolute;z-index: -100;right: 60px;top: -1px"><img src="/static/img/blackboard.jpg" ></div>
    <div class="title_right" style="z-index: 1000;" >
        <span style="font-size: 15px;position: absolute;right:110px;color: #FFFFFF">拟题剩余:</span><br>
        <div style="position: absolute;right: 70px">
            <div  id="countdown" class="container timeBar" data="${balanceTime}" style="font-size: 16px;color: #FFFFFF"></div>
        </div>
        <div id="over" style="font-size: 15px;color: #FFFFFF;display: none;position: absolute;right: 110px">已结束</div>
    </div>
</div>
<div class="x_panel">
    <div class="row x_title">
        <h2>课题列表</h2>
    </div>
    <div class="x_content" >
        <div id="toolbar" class="btn-toolbar">
            <div class="btn-group">
                <shiro:hasPermission name="sys:student:assignTopic">
                   <a href="${ctx}/sys/student/assignTopicByStudentAdd" class="btn btn-default btn-sm"><i class="fa fa-plus"></i>学生拟题</a>
                </shiro:hasPermission>
            </div>
        </div>
        <table id="data-table"></table>
    </div>
</div>


<script src="${ctx}/static/lib/jquery/jquery.js"></script>
<script src="${ctx}/static/lib/bootstrap/js/bootstrap.js"></script>
<script src="${ctx}/static/lib/layer/layer.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script src="${ctx}/static/lib/nprogress/nprogress.js"></script>
<script src="${ctx}/static/js/custom.js"></script>
<script src="${ctx}/static/lib/jquery-star-rating/lib/jquery.raty.js"></script>
<script src="${ctx}/static/lib/countdown/countdown.js"></script>
<script>
    var list_url = '${ctx}/sys/student/assignTopicByStudentList';
    // 初始化表格数据
    var dataTable = $('#data-table').bootstrapTable('destroy').bootstrapTable({
        url: list_url,                      //  请求后台的URL
        method: "get",                      //  请求方式
        uniqueId: "tNo",                     //  每一行的唯一标识，一般为主键列
        cache: false,                       //  设置为 false 禁用 AJAX 数据缓存， 默认为true
        pagination: true,                   //  是否显示分页
        sidePagination: "client",           //  分页方式：client客户端分页，server服务端分页
        pageSize: 5,                       //  每页的记录行数
        queryParamsType: '',
        toolbar: "#toolbar",//显示工具栏
        queryParams: function (param) {
            return {
                pageNum: param.pageNumber,
                pageSize: param.pageSize,
                tNo: null
            }
        },
        columns: [
            {
            field: 'tNo',
            title: '课题编号'
        }, {
            field: 'tTitle',
            title: '课题名称'
        },{
            field: 'tTeacherName',
            title: '指导教师'
        },{
            field: 'tEducation',
            title: '课题所属组织'
        }, {
            field: 'tType',
            title: '课题类型'
        }, {
            field: 'tSource',
            title: '课题来源'
        },{
            field: 'tDifferent',
            title: '难度指数',
            formatter: function(value){  // 自定义方法，添加按钮组
                if(value==1){
                    return '<div ><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png">'
                }else if(value==2){
                    return '<div ><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png">'
                }else if(value==3){
                    return '<div ><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png">'
                }else if(value==4){
                    return '<div ><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png">'
                }else {
                    return '<div ><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png">'
                }
            }
        },{
            field: 'tMaketime',
            title: '出题时间'
        },{
            field: 'tStatus',
            title: '课题状态',
            formatter: function(value){
                if(value==0){
                    return '<span style="color: #F3AA00"">待审核</span>'
                }else if(value==1){
                    return '<span style="color: #F3AA00">导师同意</span>'
                }else if(value==2||value==3){
                    return '<span style="color: #1ABB9C">审核通过</span>'
                }else if(value==-1){
                    return '<span style="color: #ac2925">未通过</span>'
                }else if(value==-2){
                    return '<span style="color: #ac2925">导师拒绝</span>'
                }
            }
        },{
            field: 'tNo',
            title: '查看详情',
            formatter: function(value){
                return '<a href="${ctx}/sys/student/assignTopicByStudentShow/'+value+'" class="btn btn-default btn-sm" style="margin-bottom: 0px"><span>课题详情</span></a>'
            }},
        {
            field: 'tNo',
            title: '操作',
            formatter: function(value){
            return '<shiro:hasPermission name="sys:student:assignTopic"> <a href="${ctx}/sys/student/assignTopicByStudentUpdate/'+value+'" class="btn btn-info btn-sm" style="margin-bottom: 0px">'+ '<span><i class="fa fa-pencil"></i>修改</span></a> '
            +'<a href="javascript:void(0);"  class="btn btn-danger btn-sm" style="margin-bottom: 0px"  onclick="assignTopicByStudent(\''+value+'\')">'+ '<span><i class="fa fa-times"></i>删除</span></a> </shiro:hasPermission>'
            }
        }]
    });
    // 刷新表格
    function refreshTable() {
        dataTable.bootstrapTable('refresh', {
            url: list_url,
            pageSize: 5,
            pageNumber: 1
        });
    }
    //删除
    function assignTopicByStudent(s) {
        window.parent.layer.confirm("确认删除?", {icon: 3, offset: 't'}, function () {
            $.ajax({
                url: '${ctx}/sys/student/assignTopicByStudentDelete/' + s,
                type: 'get',
                success: function (response) {
                    if (response.code == 0) {
                        window.parent.layer.msg(response.msg, {icon: 1, time: 1000, offset: 't'});
                        window.location.href = '${ctx}/sys/student/assignTopic';
                    } else {
                        window.parent.layer.alert(response.msg, {icon: 5, offset: 't'});
                        window.location.href = '${ctx}/sys/student/assignTopic';
                    }
                }
            });
        })
    }
    //星星评分
    $(document).ready(function () {
        $("#star").raty({
            hints:['1','2','3','4','5'],
            path:"${ctx}/static/lib/jquery-star-rating/lib/img",
            starOff:'star-off.png',
            starOn:'star-on.png',
            score:3,
            number:5,
            readOnly:false,
            size:24,
            target:'.result',
            targetKeep:true,
            click:function (score,evt) {

            }
        })
    });
    //倒计时
    $(function() {
       if($("#countdown").attr("data")!=0) {    //倒计时开启
           $(".timeBar").each(function () {
               $(this).countdownsync({
                   dayTag: "<label class='tt dd dib vam'>00</label><span>天</span>",
                   hourTag: "<label class='tt hh dib vam'>00</label><span>时</span>",
                   minTag: "<label class='tt mm dib vam'>00</label><span>分</span>",
                   secTag: "<label class='tt ss dib vam'>00</label><span>秒</span>",
                   dayClass: ".dd",
                   hourClass: ".hh",
                   minClass: ".mm",
                   secClass: ".ss",
                   isDefault: false,
                   showTemp: 0

               }, function () {            //倒计时结束
                   $("#countdown").css("display", "none");
                   $("#over").css("display", "")
               });
           });
       }else if($("#countdown").attr("data")==0){                 //结束
           $("#countdown").css("display", "none");
           $("#over").css("display", "")
       }
    });
    window.onload=function(){
        parent.scrollTo(0,0)
    }
</script>

</body>
</html>
