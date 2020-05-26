<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>毕设选题系统 - 指导老师发布通知管理页面</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${ctx}/static/lib/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${ctx}/static/lib/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/static/lib/bootstrap-table/bootstrap-table.css" rel="stylesheet">
    <link href="${ctx}/static/css/custom.css" rel="stylesheet">
    <style>
        td{
            white-space:nowrap;
            overflow:hidden;
            text-overflow:ellipsis;
        }
    </style>
</head>
<body class="content_col">
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 750px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">公告信息</h4>
            </div>
            <div class="modal-body" >
                <form class="form-horizontal" id="data-form" onsubmit="return false" data-parsley-validate  >
                    <div class="form-group"   >
                        <label class="control-label col-md-2 col-sm-2 col-xs-15">
                            标题<span class="required" >*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12" >
                            <input id="titleName" type="text" name="titleName" class="form-control" required style="width: 500px" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group" >
                        <label class="control-label col-md-2 col-sm-2 col-xs-15">
                            正文<span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12" >
                            <textarea id="textName" rows="15" cols="30" name="textName" maxlength="1000" style="width: 500px;font-size: 14px" onKeyUp="textarealength(this,500)" readonly="readonly"></textarea>
                        </div>
                    </div>
                    <div class="form-group" >
                        <label class="control-label col-md-2 col-sm-2 col-xs-15">
                            附件<span class="required">*</span>
                        </label>
                        <div class="col-md-6 col-sm-6 col-xs-12" id="pathName" style="width: 500px;">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<div class="page-title">
    <div class="title_left" style="float: left" >
        <h3>通知管理</h3>
    </div>
</div>
<div class="x_panel" >
    <div class="x_title">
        <h2>发布通知列表</h2>
        <div class="clear"></div>
    </div>
    <div class="x_content" >
        <div id="toolbar">
            <a href="${ctx}/sys/teacher/deliverNoticeAdd" class="btn btn-success btn-sm"><i class="fa fa-paper-plane"></i> 发布通知</a>
        </div>
        <table id="data-table"></table>
    </div>

</div>

</div>

<script src="${ctx}/static/lib/jquery/jquery.js"></script>
<script src="${ctx}/static/lib/bootstrap/js/bootstrap.js"></script>
<script src="${ctx}/static/lib/layer/layer.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script src="${ctx}/static/lib/nprogress/nprogress.js"></script>
<script src="${ctx}/static/js/custom.js"></script>

<script>
    var list_url = '${ctx}/sys/teacher/getNoticeList';
    // 初始化表格数据
    var dataTable = $('#data-table').bootstrapTable('destroy').bootstrapTable({
        url: list_url,                      //  请求后台的URL
        method: "get",                      //  请求方式
        uniqueId: "Id",                     //  每一行的唯一标识，一般为主键列
        cache: false,                       //  设置为 false 禁用 AJAX 数据缓存， 默认为true
        pagination: true,                   //  是否显示分页
        sidePagination: "client",           //  分页方式：client客户端分页，server服务端分页
        pageSize: 10,                       //  每页的记录行数
        pageList:[5,10,20],
        queryParamsType: '',
        toolbar: "#toolbar",//显示工具栏
        queryParams: function (param) {
            return {
                pageNum: param.pageNumber,
                pageSize: param.pageSize,
                Id: null
            }
        },
        columns: [
            {
                field: 'titleName',
                title: '通知标题',
                formatter: function(value,row){
                    return '<a href="#" onclick="getNoticeOneData('+row.id+')" style="margin-bottom: 0px">'+value+'</a>'
                }
            }, {
                field: 'resourceName',
                title: '发送方'
            }, {
                field: 'receiverollName',
                title: '接收方'
            },{
                field: 'createTime',
                title: '发送时间'
            },{
                field: 'id',
                title: '操作',
                formatter: function(value){
                    return '<a href="javascript:void(0);"  class="btn btn-danger btn-sm" style="margin-bottom: 0px"  onclick="noticeDelete(\''+value+'\')">'+ '<span><i class="fa fa-times"></i>删除</span></a>'
                }
            }]
    });
    //删除
    function noticeDelete(s) {
        window.parent.layer.confirm("确认删除?", {icon: 3, offset: 't'}, function () {
            $.ajax({
                url: '${ctx}/sys/teacher/noticeDelete/' + s,
                type: 'get',
                success: function (response) {
                    if (response.code == 0) {
                        window.parent.layer.msg(response.msg, {icon: 1, time: 1000, offset: 't'});
                        window.location.href = '${ctx}/sys/teacher/deliverNotice';
                    } else {
                        window.parent.layer.alert(response.msg, {icon: 5, offset: 't'});
                        window.location.href = '${ctx}/sys/teacher/deliverNotice';
                    }
                }
            });
        })
    }
    //查看公告
    function getNoticeOneData(s) {
        $.ajax({
            url: '${ctx}/sys/teacher/getDeliverNoticeOneData/'+s,
            type: 'get',
            success: function (response) {
                if (response.code == 0) {
                    $("#pathName").html("");
                    $("#titleName").attr("value",response.notice.titleName);
                    $("#textName").text(response.notice.textName);
                    for(var i=0;i<response.notice.pathName.length;i++){
                        var path = response.notice.pathName[i].substring(36);
                        $("#pathName").append('<span><b>'+path+'</b>&nbsp;&nbsp;<a href=\"${ctx}/sys/teacher/downloadFile/'+response.notice.pathName[i]+'\">下载</a></span><br/>');
                    }
                    $("#myModal").modal();
                } else {
                    window.parent.layer.alert(response.msg, {icon: 5, offset: 't'});
                }
            }
        });
    }
    window.onload=function(){
        parent.scrollTo(0,0)
    }
</script>
</body>
</html>
