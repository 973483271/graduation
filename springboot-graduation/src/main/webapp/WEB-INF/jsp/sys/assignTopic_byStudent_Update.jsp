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
    <title>毕设选题系统 - 学生更新课题信息</title>
    <link href="${ctx}/static/lib/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${ctx}/static/lib/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/static/css/custom.css" rel="stylesheet">
    <link href="${ctx}/static/lib/wangEditor/css/wangEditor.min.css" rel="stylesheet">
</head>
<body class="content_col">
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
    <div class="modal-dialog" style="z-index:10001"  >
        <div class="modal-content"  >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">指导老师信息</h4>
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

<div>
    <div class="x_panel">
        <div class="x_title">
            <h2>修改课题信息</h2>
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
                        <input type="text" name="tTitle" class="form-control" value="${topicMessage.tTitle}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        所属组织<span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12" >
                        <input hidden="hidden" name="tEduid" value="${topicMessage.tEduid}"/>
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
                        意向导师<span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <select class="form-control" id="tMaketeacher" name="tMaketeacher" symbol="${topicMessage.tMaketeacher}" >
                            <c:forEach var="teadata" items="${teaData}">
                                <option value="${teadata.teaId}">
                                        ${teadata.teaName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <label><a href="##" class="btn btn-info" onclick="getTeacherData()">导师详情</a></label>
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
                            <input  type="radio" name="tType"  value="工程设计类">&nbsp<span style="font-size: 16px">工程设计类</span>
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
                    <div id="tSource" class="col-md-6 col-sm-6 col-xs-12" style="background-color: #EAEDF2;margin-left: 10px" symbol="${topicMessage.tSource}">
                        <input hidden="hidden" name="tSource" value="学生自拟"/>
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
                        </div>
                    </div>
                </div>

                <div class="ln_solid"></div>
                <div class="form-group">
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
            url: '${ctx}/sys/student/assignTopicByStudentUpdate',
            type: 'post',
            data: $("#data-form").serialize(),
            dataType: 'json',
            success: function (response) {
                if (response.code == 0) {
                    window.parent.layer.msg(response.msg, {icon: 1, time: 1000, offset: '0px'});
                    window.location.href = '${ctx}/sys/student/assignTopic';
                } else {
                    window.parent.layer.alert(response.msg, {icon: 5, offset: '0px'});
                    window.location.href = '${ctx}/sys/student/assignTopic';
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
    });

    //查看导师详情
    function getTeacherData() {
        $.ajax({
            url: '${ctx}/sys/student/getAssignTopicTeacherData/'+$("#tMaketeacher option:selected").val(),
            type: 'get',
            success: function (response) {
                if (response.code == 0) {
                    $("#id").text(response.teaData.teaId);
                    $("#name").text(response.teaData.teaName);
                    $("#education").text(response.teaData.teaEducation);
                    $("#telphone").text(response.teaData.teaTelphone);
                    $("#email").text(response.teaData.teaEmail);
                    $("#college").text(response.teaData.teaCollege);
                    $("#myModal").modal()

                } else {
                    window.parent.layer.alert(response.msg, {icon: 5, offset: 't'});
                }
            }
        });
    }

    //富文本1操作
    var E1 = window.wangEditor;
    var editor1 = new E1('#tIntroduce');
    //取textarea1对象
    var $text1 = $('#tIntro');
    //给textarea1赋初始值
    $text1.val($("#s1").html());
    editor1.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        $text1.val(html)
    };
    editor1.customConfig.zIndex = 100;
    editor1.create();
    //给富文本1 赋初始值用于显示
    editor1.txt.html($("#s1").html());



    //富文本2操作
    var E2 = window.wangEditor;
    var editor2 = new E2('#tTaskrequest');
    //取textarea2的对象
    var $text2 = $('#tTaskr');
    //给textarea2赋初始值
    $text2.val($("#s2").html());
    editor2.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        $text2.val(html)
    };
    editor2.customConfig.zIndex = 100;
    editor2.create();
    //赋初始值
    editor2.txt.html($("#s2").html());


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
    });
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
            readOnly:false,
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
