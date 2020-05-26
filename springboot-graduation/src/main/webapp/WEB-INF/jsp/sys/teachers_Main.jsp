<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>毕设选题系统 - 指导老师个人主页</title>
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
<!-- 模态框（Modal） -->
<div class="modal fade" id="examinTeacherDataModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
    <div class="modal-dialog" style="z-index:10001"  >
        <div class="modal-content"  >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="exmTeaModalLabel">审题教师信息</h4>
            </div>
            <div class="modal-body" style="width: 60%;margin-left: auto;margin-right: auto;">
                <div><label style="width: 50%">工号</label> <span id="exmTeaId" style="width: 50%"></span></div>
                <div><label style="width: 50%">姓名</label> <span id="exmTeaName" style="width: 50%"></span></div>
                <div><label style="width: 50%">职称</label> <span id="exmTeaEducation" style="width: 50%"></span></div>
                <div><label style="width: 50%">学院(部)</label> <span id="exmTeaCollege" style="width: 50%"></span></div>
                <div><label style="width: 50%">电话</label> <span id="exmTeaTelphone" style="width: 50%"></span></div>
                <div><label style="width: 50%">邮箱</label> <span id="exmTeaEmail" style="width: 50%"></span></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<div class="x_panel" >
    <span style="font-weight: bold;">指导教师:</span>&nbsp;&nbsp;${teacherData.teaName}
    <span style="font-weight: bold;margin-left: 70px">教师工号:</span>&nbsp;&nbsp;${teacherData.teaId}
    <span style="font-weight: bold;margin-left: 70px">学校:</span>&nbsp;&nbsp;${teacherData.teaSchool}
    <span style="font-weight: bold;margin-left: 70px">学院(部):</span>&nbsp;&nbsp;${teacherData.teaCollege}
    <span style="font-weight: bold;margin-left: 70px">职称:</span>&nbsp;&nbsp;${teacherData.teaEducation}
    <div style="margin-top: 18px"><span style="font-weight: bold">学生人数:</span>&nbsp;&nbsp;<span style="color: red;font-size: 20px">${teacherData.teaStudentNum}</span></div>
</div>

<div class="x_panel">
    <div class="row x_title">
        <h2>学生选题列表</h2>
    </div>
    <div class="x_content">
        <button type="button" class="btn btn-default btn-sm" onclick="exportData()" ><i class="fa fa-download"></i>导出</button>
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
<%--导出数据--%>
<script src="${ctx}/static/lib/bootstrap-table/FileSaver.min.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/xlsx.core.min.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/jspdf.min.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/jspdf.plugin.autotable.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/es6-promise.auto.min.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/html2canvas.min.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/tableExport.min.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table-export.js"></script>

<script>
    var list_url = '${ctx}/sys/teacher/getStudentChooseTopic';
    // 初始化表格数据
    var dataTable = $('#data-table').bootstrapTable('destroy').bootstrapTable({
        url: list_url,                      //  请求后台的URL
        method: "get",                      //  请求方式
        uniqueId: "tNo",                     //  每一行的唯一标识，一般为主键列
        cache: false,                       //  设置为 false 禁用 AJAX 数据缓存， 默认为true
        pagination: true,                   //  是否显示分页
        sidePagination: "client",           //  分页方式：client客户端分页，server服务端分页
        pageSize: 15,                       //  每页的记录行数
        queryParamsType: '',
        exportDataType:"all",
        exportTypes: ['json', 'xml', 'png', 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf'],//导出格式
        exportOptions: {//导出设置
            fileName: '指导老师出题题目汇总'//下载文件名称
        },
        queryParams: function (param) {
            return {
                pageNum: param.pageNumber,
                pageSize: param.pageSize
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
            field: 'tExaminteacher',
            title: '审题教师工号',
            visible:false,
            sortable: true
        },{
            field: 'tExaminTeacherName',
            formatter: function(value,row) {
                return  '<a href="#examinTeacherDataModal" onclick="getExaminTeacherData(\''+row.tExaminteacher+'\')">'+value+'</a>'
            },
            title: '审题教师',
            sortable: true
        },{
            field: 'tChooseStudentName',
            title: '选题学生',
            formatter: function(value,row) {
                return '<a href="#studentDataModal" onclick="getStudentData(\''+row.tChoosestudent+'\')">'+value +'</a>'
            },
            sortable: true
        },
            {
            field: 'tNo',
            title: '操作',
            formatter: function(value){
                return '<a href="javascript:void(0);"  class="btn btn-danger btn-sm" style="margin-bottom: 0px"  onclick="deleData(\''+value+'\')">'+ '<span><i class="fa fa-reply-all"/> 撤回</span></a>'
            }
       }
       ]
    });

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
    //获取审核老师详情
    function getExaminTeacherData(s) {
        $.ajax({
            url: '${ctx}/sys/teacher/getExaminTopicByExaminTeacherData/'+s,
            type: 'get',
            success: function (response) {
                if (response.code == 0) {
                    $("#exmTeaId").text(response.exaTeaData.teaId);
                    $("#exmTeaName").text(response.exaTeaData.teaName);
                    $("#exmTeaEducation").text(response.exaTeaData.teaEducation);
                    $("#exmTeaTelphone").text(response.exaTeaData.teaTelphone);
                    $("#exmTeaEmail").text(response.exaTeaData.teaEmail);
                    $("#exmTeaCollege").text(response.exaTeaData.teaCollege);

                    $("#examinTeacherDataModal").modal()
                } else {
                    window.parent.layer.alert(response.msg, {icon: 5, offset: 't'});
                }
            }
        });
    }

    // 自定义按钮导出数据
    function exportData(){
        $('#data-table').tableExport({
            type: 'excel',
            exportDataType: "selected",
            ignoreColumn: [4],//忽略某一列的索引
            fileName: '学生选题信息汇总',//下载文件名称
            onCellHtmlData: function (cell, row, col, data){//处理导出内容,自定义某一行、某一列、某个单元格的内容
                console.info(data);
                return data;
            }
        });
    }
    //删除学生选题
    function deleData(e) {
        window.parent.layer.confirm("确认撤回?", {icon: 3, offset: 't'}, function (){
        $.ajax({
            url: '${ctx}/sys/teacher/deleteStudentChooseTopic/'+e,
            type: 'get',
            success: function (response) {
                if (response.code == 0) {
                    window.parent.layer.msg(response.msg, {icon: 1, time: 1000, offset: 't'});
                    window.location.href = '${ctx}/sys/teacher/teacherMain';
                } else {
                    window.parent.layer.alert(response.msg, {icon: 5, offset: 't'});
                }
            }
        });
        });
    }
    window.onload=function(){
        parent.scrollTo(0,0)
    }
</script>
</body>
</html>
