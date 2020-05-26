<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>毕设选题系统 - 导师对学生选题审核页面</title>
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
<!-- 模态框（Modal） -->
<div class="modal fade" id="studentDataModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="stuModalLabel">拟题学生信息</h4>
            </div>
            <div class="modal-body" style="width: 60%;margin-left: auto;margin-right: auto;">
                <div><label style="width: 50%">学号</label> <span id="stuId" style="width: 50%"></span></div>
                <div><label style="width: 50%">姓名</label> <span id="stuName" style="width: 50%"></span></div>
                <div><label style="width: 50%">班级</label> <span id="class" style="width: 50%"></span></div>
                <div><label style="width: 50%">学院(部)</label> <span id="stuCollege" style="width: 50%"></span></div>
                <div><label style="width: 50%">专业</label> <span id="stuEducation" style="width: 50%"></span></div>
                <div><label style="width: 50%">电话</label> <span id="stuTelphone" style="width: 50%"></span></div>
                <div><label style="width: 50%">邮箱</label> <span id="stuEmail" style="width: 50%"></span></div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<div class="page-title">
    <div class="title_left" style="float: left" >
        <h3>学生选题审核</h3>
    </div>
    <div style="position: absolute;z-index: -100;right: 60px;top: -1px"><img src="/static/img/blackboard.jpg" ></div>
    <div class="title_right" style="z-index: 1000;" >
        <span style="font-size: 15px;position: absolute;right:95px;color: #FFFFFF">审核选题剩余:</span><br>
        <div style="position: absolute;right: 70px">
            <div  id="countdown" class="container timeBar" data="${balanceTime}" style="font-size: 16px;color: #FFFFFF"></div>
        </div>
        <div id="over" style="font-size: 15px;color: #FFFFFF;display: none;position: absolute;right: 110px">已结束</div>
    </div>
</div>
<div class="x_panel">
    <div class="row x_title">
        <h2>学生选题列表</h2>
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
<script src="${ctx}/static/lib/countdown/countdown.js"></script>

<script>
    var list_url = '${ctx}/sys/teacher/getExaminStudentChooseTopicList';
    // 初始化表格数据
    var dataTable = $('#data-table').bootstrapTable('destroy').bootstrapTable({
        url: list_url,                      //  请求后台的URL
        method: "get",                      //  请求方式
        uniqueId: "tNo",                     //  每一行的唯一标识，一般为主键列
        cache: false,                       //  设置为 false 禁用 AJAX 数据缓存， 默认为true
        pagination: true,                   //  是否显示分页
        sidePagination: "client",           //  分页方式：client客户端分页，server服务端分页
        pageSize: 10,                       //  每页的记录行数
        pageList:[5,10,20],
        queryParamsType: '',
        queryParams: function (param) {
            return {
                pageNum: param.pageNumber,
                pageSize: param.pageSize,
            }
        },
        columns: [ {
            field: 'tNo',
            title: '课题编号'
        }, {
            field: 'tTitle',
            title: '课题名称',
            sortable: true
        },{
            field: 'tChoosestudent',
            title: '学生学号',
            visible:false
        },{
            field: 'tChooseStudentName',
            title: '选题学生',
            formatter: function(value,row) {
                return value +'<a href="#studentDataModal" onclick="getStudentData(\''+row.tChoosestudent+'\')">'+' [详情]'+'</a>'
            },
            sortable: true
        },{
            field: 'tNo',
            title: '操作',
            align: 'center',
            formatter: function(value){
                return ' <shiro:hasPermission name="sys:teacher:examinStudentChooseTopic"><a href="javascript:void(0);"  class="btn btn-info btn-sm" style="margin-bottom: 0px"  onclick="chooseTopicByStudentAgree(\''+value+'\')">'+ '<span><i class="fa fa-check"/>同意</span></a>'
                    +'<a href="javascript:void(0);"  class="btn btn-danger btn-sm" style="margin-bottom: 0px"  onclick="chooseTopicByStudentDisagree(\''+value+'\')">'+ '<span><i class="fa fa-times"/>拒绝</span></a></shiro:hasPermission>'
            }
        }]
    });
    // 刷新表格
    function refreshTable() {
        dataTable.bootstrapTable('refresh', {
            url: list_url,
            pageSize: 10,
            pageNumber: 1
        });
    }
    //同意
    function chooseTopicByStudentAgree(s) {
        window.parent.layer.confirm("确认同意?", {icon: 3, offset: 't'}, function () {
            $.ajax({
                url: '${ctx}/sys/teacher/chooseTopicByStudentAgree/' + s,
                type: 'get',
                success: function (response) {
                    if (response.code == 0) {
                        window.parent.layer.msg(response.msg, {icon: 1, time: 1000, offset: 't'});
                        refreshTable();
                    } else {
                        window.parent.layer.alert(response.msg, {icon: 5, offset: 't'});
                    }
                }
            });
        })
    }
    //拒绝
    function chooseTopicByStudentDisagree(s) {
        window.parent.layer.confirm("确认拒绝?", {icon: 3, offset: 't'}, function () {
            $.ajax({
                url: '${ctx}/sys/teacher/chooseTopicByStudentDisagree/' + s,
                type: 'get',
                success: function (response) {
                    if (response.code == 0) {
                        window.parent.layer.msg(response.msg, {icon: 1, time: 1000, offset: 't'});
                        refreshTable();
                    } else {
                        window.parent.layer.alert(response.msg, {icon: 5, offset: 't'});
                    }
                }
            });
        })
    }
    //获取学生信息详情
    function getStudentData(s) {
        $.ajax({
            url: '${ctx}/sys/teacher/getAssignTopicByStudentData/' + s,
            type: 'get',
            success: function (response) {
                if (response.code == 0) {
                    $("#stuId").text(response.stuData.stuId);
                    $("#stuName").text(response.stuData.stuName);
                    $("#class").text(response.stuData.stuClassName);
                    $("#stuTelphone").text(response.stuData.stuTelphone);
                    $("#stuEmail").text(response.stuData.stuEmail);
                    $("#stuCollege").text(response.stuData.stuCollege);
                    $("#stuEducation").text(response.stuData.stuEducation);

                    $("#studentDataModal").modal()
                } else {
                    window.parent.layer.alert(response.msg, {icon: 5, offset: 't'});
                }
            }
        });
    }
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
                    $("#countdown").css("display", "none")
                    $("#over").css("display", "")
                });
            });
        }else if($("#countdown").attr("data")==0){                 //结束
            $("#countdown").css("display", "none")
            $("#over").css("display", "")
        }
    });
    window.onload=function(){
        parent.scrollTo(0,0)
    }
</script>
</body>
</html>
