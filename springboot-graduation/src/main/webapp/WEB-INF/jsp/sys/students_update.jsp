<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>毕设选题系统 - 更新学生信息</title>
    <link href="${ctx}/static/lib/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${ctx}/static/lib/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/static/css/custom.css" rel="stylesheet">
</head>
<body class="content_col">
<div>
    <div class="x_panel">
        <div class="x_title">
            <h2>修改学生信息</h2>
            <div class="clear"></div>
        </div>
        <div class="x_content">
            <form class="form-horizontal" id="data-form" onsubmit="return false" data-parsley-validate>
                <input type="hidden" value="${stuUser.stuId}" name="stuId">
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        工号 <span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <input type="text" name="stuId" class="form-control" value="${stuUser.stuId}" disabled="disabled">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        姓名 <span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <input type="text" name="stuName" class="form-control" value="${stuUser.stuName}"
                               required minlength="2"  onkeyup="this.value=this.value.replace(/[\d]/g,'')" >
                    </div>
                </div>

                 <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-12">
                            专业 <span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12">
                            <select class="form-control" id="stuEducation" name="stuEducationId" symbol="${stuUser.stuEducationId}">
                                <c:forEach var="coll" items="${eduColl}">
                                    <optgroup label="${coll.collName}" >
                                        <c:forEach var="edu" items="${coll.educations}">
                                            <option value="${edu.eduId}">
                                                    ${edu.eduName}
                                            </option>
                                        </c:forEach>
                                    </optgroup>
                                </c:forEach>
                            </select>
                        </div>
                 </div>

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        班级 <span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <select class="form-control" id="stuclass"  name="stuClass" symbol="${stuUser.stuClass}">
                            <c:forEach var="stucla" items="${stuClass}">
                                <option value="${stucla.stuClass}">
                                        ${stucla.className}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-group" >
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        手机号 <span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <input type="tel" class="form-control" id="stuTelphone" value="${stuUser.stuTelphone}" name="stuTelphone">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        邮箱 <span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <input type="email" name="stuEmail" value="${stuUser.stuEmail}" class="form-control col-md-7 col-xs-12">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        账号状态 <span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <select class="form-control" name="status">
                            <c:if test="${stuUser.status==0}">
                                <option value="0" selected="selected">正常</option>
                                <option value="1">禁用</option>
                                <option value="2">锁定</option>
                            </c:if>
                            <c:if test="${stuUser.status==1}">
                                <option value="0" >正常</option>
                                <option value="1" selected="selected">禁用</option>
                                <option value="2">锁定</option>
                            </c:if>
                            <c:if test="${stuUser.status==2}">
                                <option value="0" selected="selected">正常</option>
                                <option value="1">禁用</option>
                                <option value="2" selected="selected">锁定</option>
                            </c:if>
                        </select>
                    </div>
                </div>

                <div class="ln_solid"></div>
                <div class="form-group">
                    <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3" style="margin-left: 600px">
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

<!--表单校验-->
<script src="${ctx}/static/lib/parsleyjs/parsley.js"></script>
<script src="${ctx}/static/lib/parsleyjs/zh_cn.js"></script>

<script src="${ctx}/static/lib/nprogress/nprogress.js"></script>
<script src="${ctx}/static/js/custom.js"></script>

<script>
    $('#data-form').parsley().on('form:submit', function () {
        $.ajax({
            url: '${ctx}/sys/admin/studentsUpdate',
            type: 'post',
            data: $("#data-form").serialize(),
            dataType: 'json',
            success: function (response) {
                if (response.code == 0) {
                    window.parent.layer.msg(response.msg, {icon: 1, time: 1000, offset: '0px'});
                    window.location.href = '${ctx}/sys/admin/student';
                } else {
                    window.parent.layer.alert(response.msg, {icon: 5, offset: '0px'});
                }
            }
        })
    });
    //更新默认选择学生的专业
    var coll = $("#stuEducation").attr("symbol");
    $("#stuEducation").find("option").each(function () {
        if(($(this).attr("value"))==coll) {
            $(this).attr("selected","selected")
        }
    })
    //更新默认选择学生的班级
    var stuclass = $("#stuclass").attr("symbol");
    $("#stuclass").find("option").each(function () {
        if(($(this).attr("value"))==stuclass) {
            $(this).attr("selected","selected")
        }
    })

</script>
</body>
</html>

