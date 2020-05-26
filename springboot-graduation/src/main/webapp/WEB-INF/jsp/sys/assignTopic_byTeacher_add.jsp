<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>毕设选题系统 - 指导老师出题页面</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${ctx}/static/lib/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${ctx}/static/lib/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/static/lib/datetimepicker/datetimepicker.css" rel="stylesheet">
    <link href="${ctx}/static/css/custom.css" rel="stylesheet">
    <link href="${ctx}/static/lib/wangEditor/css/wangEditor.min.css" rel="stylesheet">

</head>
<body class="content_col">
<div>
    <div class="x_panel">
        <div class="x_title">
            <h2>教师出题</h2>
            <div class="clear"></div>
        </div>
        <div class="x_content" >
            <form class="form-horizontal" id="data-form" onsubmit="return false" data-parsley-validate >
                <div class="form-group" >
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        课题名称<span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12" >
                        <%--输入中文、数字、英文--%>
                        <input type="text" name="tTitle" class="form-control" >
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        所属组织<span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12" >
                        <select class="form-control" name="tEduid">
                            <c:forEach var="coll" items="${eduColl}">
                                <optgroup label="${coll.collName}">
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

                <div class="form-group" >
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        课题说明<span class="required">*</span>
                    </label>
                    <textarea id="tIntro" name="tIntroduce" hidden="hidden"></textarea>
                </div>
                <div id="tIntroduce" style="width: 70%; margin-left: 245px;"></div>


                <div class="form-group" style="margin-top: 5px">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        课题任务和要求<span class="required">*</span>
                    </label>
                    <textarea id="tTaskr" name="tTaskrequest" hidden="hidden"></textarea>
                </div>
                <div id="tTaskrequest" style="width: 70%; margin-left: 245px;"></div>

                <div class="form-group"style="margin-top: 30px" >
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        课题类型<span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12" style="width: 600px;background-color: #EAEDF2;margin-left: 10px">
                        <label class="checkbox-inline ">
                            <input type="radio" name="tType"  value="法学类" checked="checked">&nbsp<span style="font-size: 16px">法学类</span>
                        </label>
                        <label class="checkbox-inline">
                            <input type="radio" name="tType"  value="综合类">&nbsp<span style="font-size: 16px">综合类</span>
                        </label>
                        <label class="checkbox-inline">
                            <input type="radio" name="tType"  value="经管文类">&nbsp<span style="font-size: 16px">经管文类</span>
                        </label>
                        <label class="checkbox-inline">
                            <input type="radio" name="tType"  value="艺术设计类">&nbsp<span style="font-size: 16px">艺术设计类</span>
                        </label>
                        <label class="checkbox-inline">
                            <input type="radio" name="tType"  value="理论研究类">&nbsp<span style="font-size: 16px">理论研究类</span>
                        </label>
                        <label class="checkbox-inline">
                            <input type="radio" name="tType"  value="实验研究类">&nbsp<span style="font-size: 16px">实验研究类</span>
                        </label>
                        <label class="checkbox-inline">
                            <input type="radio" name="tType"  value="工程设计类">&nbsp<span style="font-size: 16px">工程设计类</span>
                        </label>
                        <label class="checkbox-inline">
                            <input type="radio" name="tType"  value="计算机软件研制类">&nbsp<span style="font-size: 16px">计算机软件研制类</span>
                        </label>
                    </div>
                </div>
                <div class="form-group"  >
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        课题来源<span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12" style="background-color: #EAEDF2;margin-left: 10px">
                        <input hidden="hidden" name="tSource" value="教师自拟"/>
                        <label class="checkbox-inline">
                            <input disabled="disabled" checked="checked" type="radio" name="tSource"  value="教师自拟">&nbsp<span style="font-size: 16px" >教师自拟</span>
                        </label>
                        <label class="checkbox-inline">
                            <input  disabled="disabled" type="radio" name="tSource"  value="学生自拟">&nbsp<span style="font-size: 16px">学生自拟</span>
                        </label>
                    </div>
                </div>
                <div class="form-group" style="margin-top: 30px" >
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        难度指数<span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12" >
                        <div id="star" style="margin-top: 5px;"></div>
                        <div>
                            <input type="text" hidden="hidden" class="result" name="tDifferent" >
                            <%--<span class="result" hidden="hidden"></span>--%>
                        </div>
                    </div>
                </div>
                <div class="ln_solid"></div>
                <div class="form-group" style="margin-top: 30px">
                    <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3 " style="margin-left: 600px">
                        <button class="btn btn-primary btn-sm" type="button" onclick="window.history.go(-1);">返回</button>
                        <button class="btn btn-primary btn-sm" type="reset">重置</button>
                        <button type="submit" class="btn btn-success btn-sm">提交</button>
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
<script src="${ctx}/static/lib/wangEditor/js/wangEditor.min.js"></script>
<script src="${ctx}/static/lib/jquery-star-rating/lib/jquery.raty.js"></script>

<script>
    $('#data-form').parsley().on('form:submit', function () {
        $.ajax({
            url: '${ctx}/sys/teacher/assignTopicAdd',
            type: 'post',
            data: $("#data-form").serialize(),
            dataType: 'json',
            success: function (response) {
                if (response.code == 0) {
                    window.parent.layer.msg(response.msg, {icon: 1, time: 500, offset: '0px'});
                    window.location.href = '${ctx}/sys/teacher/assignTopic';
                } else {
                    window.parent.layer.alert(response.msg, {icon: 5, offset: '0px'});
                    window.location.href = '${ctx}/sys/teacher/assignTopic';
                }
            }
        })
    }
    );
    //星星评分
    $(document).ready(function () {
        $("#star").raty({
            hints:['1','2','3','4','5'],
            path:"${ctx}/static/lib/jquery-star-rating/lib/img",
            starOff:'star-off.png',
            starOn:'star-on.png',
            score:1,
            number:5,
            readOnly:false,
            size:24,
            target:'.result',
            targetKeep:true,
            click:function (score,evt) {

            }
        })
    });
    //创建富文本1
    var E = window.wangEditor;
    var editor1 = new E('#tIntroduce');
    //创建富文本2
    var editor2 = new E('#tTaskrequest');

    //取富文本1的值
    var $text1 = $('#tIntro');
    editor1.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        $text1.val(html)
    };
    editor1.create();
    //取富文本2的值
    var $text2 = $('#tTaskr');
    editor2.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        $text2.val(html)
    };
    editor2.create();

</script>
</body>

</html>
