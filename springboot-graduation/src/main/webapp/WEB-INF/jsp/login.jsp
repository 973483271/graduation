<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>毕业论文(设计)选题系统 - 系统登录</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="${ctx}/static/lib/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${ctx}/static/lib/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/static/lib/nprogress/nprogress.css" rel="stylesheet">
    <link href="${ctx}/static/lib/animate/animate.css" rel="stylesheet">
    <link href="${ctx}/static/css/custom.min.css" rel="stylesheet">
</head>

<body class="login" style="background-color:#FFFFFF">
<div>

</div>
<div class="login_wrapper" >
    <div class="animate form login_form" style="position: relative;">
        <section class="login_content" style="">
            <form id="data-form" onsubmit="return false" data-parsley-validate>
                <h1>系统登录</h1>
                <div style="width: 350px">
                    <input type="text"   class="form-control " placeholder="用户名" name="username" data-parsley-required="true"  data-parsley-required-message="用户名不可为空" />
                </div>
                <div >
                    <input type="password" class="form-control" placeholder="密码" name="password" data-parsley-required="true"  data-parsley-required-message="密码不可为空"/>
                </div>
                <div >
                    <span><a class="btn btn-default submit" href="javascript:;" style="width:350px;background-color: #3789FF;color: #FFFFFF;text-shadow: none;font-size: 15px">登录</a></span>
                </div>
                <div class="clearfix"></div>
                <div class="separator">
                    <div class="clearfix"></div>
                    <br/>

                    <div>
                        <h1><i class="fa fa-graduation-cap"></i> 毕业设计选题系统</h1>
                        <p>©2020 Graduation design topics.</p>
                    </div>
                </div>
            </form>
        </section>
    </div>
</div>
<script src="${ctx}/static/lib/jquery/jquery.js"></script>
<script src="${ctx}/static/lib/bootstrap/js/bootstrap.js"></script>
<script src="${ctx}/static/lib/layer/layer.js"></script>
<!--表单校验-->
<script src="${ctx}/static/lib/parsleyjs/parsley.js"></script>
<script src="${ctx}/static/lib/parsleyjs/zh_cn.js"></script>
<script>
    $('.submit').bind('click',function () {
        $('#data-form').submit();
    });

    $('#data-form').parsley().on('form:submit', function () {
        $.ajax({
            url: '${ctx}/login',
            type: 'post',
            data: $("#data-form").serialize(),
            dataType: 'json',
            success: function (response) {
                console.log(response)
                if (response.code == 0) {
                    layer.msg(response.msg, {icon: 1, time: 1000, offset: '0px'});
                    window.location.href = '${ctx}/index';
                } else {
                    layer.alert(response.msg, {icon: 5, offset: '0px'});
                }
            }
        });
    });
</script>
</body>
</html>
