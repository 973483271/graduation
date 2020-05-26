<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>毕设选题系统 - 显示指导老师出题信息</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${ctx}/static/lib/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${ctx}/static/lib/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/static/lib/bootstrap-table/bootstrap-table.css" rel="stylesheet">
    <link href="${ctx}/static/lib/nprogress/nprogress.css" rel="stylesheet">
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

<%--<div id="star"></div>--%>
<%--<div>--%>
    <%--<span class="result"></span>--%>
<%--</div>--%>
<div class="page-title">
    <div class="title_left" style="float: left" >
        <h3>导师课题管理</h3>
    </div>
    <div style="position: absolute;z-index: -100;right: 60px;top: -1px"><img src="/static/img/blackboard.jpg" ></div>
    <div class="title_right" style="z-index: 1000;" >
        <span style="font-size: 15px;position: absolute;right:110px;color: #FFFFFF">拟题剩余:</span><br>
        <div style="position: absolute;right: 70px">
            <div  id="countdown" class="container timeBar" data="${balanceTime}" style="font-size: 16px;color: #FFFFFF"></div>
        </div>
        <div id="over" style="font-size: 15px;color: #FFFFFF;display: none;position: absolute;right: 110px">已结束</div>
    </div>
</div>
<div class="x_panel">
    <div class="row x_title" style="height: 48px">
        <h2>课题列表</h2>
        <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search"  style="width: 210px">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="输入课题名称/来源" id="tTitle">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button" id="btn-search">搜索</button>
                </span>
            </div>
        </div>
    </div>
    <div class="x_content">
        <div class="btn-toolbar">
            <div id="toolbar" class="btn-group">
                <shiro:hasPermission name="sys:teacher:assignTopic">
                <a href="${ctx}/sys/teacher/assignTopicAdd" class="btn btn-default btn-sm"><i class="fa fa-plus"></i>出题</a>
                <button type="button" class="btn btn-default btn-sm" id="btn-update"><i class="fa fa-pencil"></i>修改</button>
                <button type="button" class="btn btn-default btn-sm" id="btn-delete" ><i class="fa fa-times"></i>删除</button>
                </shiro:hasPermission>
                <button type="button" class="btn btn-default btn-sm" onclick="exportData()" ><i class="fa fa-download"></i>导出</button>
            </div>
        </div>
        <table id="data-table"></table>

    </div>
</div>

<script src="${ctx}/static/lib/jquery/jquery.js"></script>
<script src="${ctx}/static/lib/bootstrap/js/bootstrap.js"></script>
<script src="${ctx}/static/lib/layer/layer.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script src="${ctx}/static/lib/nprogress/nprogress.js"></script>
<script src="${ctx}/static/js/custom.js"></script>
<script src="${ctx}/static/lib/jquery-star-rating/lib/jquery.raty.js"></script>
<%--导出数据--%>
<script src="${ctx}/static/lib/bootstrap-table/FileSaver.min.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/xlsx.core.min.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/jspdf.min.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/jspdf.plugin.autotable.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/es6-promise.auto.min.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/html2canvas.min.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/tableExport.min.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table-export.js"></script>
<script src="${ctx}/static/lib/countdown/countdown.js"></script>

<script>
    var list_url = '${ctx}/sys/teacher/assignTopicList';
    // 初始化表格数据
    var dataTable = $('#data-table').bootstrapTable('destroy').bootstrapTable({
        url: list_url,                      //  请求后台的URL
        method: "get",                      //  请求方式
        uniqueId: "tNo",                     //  每一行的唯一标识，一般为主键列
        cache: false,                       //  设置为 false 禁用 AJAX 数据缓存， 默认为true
        pagination: true,                   //  是否显示分页
        sidePagination: "client",           //  分页方式：client客户端分页，server服务端分页
        pageSize: 10,                       //  每页的记录行数
        pageList:[5,10,20],
        queryParamsType: '',
        toolbar: "#toolbar",//显示工具栏
        exportDataType:"all",
        exportTypes: ['json', 'xml', 'png', 'csv', 'txt', 'sql', 'doc', 'excel','xlsx', 'pdf'],//导出格式
        queryParams: function (param) {
            return {
                pageNum: param.pageNumber,
                pageSize: param.pageSize,
                tTitle: $("#tTitle").val(),
                tNo: null
            }
        },
        columns: [{
            checkbox: true
        }, {
            field: 'tNo',
            title: '课题编号',
        }, {
            field: 'tTitle',
            title: '课题名称',
            sortable: true
        },{
            field: 'tTeacherName',
            title: '指导教师'
        },{
            field: 'tEducation',
            title: '课题所属组织'
        }, {
            field: 'tType',
            title: '课题类型',
            sortable: true
        }, {
            field: 'tSource',
            title: '课题来源',
            sortable: true
        },{
            field: 'tDifferent',
            title: '难度指数',
            sortable: true,
            formatter: function(value){  // 自定义方法，添加按钮组
                if(value==1){
                    return '<div ><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png">'
                }else if(value==2){
                    return '<div ><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png">'
                }else if(value==3){
                    return '<div ><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png">'
                }else if(value==4){
                    return '<div ><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-off.png">'
                }else {
                    return '<div ><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png"><img src="/static/lib/jquery-star-rating/lib/img/star-on.png">'
                }
            }
        },{
            field: 'tMaketime',
            title: '出题时间',
            sortable: true
        },{
            field: 'tExaminTeacherName',
            title: '审题教师',
            formatter: function(value) {
                return value
            },
            sortable: true
        },{
            field: 'tExamintime',
            title: '审题时间',
            sortable: true
        },{
            field: 'tStatus',
            title: '课题状态',
            formatter: function(value,row){
                if(value==1){
                    return '<span style="color: #F3AA00">待审核</span>'
                }else if(value==3){
                    return '<span style="color: #1ABB9C">审核通过'+'<span style="color: #9F0908">[已选]</span>'+'</span>'
                }else if(value==-1){
                    return '<span style="color: #9F0908">未通过</span>'
                }else if(value==2){
                    return '<span style="color: #1ABB9C">审核通过</span>'
                }
            }
        },{
            field: 'tNo',
            title: '查看详情',
            formatter: function(value){
                 return '<a href="${ctx}/sys/teacher/assignTopicShow/'+value+'" class="btn btn-default btn-sm" style="margin-bottom: 0px"><span>课题详情</span></a>'
        }}]
    });

    // 查询
    $('#btn-search').bind('click', function () {
        refreshTable();
    });

    // 刷新表格
    function refreshTable() {
        dataTable.bootstrapTable('refresh', {
            url: list_url,
            pageSize: 10,
            pageNumber: 1
        });
    }

    // 修改
    $('#btn-update').click(function () {
        var rows = $('#data-table').bootstrapTable('getSelections');
        if (rows.length == 0) {
            window.parent.layer.msg("请选择数据行!", {icon: 2, time: 1000, offset: 't'})
        } else if (rows.length != 1) {
            window.parent.layer.msg("一次只能修改一条数据!", {icon: 2, time: 1000, offset: 't'})
        } else {
            window.location.href = '${ctx}/sys/teacher/assignTopicUpdate/' + rows[0].tNo;
        }
    });

    // 删除
    $('#btn-delete').click(function () {
        var rows = $('#data-table').bootstrapTable('getSelections');
        if (rows.length == 0) {
            window.parent.layer.msg("请选择数据行!", {icon: 2, time: 1000, offset: 't'})
        } else if (rows.length == 1) {
            window.parent.layer.confirm("确认删除?", {icon: 3, offset: 't'}, function () {
                $.ajax({
                    url: '${ctx}/sys/teacher/assignTopicDelete/' + rows[0].tNo,
                    type: 'get',
                    success: function (response) {
                        if (response.code == 0) {
                            window.parent.layer.msg(response.msg, {icon: 1, time: 1000, offset: 't'});
                            refreshTable();
                        } else {
                            window.parent.layer.alert(response.msg, {icon: 5, offset: 't'});
                            window.location.href = '${ctx}/sys/teacher/assignTopic';
                        }
                    }
                });
            })
        } else {
            window.parent.layer.confirm("确认批量删除?", {icon: 3, offset: 't'}, function () {
                var ids = new Array();//要删除的用户的id的集合
                for (var i = 0; i < rows.length; i++) {
                    ids.push(rows[i].tNo);
                }
                $.ajax({
                    url: '${ctx}/sys/teacher/assignTopicDeleteBatch',
                    contentType: "application/json; charset=UTF-8",//发送给服务器的是json数据
                    type: 'post',
                    dateType: 'json',
                    data: JSON.stringify(ids),
                    success: function (response) {
                        if (response.code == 0) {
                            window.parent.layer.msg(response.msg, {icon: 1, time: 1000, offset: 't'});
                            refreshTable();
                        } else {
                            window.parent.layer.alert(response.msg, {icon: 5, offset: 't'});
                            window.location.href = '${ctx}/sys/teacher/assignTopic';
                        }
                    }
                });
            });
        }
    });
    $(document).ready(function () {
        $("#star").raty({
            hints:['1','2','3','4','5'],
            path:"${ctx}/static/lib/jquery-star-rating/lib/img",
            starOff:'star-off.png',
            starOn:'star-on.png',
            score:3,
            number:5,
            readOnly:false,
            size:24,
            target:'.result',
            targetKeep:true,
            click:function (score,evt) {

            }
        })
    });

    // 自定义按钮导出数据
    function exportData(){
        $('#data-table').tableExport({
            type: 'excel',
            exportDataType: "selected",
            ignoreColumn: [0,7,12],//忽略某一列的索引
            fileName: '指导老师出题信息汇总',//下载文件名称
            onCellHtmlData: function (cell, row, col, data){//处理导出内容,自定义某一行、某一列、某个单元格的内容
                console.info(data);
                return data;
            }
        });
    }
    //倒计时
    $(function() {
        if($("#countdown").attr("data")!=0) {    //倒计时开启
            $(".timeBar").each(function () {
                $(this).countdownsync({
                    dayTag: "<label class='tt dd dib vam'>00</label><span>天</span>",
                    hourTag: "<label class='tt hh dib vam'>00</label><span>时</span>",
                    minTag: "<label class='tt mm dib vam'>00</label><span>分</span>",
                    secTag: "<label class='tt ss dib vam'>00</label><span>秒</span>",
                    dayClass: ".dd",
                    hourClass: ".hh",
                    minClass: ".mm",
                    secClass: ".ss",
                    isDefault: false,
                    showTemp: 0

                }, function () {            //倒计时结束
                    $("#countdown").css("display", "none")
                    $("#over").css("display", "")
                });
            });
        }else if($("#countdown").attr("data")==0){                 //结束
            $("#countdown").css("display", "none")
            $("#over").css("display", "")
        }
    });

    window.onload=function(){
        parent.scrollTo(0,0)
    }
</script>

</body>
</html>
