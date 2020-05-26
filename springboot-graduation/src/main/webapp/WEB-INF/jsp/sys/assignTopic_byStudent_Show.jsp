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
    <title>毕设选题系统 - 学生查看课题信息详情</title>
    <link href="${ctx}/static/lib/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${ctx}/static/lib/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/static/css/custom.css" rel="stylesheet">
    <link href="${ctx}/static/lib/wangEditor/css/wangEditor.min.css" rel="stylesheet">
</head>
<body class="content_col">
<div >
    <div class="x_panel">
        <div class="x_title">
            <h2>课题详情</h2>
            <div class="clear"></div>
        </div>
        <div class="x_content">
            <form class="form-horizontal" id="data-form" onsubmit="return false" data-parsley-validate>
                <input type="hidden" value="${topicMessage.tNo}" name="tNo">
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        课题名称<span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12" >
                        <input type="text" name="tTitle" class="form-control" value="${topicMessage.tTitle}" disabled="disabled">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        所属组织<span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12" >
                        <select class="form-control" id="tEduid" name="tEduid" symbol="${topicMessage.tEduid}" disabled="disabled">
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

                <div class="form-group"  >
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        指导导师<span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <select class="form-control" id="tMaketeacher" name="tMaketeacher" symbol="${topicMessage.tMaketeacher}" disabled="disabled">
                            <c:forEach var="teadata" items="${teaData}">
                                <option value="${teadata.teaId}">
                                        ${teadata.teaName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-group" >
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        课题说明<span class="required">*</span>
                    </label>
                    <textarea id="tIntro" name="tIntroduce" hidden="hidden" ></textarea>
                    <span id="s1" hidden="hidden">${topicMessage.tIntroduce}</span>
                </div>
                <div id="tIntroduce" style="width: 70%; margin-left: 245px;"></div>

                <div class="form-group" style="margin-top: 5px">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        课题任务和要求<span class="required">*</span>
                    </label>
                    <textarea id="tTaskr" name="tTaskrequest" hidden="hidden"></textarea>
                    <span id="s2" hidden="hidden">${topicMessage.tTaskrequest}</span>
                </div>
                <div id="tTaskrequest" style="width: 70%; margin-left: 245px;"></div>

                <div class="form-group"style="margin-top: 30px" >
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        课题类型<span class="required">*</span>
                    </label>
                    <div id="tType" class="col-md-6 col-sm-6 col-xs-12" style="width: 600px;background-color: #EAEDF2;margin-left: 10px" symbol="${topicMessage.tType}">
                        <label class="checkbox-inline ">
                            <input disabled="disabled" type="radio" name="tType"  value="法学类" checked="checked">&nbsp<span style="font-size: 16px" >法学类</span>
                        </label>
                        <label class="checkbox-inline">
                            <input disabled="disabled" type="radio" name="tType"  value="综合类">&nbsp<span style="font-size: 16px">综合类</span>
                        </label>
                        <label class="checkbox-inline">
                            <input  disabled="disabled" type="radio" name="tType"  value="经管文类">&nbsp<span style="font-size: 16px">经管文类</span>
                        </label>
                        <label class="checkbox-inline">
                            <input disabled="disabled" type="radio" name="tType"  value="艺术设计类">&nbsp<span style="font-size: 16px">艺术设计类</span>
                        </label>
                        <label class="checkbox-inline">
                            <input disabled="disabled" type="radio" name="tType"  value="理论研究类">&nbsp<span style="font-size: 16px">理论研究类</span>
                        </label>
                        <label class="checkbox-inline">
                            <input disabled="disabled" type="radio" name="tType"  value="实验研究类">&nbsp<span style="font-size: 16px">实验研究类</span>
                        </label>
                        <label class="checkbox-inline">
                            <input disabled="disabled"  type="radio" name="tType"  value="工程设计类">&nbsp<span style="font-size: 16px">工程设计类</span>
                        </label>
                        <label class="checkbox-inline">
                            <input disabled="disabled"  type="radio" name="tType"  value="计算机软件研制类">&nbsp<span style="font-size: 16px">计算机软件研制类</span>
                        </label>
                    </div>
                </div>

                <div class="form-group"  >
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        课题来源<span class="required">*</span>
                    </label>
                    <div id="tSource" class="col-md-6 col-sm-6 col-xs-12" style="background-color: #EAEDF2;margin-left: 10px" symbol="${topicMessage.tSource}">
                        <label class="checkbox-inline">
                            <input disabled="disabled" checked="checked" type="radio" name="tSource"  value="教师自拟">&nbsp<span style="font-size: 16px">教师自拟</span>
                        </label>
                        <label class="checkbox-inline">
                            <input disabled="disabled" type="radio" name="tSource"  value="学生自拟">&nbsp<span style="font-size: 16px">学生自拟</span>
                        </label>
                    </div>
                </div>

                <div class="form-group" style="margin-top: 30px" >
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        难度指数<span class="required">*</span>
                    </label>
                    <div id="tDifferent" class="col-md-6 col-sm-6 col-xs-12" symbol="${topicMessage.tDifferent}">
                        <div id="star" style="margin-top: 5px;"></div>
                        <div>
                            <input type="text" hidden="hidden" class="result" name="tDifferent" >
                            <%--<span class="result" hidden="hidden"></span>--%>
                        </div>
                    </div>
                </div>

                <div class="ln_solid"></div>
                <div class="form-group">
                    <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3" style="margin-left: 500px">
                        <button class="btn btn-primary btn-sm" type="button" onclick="window.history.go(-1);">返回</button>
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
<script src="${ctx}/static/lib/wangEditor/js/wangEditor.min.js"></script>
<script src="${ctx}/static/lib/nprogress/nprogress.js"></script>
<script src="${ctx}/static/js/custom.js"></script>
<script src="${ctx}/static/lib/jquery-star-rating/lib/jquery.raty.js"></script>

<script>
    $('#data-form').parsley().on('form:submit', function () {
        $.ajax({
            url: '',
            type: 'post',
            data: $("#data-form").serialize(),
            dataType: 'json',
            success: function (response) {
                if (response.code == 0) {
                    window.parent.layer.msg(response.msg, {icon: 1, time: 1000, offset: '0px'});
                    window.location.href = '${ctx}/sys/teacher/assignTopic';
                } else {
                    window.parent.layer.alert(response.msg, {icon: 5, offset: '0px'});
                }
            }
        })
    });
    //更新默认选择课题所属组织
    var education = $("#tEduid").attr("symbol");
    $("#tEduid").find("option").each(function () {
        if(($(this).attr("value"))==education) {
            $(this).attr("selected","selected")
        }
    })

    //富文本1操作
    var E1 = window.wangEditor;
    var editor1 = new E1('#tIntroduce');
    //取富文本1的值
    var $text1 = $('#tIntro');
    editor1.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        $text1.val(html)
    };
    editor1.create();
    //赋初始值
    editor1.txt.html($("#s1").html());
    editor1.$textElem.attr('contenteditable', false);

    //富文本2操作
    var E2 = window.wangEditor;
    var editor2 = new E2('#tTaskrequest');
    //取富文本2的值
    var $text2 = $('#tTaskr');
    editor2.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        $text2.val(html)
    };
    editor2.create();
    //赋初始值
    editor2.txt.html($("#s2").html());
    editor2.$textElem.attr('contenteditable', false);

    //设置课题类型为当前课题默认值
    var topicType =$("#tType").attr("symbol");
    $("#tType").find("input").each(function () {
        if($(this).attr("value")==topicType){
            $(this).attr("checked","checked")
        }
    })
    //设置课题来源为当前课题默认值
    var topicSource =$("#tSource").attr("symbol");
    $("#tSource").find("input").each(function () {
        if($(this).attr("value")==topicSource){
            $(this).attr("checked","checked")
        }
    })
    //设置课题导师为当前课题默认值
    var topicMakeTeacher =$("#tMaketeacher").attr("symbol");
    $("#tMaketeacher").find("option").each(function () {
        if($(this).attr("value")==topicMakeTeacher){
            $(this).attr("selected","selected")
        }
    });
    //设置课题难度为当前课题难度
    $(document).ready(function () {
        $("#star").raty({
            hints:['1','2','3','4','5'],
            path:"${ctx}/static/lib/jquery-star-rating/lib/img",
            starOff:'star-off.png',
            starOn:'star-on.png',
            score: $("#tDifferent").attr("symbol"),
            number:5,
            readOnly:true,
            size:24,
            target:'.result',
            targetKeep:true,
            click:function (score,evt) {

            }
        })
    });

</script>
</body>
</html>
