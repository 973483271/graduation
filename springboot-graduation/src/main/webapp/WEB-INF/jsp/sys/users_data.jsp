<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>毕设选题系统 - 用户个人信息</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${ctx}/static/lib/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${ctx}/static/lib/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/static/lib/bootstrap-table/bootstrap-table.css" rel="stylesheet">
    <link href="${ctx}/static/css/custom.css" rel="stylesheet">
    <link href="${ctx}/static/lib/bootstrap/css/bootstrapValidator.min.css" rel="stylesheet">

</head>
<body class="content_col">

<div class="page-title">
    <div class="title_left">
        <h3>个人信息管理</h3>
    </div>
</div>
 <div class="x_panel" >
     <div class="row x_title">
        <h2>个人信息</h2>
     </div>
     <div class="x_content" style="margin-left: 150px">
     <form id="form1">
         <div class="form-group" style="width: 600px">
             <label for="usersId">学号/工号</label>
             <input type="text" class="form-control" id="usersId" value="" disabled="disabled" >
         </div>
         <div class="form-group" style="width: 600px">
             <label for="usersName">姓名</label>
             <input type="text" class="form-control" id="usersName" value="" name="adminName" >
         </div>
         <div class="form-group" style="width: 600px">
             <label for="usersTelphone">手机号</label>
             <input type="tel" class="form-control" id="usersTelphone" value="" name="adminTelphone">
         </div>
         <div class="form-group" style="width: 600px">
             <label for="usersEmail">电子邮箱</label>
             <input type="email" class="form-control" id="usersEmail" value="" name="adminEmail">
         </div>
         <div class="btn-toolbar" style="width: 60%;float: right">
             <div class="btn-group" >
                 <button type="button" class="btn btn-default btn-sm" id="btn-save" onclick="save()">保存</button>
             </div>

             <div class="btn-group" >
                 <button type="button" class="btn btn-default btn-sm" id="btn-reset">重置</button>
             </div>
         </div>

     </form>
     </div>
 </div>

<script src="${ctx}/static/lib/jquery/jquery.js"></script>
<script src="${ctx}/static/lib/bootstrap/js/bootstrap.js"></script>
<script src="${ctx}/static/lib/layer/layer.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script src="${ctx}/static/lib/nprogress/nprogress.js"></script>
<script src="${ctx}/static/js/custom.js"></script>
<script src="${ctx}/static/lib/bootstrap/js/bootstrapValidator.min.js"></script>

<script>
    $.ajax({
        url: '${ctx}/sys/user/list',
        type: 'get',
        dataType: 'json',
        success: function (response) {
            if (response.code == 0) {
              $("#usersId").attr("value",response.data.usersId);
              $("#usersName").attr("value",response.data.usersName);
              $("#usersTelphone").attr("value",response.data.usersTelphone);
              $("#usersEmail").attr("value",response.data.usersEmail);
            }
        }
    });

    $(document).ready(function() {
        $('#form1').bootstrapValidator({
            live: 'disabled',//验证时机，enabled是内容有变化就验证（默认），disabled和submitted是提交再验证
            excluded: [':disabled', ':hidden', ':not(:visible)'],//排除无需验证的控件，比如被禁用的或者被隐藏的
            submitButtons: '#btn-save',//指定提交按钮，如果验证失败则变成disabled，但我没试成功，反而加了这句话非submit按钮也会提交到action指定页面

            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                'adminName': {
                    message: '用户名验证失败',
                    validators: {
                        notEmpty: {
                            message: '用户名不能为空'
                        },
                        stringLength: {
                            min: 2,
                            message: '用户名长度必须大于2个字符'
                        }
                    }
                },
                'adminTelphone': {
                    validators: {
                        notEmpty: {
                            message: '手机号不能为空'
                        },
                        regexp: {
                            regexp: /^1\d{10}$/,
                            message: '手机号格式错误'
                        }
                    }
                },
                'adminEmail': {
                    validators: {
                        notEmpty: {
                            message: '邮箱不能为空'
                        },
                        emailAddress: {
                            message: '邮箱地址格式有误'
                        }
                    }
                }
            }
        });
    });
    function save() {
        $("#form1").bootstrapValidator('validate');
        if(!$('#form1').data('bootstrapValidator').isValid()){
          return;
        }
        $.ajax({
            url: '${ctx}/sys/user/updateUserData',
            type: 'post',
            data:$('#form1').serialize(),
            dataType: 'json',
            success: function (response) {
                if (response.code == 0) {
                    window.parent.layer.msg(response.msg, {icon: 1, time: 1000, offset: 't'})
                    window.location.href = '${ctx}/sys/user/data'
                }else {
                    layer.alert(response.msg, {icon: 5, offset: 't'});
                }
            }
        });
    }
    //重置
    $("#btn-reset").click(function () {
        $('#form1')[0].reset();
        window.location.reload();
    });

    window.onload=function(){
        parent.scrollTo(0,0)
    }
</script>
</body>
</html>
