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
<div class="x_panel" >
    <span style="font-weight: bold;">审题教师:</span>&nbsp;&nbsp;${examinTea.examinteaName}
    <span style="font-weight: bold;margin-left: 70px">教师工号:</span>&nbsp;&nbsp;${examinTea.examinteaId}
    <span style="font-weight: bold;margin-left: 70px">学校:</span>&nbsp;&nbsp;${examinTea.examinteaSchool}
    <span style="font-weight: bold;margin-left: 70px">学院(部):</span>&nbsp;&nbsp;${examinTea.teaCollege}
    <span style="font-weight: bold;margin-left: 70px">负责专业:</span>&nbsp;&nbsp;${examinTea.examinteaResEducation}
    <div style="margin-top: 18px">
        <span style="font-weight: bold;">职称:</span>&nbsp;&nbsp;${examinTea.examinteaEducation}
        <span style="font-weight: bold;margin-left: 70px">审核题数:</span>&nbsp;&nbsp;<span style="color: red;font-size: 20px">${examinTea.examinteaExamTopicNum}</span>
        <span style="font-weight: bold;margin-left: 70px">通过率:</span>&nbsp;&nbsp;<span style="color: red;font-size: 20px">${examinTea.examinteaExamTopicPassRate*100}%</span>
    </div>
</div>


<script src="${ctx}/static/lib/jquery/jquery.js"></script>
<script src="${ctx}/static/lib/bootstrap/js/bootstrap.js"></script>
<script src="${ctx}/static/lib/layer/layer.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script src="${ctx}/static/lib/nprogress/nprogress.js"></script>
<script src="${ctx}/static/js/custom.js"></script>

<script>
    window.onload=function(){
        parent.scrollTo(0,0)
    }
</script>
</body>
</html>
