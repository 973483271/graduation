<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${ctx}/static/lib/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${ctx}/static/lib/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/static/lib/bootstrap-table/bootstrap-table.css" rel="stylesheet">
    <link href="${ctx}/static/css/custom.css" rel="stylesheet">
</head>
<body class="content_col">

<div class="page-title">
    <div class="title_left">
        <h3>欢迎使用</h3>
    </div>

</div>
<div>
    <span>
        此系统主要任务是协助学生完成毕业设计选题，系统分为管理员、
        指导教师、学生、审题教师四种角色，不同角色分配不同资源及功能。其中管理员利用系统可以添加
        、删除、修改用户信息，发布通知，对老师出题和学生选题情况
        进行统计等；指导教师可在此系统修改个人信息，发布毕设题目，对题目进行修改、删除，
        审核学生选题，发布和查看通知，查看学生选题信息等；学生可在此系统上修改个人信息
        ，查询和选择老师发布且审核通过的题目，通过选择题目确定自己导师，查看自己选题状态，
        学生也可自带题目选择自己心仪导师，查看相关通知等，审题教师可在此系统修改个人信息，
        审核指导老师出的题目，查看自己审题统计等。
        </span>
</div>
<br>
<div>
    <blockquote class="blockquote-reverse">
        <p>意志坚强的人能把世界放在手中像泥块一样任意揉捏。</p>
        <footer style="background-color: #F7F7F7">歌德</footer>
    </blockquote>
</div>

<script src="${ctx}/static/lib/jquery/jquery.js"></script>
<script src="${ctx}/static/lib/bootstrap/js/bootstrap.js"></script>
<script src="${ctx}/static/lib/layer/layer.js"></script>

<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table-zh-CN.js"></script>

<script src="${ctx}/static/lib/nprogress/nprogress.js"></script>
<script src="${ctx}/static/js/custom.js"></script>
</body>
</html>
