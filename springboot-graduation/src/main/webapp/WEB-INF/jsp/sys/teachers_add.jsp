<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>毕设选题系统 - 新增教师</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${ctx}/static/lib/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${ctx}/static/lib/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/static/lib/datetimepicker/datetimepicker.css" rel="stylesheet">
    <link href="${ctx}/static/css/custom.css" rel="stylesheet">
</head>
<body class="content_col">
<div>
    <div class="x_panel">
        <div class="x_title">
            <h2>新增教师</h2>
            <div class="clear"></div>
        </div>
        <div class="x_content">
            <form class="form-horizontal" id="data-form" onsubmit="return false" data-parsley-validate>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        工号 <span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <%--只能输入至少6位数字--%>
                        <input type="text" name="teaId" class="form-control"  required minlength="6" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        初始密码 <span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <%--只能输入至少6位数字--%>
                        <input type="text" name="password" id="password" class="form-control" value="123456" required minlength="6" onkeyup="this.value=this.value.replace(/\D/g,'')">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        姓名<span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <input type="text" name="teaName" class="form-control"
                               required minlength="2"  onkeyup="this.value=this.value.replace(/[\d]/g,'')" >
                    </div>
                </div>
                <div class="form-group"  >
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        学院(部)<span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <select class="form-control" name="teaCollId" >
                                <c:forEach var="coll" items="${teaCollege}">
                                    <option value="${coll.collId}">
                                            ${coll.collName}
                                    </option>
                                </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group"  >
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        职称<span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <select class="form-control" name="teaEducation">
                            <option value="助教">助教</option>
                            <option value="讲师">讲师</option>
                            <option value="副教授">副教授</option>
                            <option value="教授">教授</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        账号状态 <span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <select class="form-control" name="status">
                            <option value="0">正常</option>
                            <option value="1">禁用</option>
                            <option value="2">锁定</option>
                        </select>
                    </div>
                </div>
                <div class="ln_solid"></div>
                <div class="form-group " >
                    <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3" style="margin-left: 500px">
                        <button class="btn btn-primary btn-sm" type="button" onclick="window.history.go(-1);">返回</button>
                        <button class="btn btn-primary btn-sm" type="reset">重置</button>
                        <button type="submit" class="btn btn-success btn-sm">保存</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="${ctx}/static/lib/jquery/jquery.js"></script>
<script src="${ctx}/static/lib/bootstrap/js/bootstrap.js"></script>
<script src="${ctx}/static/lib/layer/layer.js"></script>
<script src="${ctx}/static/lib/parsleyjs/parsley.js"></script>
<script src="${ctx}/static/lib/parsleyjs/zh_cn.js"></script>
<script src="${ctx}/static/lib/nprogress/nprogress.js"></script>
<script src="${ctx}/static/js/custom.js"></script>

<script>
    $('#data-form').parsley().on('form:submit', function () {
        $.ajax({
            url: '${ctx}/sys/admin/addTeacher',
            type: 'post',
            data: $("#data-form").serialize(),
            dataType: 'json',
            success: function (response) {
                if (response.code == 0) {
                    window.parent.layer.msg(response.msg, {icon: 1, time: 500, offset: '0px'});
                    window.location.href = '${ctx}/sys/admin/teacher';
                } else {
                    window.parent.layer.alert(response.msg, {icon: 5, offset: '0px'});
                }
            }
        })
    });
</script>
</body>
</html>

