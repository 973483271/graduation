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
        <div class="x_title">
            <h2>发布通知</h2>
            <div class="clear"></div>
        </div>
        <div class="x_content" style="width: 110%" >
            <form class="form-horizontal" id="data-form" onsubmit="return false" data-parsley-validate  enctype="multipart/form-data"  method="post">
                <div class="form-group" >
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <input id="filePath" name="filePath" style="display:none;">
                    </div>
                </div>
                <div class="form-group" >
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        接收方<span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12" style="display: inline;width: 263px">
                        学院(部)
                        <select class="form-control" name="receivecollId" >、
                            <option value="${collData.collId}">${collData.collName}</option>
                        </select>
                    </div>
                    <div class="col-md-6 col-sm-6 col-xs-12" style="display: inline;width: 263px">
                        角色<select class="form-control" name="receiverollId" >、
                        <option value="2">学生</option>
                    </select>
                    </div>
                </div>
                <div class="form-group" >
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        标题<span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12" >
                        <input type="text" name="titleName" class="form-control" required>
                    </div>
                </div>
                <div class="form-group" >
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        正文<span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12" >
                        <textarea rows="15" cols="30" name="textName" maxlength="2000" style="width: 100%;font-size: 14px" required placeholder="内容不超过2000字" onKeyUp="textarealength(this,500)"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-md-3 col-sm-3 col-xs-12">
                        上传附件<span class="required">*</span>
                    </label>
                    <div class="col-md-6 col-sm-6 col-xs-12">
                        <div class="form-group">
                            附件1：
                            <p>
                               <input id="fi1" type="file" name="file" style="display: inline" accept=".xls,.xlsx,.doc,.docx,.ppt,.pptx"/> <a id="del1" href="###" onclick="removeFile1()" hidden="none">删除</a>
                            </p>
                            附件2：
                            <p>
                                <input id="fi2" type="file" name="file" style="display: inline" accept=".xls,.xlsx,.doc,.docx,.ppt,.pptx"/> <a id="del2" href="#" onclick="removeFile2()" hidden="none">删除</a>
                            </p>
                        </div>
                    </div>
                </div>
                <div class="ln_solid"></div>
                <div class="form-group " >
                    <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3" style="margin-left: 600px">
                        <button class="btn btn-primary btn-sm" type="button" onclick="window.history.go(-1);">返回</button>
                        <button class="btn btn-primary btn-sm" type="reset" onclick="resetData()">重置</button>
                        <button type="submit" class="btn btn-success btn-sm">发布</button>
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
        var formData = new FormData($("#data-form")[0]);
        $.ajax({
            url: '${ctx}/sys/teacher/deliverNotice',
            type: 'post',
            processData: false,  //必须false才会避开jQuery对 formdata 的默认处理
            contentType: false,  //必须false才会自动加上正确的Content-Type
            data: formData,
            dataType: 'json',
            success: function (response) {
                if (response.code == 0) {
                    window.parent.layer.msg(response.msg, {icon: 1, time: 500, offset: '0px'});
                    window.location.href = '${ctx}/sys/teacher/deliverNotice';
                } else {
                    window.parent.layer.alert(response.msg, {icon: 5, offset: '0px'});
                }
            }
        })
    });
    //删除文件 1
    function removeFile1() {
        var obj = document.getElementById('fi1') ;
        obj.outerHTML=obj.outerHTML;
        $("#del1").hide();
    }
    //删除文件 2
    function removeFile2() {
        var obj = document.getElementById('fi2') ;
        obj.outerHTML=obj.outerHTML;
        $("#del2").hide();
    }
    $(document).off('change','#fi1').on('change','#fi1',function(){
        $("#del1").show();
    });
    $(document).off('change','#fi2').on('change','#fi2',function(){
        $("#del2").show();
    });

    //重置
    function resetData() {
        $("#del1").hide();
        $("#del2").hide();
    }

    window.onload=function(){
        parent.scrollTo(0,0)
    }
</script>
</body>
</html>
