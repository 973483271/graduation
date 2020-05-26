<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>毕设选题系统 - 修改用户密码</title>
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
        <h3>密码管理</h3>
    </div>
</div>
<div class="x_panel" >
    <div class="row x_title">
        <h2>修改密码</h2>
    </div>
    <div class="x_content" style="margin-left: 150px">
    <form id="form_password" >
        <div class="form-group" style="width: 600px">
            <label>*原始密码:</label><input type="password" name="oldPassword" class="form-control" placeholder="请输入原始密码" />
        </div>
        <div class="form-group" style="width: 600px">
            <label>*新密码:</label><input type="password" name="newPassword" class="form-control" placeholder="请输入用户新密码" />
        </div>
        <div class="form-group" style="width: 600px">
            <label>*确认密码:</label><input type="password" name="comfirmPassword" class="form-control" placeholder="请输入用户确认密码" style="width: 600px"/>
        </div>
        <div class="btn-toolbar" style="width: 60%;float: right">
            <div class="btn-group" >
                <button type="button" class="btn btn-default btn-sm" id="btn-updatePass" onclick="updatePass()">保存</button>
            </div>

            <div class="btn-group" >
                <button type="button" class="btn btn-default btn-sm" id="btn-resetPass" onclick="resetPass()">重置</button>
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
    $(document).ready(function() {
        $('#form_password').bootstrapValidator({
            live: 'disabled',//验证时机，enabled是内容有变化就验证（默认），disabled和submitted是提交再验证
            excluded: [':disabled', ':hidden', ':not(:visible)'],//排除无需验证的控件，比如被禁用的或者被隐藏的
            submitButtons: '#btn-updatePass',//指定提交按钮，如果验证失败则变成disabled，但我没试成功，反而加了这句话非submit按钮也会提交到action指定页面

            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                'oldPassword': {
                    validators: {
                        notEmpty: {
                            message: '原始密码不能为空'
                        },
                        regexp: {
                            regexp: /^[^ ]+$/,
                            message: '原始密码不能有空格'
                        }

                    }
                },
                'newPassword': {
                    validators: {
                        notEmpty: {
                            message: '用户新密码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 19,
                            message: '用户新密码长度大于5小于20'
                        },
                        regexp: {
                            regexp: /^[^ ]+$/,
                            message: '用户新密码不能有空格'
                        }

                    }
                },
                'comfirmPassword': {
                    validators: {
                        identical: {
                            field: 'newPassword',
                            message: '用户新密码与确认密码不一致！'
                        },
                        notEmpty: {
                            message: '用户确认密码不能为空'
                        },
                        regexp: {
                            regexp: /^[^ ]+$/,
                            message: '用户确认密码不能有空格'
                        }
                    }
                }
            }
        });
    });

//    修改密码
    function updatePass() {
        $("#form_password").bootstrapValidator('validate');
        if(!$('#form_password').data('bootstrapValidator').isValid()){
            return;
        }
        $.ajax({
            url: '${ctx}/sys/user/updateUserPassword',
            type: 'post',
            data:$('#form_password').serialize(),
            dataType: 'json',
            success: function (response) {
                if (response.code == 0) {
                    window.parent.layer.msg(response.msg, {icon: 1, time: 3000, offset: 't'})
                    top.location.href= '${ctx}/login'
                }else {
                    layer.alert(response.msg, {icon: 5, offset: 't'});
                }
            }
        });
    }
    //重置密码
    function resetPass() {
        $("input").val('');
        window.location.reload();
    }

    window.onload=function(){
        parent.scrollTo(0,0)
    }
</script>
</body>
</html>
