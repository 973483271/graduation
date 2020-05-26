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
</head>
<body class="content_col">
<!-- 模态框（Modal） -->
<div class="modal fade" id="makeTeacherDataModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
    <div class="modal-dialog" style="z-index:10001"  >
        <div class="modal-content"  >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="makTeaDataModalLabel">指导老师信息</h4>
            </div>
            <div class="modal-body" style="width: 60%;margin-left: auto;margin-right: auto;">
                <div><label style="width: 50%">工号</label> <span id="id" style="width: 50%"></span></div>
                <div><label style="width: 50%">姓名</label> <span id="name" style="width: 50%"></span></div>
                <div><label style="width: 50%">职称</label> <span id="education" style="width: 50%"></span></div>
                <div><label style="width: 50%">学院(部)</label> <span id="college" style="width: 50%"></span></div>
                <div><label style="width: 50%">电话</label> <span id="telphone" style="width: 50%"></span></div>
                <div><label style="width: 50%">邮箱</label> <span id="email" style="width: 50%"></span></div>
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
    <span style="font-weight: bold;">选题学生:</span>&nbsp;&nbsp;${stuData.stuName}
    <span style="font-weight: bold;margin-left: 55px">学生学号:</span>&nbsp;&nbsp;${stuData.stuId}
    <span style="font-weight: bold;margin-left: 68px">学院(部):</span>&nbsp;&nbsp;${stuData.stuCollege}
    <span style="font-weight: bold;margin-left: 68px">学生专业:</span>&nbsp;&nbsp;${stuData.stuEducation}
    <span style="font-weight: bold;margin-left: 68px">班级:</span>&nbsp;&nbsp;${stuData.stuClass}
    <div style="margin-top: 20px">
        <span style="font-weight: bold;">课题名称:</span>&nbsp;&nbsp;<a href="${ctx}/sys/student/assignTopicByStudentShow/${stutopicData.tNo}">${stutopicData.tTitle}</a>
        <span style="font-weight: bold;margin-left: 68px">指导教师:</span>&nbsp;&nbsp;<a href="#" onclick="getTeacherData('${stutopicData.tMaketeacher}')">${stutopicData.tTeacherName}</a>
        <span style="font-weight: bold;margin-left: 68px">审题教师:</span>&nbsp;&nbsp;<a href="#" onclick="getExaminTeacherData('${stutopicData.tExaminteacher}')">${stutopicData.tExaminTeacherName}</a>
        <span style="font-weight: bold;margin-left: 68px">课题状态:</span>&nbsp;&nbsp;
        <c:if test="${stutopicData.tStatus==0||stutopicData.tStatus==1||stutopicData.tStatus==2||stutopicData.tStatus==-1||stutopicData.tStatus==-2||stutopicData.tStatus==null}">
            <span style="color: red">未完成</span>
        </c:if>
        <c:if test="${stutopicData.tStatus==3}">
            <span style="color: red">完成</span>
        </c:if>
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

    //查看导师详情
    function getTeacherData(s) {
        $.ajax({
            url: '${ctx}/sys/student/getAssignTopicTeacherData/'+s,
            type: 'get',
            success: function (response) {
                if (response.code == 0) {
                    $("#id").text(response.teaData.teaId);
                    $("#name").text(response.teaData.teaName);
                    $("#education").text(response.teaData.teaEducation);
                    $("#telphone").text(response.teaData.teaTelphone);
                    $("#email").text(response.teaData.teaEmail);
                    $("#college").text(response.teaData.teaCollege);
                    $("#makeTeacherDataModal").modal()

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
    window.onload=function(){
        parent.scrollTo(0,0)
    }
</script>
</body>
</html>
