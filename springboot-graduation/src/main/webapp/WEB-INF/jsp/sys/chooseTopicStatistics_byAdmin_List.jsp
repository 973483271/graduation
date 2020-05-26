<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>毕设选题系统 - 选题统计页面 - 管理员</title>
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
<div class="x_panel" >
    <span style="font-weight: bold;">学生总数:</span>&nbsp;&nbsp;<span style="color: red;font-size: 20px">${chooseTopicNum.stuChooseTopicTotalNum}</span>
    <span style="font-weight: bold;margin-left: 70px">已选人数:</span>&nbsp;&nbsp;<span style="color: red;font-size: 20px">${chooseTopicNum.stuHasChooseTopicNum}</span>
    <span style="font-weight: bold;margin-left: 70px">未选人数:</span>&nbsp;&nbsp;<span style="color: red;font-size: 20px">${chooseTopicNum.stuNotChooseTopicNum}</span>
</div><br/><br/>
<div class="x_panel">
    <div class="row x_title">
        <h2>已选题学生列表</h2>
    </div>
    <div class="x_content"style="display:inline">
        <div class="input-group col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search" style="float: right;width: 220px" >
            <input type="text" class="form-control" placeholder="输入学院/专业/班级" id="inputData">
            <span class="input-group-btn">
                    <button class="btn btn-default" type="button" id="btn-search">搜索</button>
            </span>
        </div>
        <div>
            <button type="button" class="btn btn-default btn-sm" onclick="exportData()" ><i class="fa fa-download"></i>导出</button>
        </div>
    </div>
    <table id="data-table"></table>
</div><br/><br/>
<div class="x_panel">
    <div class="row x_title">
        <h2>未选题学生列表</h2>
    </div>
    <div class="x_content"style="display:inline">
        <div class="input-group col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search" style="float: right;width: 220px" >
            <input type="text" class="form-control" placeholder="输入学院/专业/班级" id="iData">
            <span class="input-group-btn">
                    <button class="btn btn-default" type="button" id="btn-search2">搜索</button>
            </span>
        </div>
        <div>
            <button type="button" class="btn btn-default btn-sm" onclick="exportData()" ><i class="fa fa-download"></i>导出</button>
        </div>
    </div>
    <table id="data-table2"></table>
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
<script src="${ctx}/static/lib/echarts/js/echarts.min.js"></script>
<%--导出数据--%>
<script src="${ctx}/static/lib/bootstrap-table/FileSaver.min.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/xlsx.core.min.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/jspdf.min.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/jspdf.plugin.autotable.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/es6-promise.auto.min.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/html2canvas.min.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/tableExport.min.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table-export.js"></script>

<script>
    <%--选题--%>
    var list_url = '${ctx}/sys/admin/hasChooseTopicStudentSituation';
    // 初始化表格数据
    var dataTable = $('#data-table').bootstrapTable('destroy').bootstrapTable({
        url: list_url,                      //  请求后台的URL
        method: "get",                      //  请求方式
        uniqueId: "stuId",                     //  每一行的唯一标识，一般为主键列
        cache: false,                       //  设置为 false 禁用 AJAX 数据缓存， 默认为true
        pagination: true,                   //  是否显示分页
        sidePagination: "client",           //  分页方式：client客户端分页，server服务端分页
        pageSize: 10,                       //  每页的记录行数
        pageList:[5,10,20],
        queryParamsType: '',
        exportDataType:"all",
        exportTypes: ['json', 'xml', 'png', 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf'],//导出格式
        exportOptions: {//导出设置
            fileName: '学生选题信息汇总'//下载文件名称
        },
        queryParams: function (param) {
            return {
                pageNum: param.pageNumber,
                pageSize: param.pageSize,
                inputData: $("#inputData").val(),
                stuId: null
            }
        },
        columns: [
            {
                field: 'stuId',
                title: '学生学号',
                sortable: true
            },
            {
                field: 'stuName',
                title: '选题学生',
                sortable: true
            }, {
                field: 'stuCollege',
                title: '学院(部)',
                sortable: true
            },{
                field: 'stuEducation',
                title: '学生专业',
                sortable: true
            },{
                field: 'stuClass',
                title: '学生班级',
                sortable: true
            },{
                field: 'stuTopicTitle',
                title: '课题名称',
                sortable: true
            }]
    });
    <%--未选题--%>
    var list_url2 = '${ctx}/sys/admin/notChooseTopicStudentSituation';
    // 初始化表格数据
    var dataTable2 = $('#data-table2').bootstrapTable('destroy').bootstrapTable({
        url: list_url2,                      //  请求后台的URL
        method: "get",                      //  请求方式
        uniqueId: "stuId",                     //  每一行的唯一标识，一般为主键列
        cache: false,                       //  设置为 false 禁用 AJAX 数据缓存， 默认为true
        pagination: true,                   //  是否显示分页
        sidePagination: "client",           //  分页方式：client客户端分页，server服务端分页
        pageSize: 10,                       //  每页的记录行数
        pageList:[5,10,20],
        queryParamsType: '',
        exportDataType:"all",
        exportTypes: ['json', 'xml', 'png', 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf'],//导出格式
        exportOptions: {//导出设置
            fileName: '未选题学生信息汇总'//下载文件名称
        },

        queryParams: function (param) {
            return {
                pageNum: param.pageNumber,
                pageSize: param.pageSize,
                iData: $("#iData").val(),
                stuId: null
            }
        },
        columns: [
            {
                field: 'stuId',
                title: '学生学号',
                sortable: true
            },
            {
                field: 'stuName',
                title: '学生姓名',
                sortable: true
            }, {
                field: 'stuCollege',
                title: '学院(部)',
                sortable: true
            },{
                field: 'stuEducation',
                title: '学生专业',
                sortable: true
            },{
                field: 'stuClass',
                title: '学生班级',
                sortable: true
            }]
    });

    // 查询------选题
    $('#btn-search').bind('click', function () {
        refreshTable();
    });
    // 刷新表格--选题
    function refreshTable() {
        dataTable.bootstrapTable('refresh', {
            url: list_url,
            pageSize: 10,
            pageNumber: 1
        });
    }
    // 查询------未选题
    $('#btn-search2').bind('click', function () {
        refreshTable2();
    });
    // 刷新表格 --未选题
    function refreshTable2() {
        dataTable2.bootstrapTable('refresh', {
            url: list_url2,
            pageSize: 10,
            pageNumber: 1
        });
    }
    // 自定义按钮导出数据 --导出选题学生信息
    function exportData(){
        $('#data-table').tableExport({
            type: 'excel',
            exportDataType: "selected",
            ignoreColumn: [],//忽略某一列的索引
            fileName: '学生选题信息汇总',//下载文件名称
            onCellHtmlData: function (cell, row, col, data){//处理导出内容,自定义某一行、某一列、某个单元格的内容
                console.info(data);
                return data;
            }
        });
    }
    // 自定义按钮导出数据 --导出未选题学生信息
    function exportData(){
        $('#data-table2').tableExport({
            type: 'excel',
            exportDataType: "selected",
            ignoreColumn: [],//忽略某一列的索引
            fileName: '未选题学生信息汇总',//下载文件名称
            //处理导出内容,自定义某一行、某一列、某个单元格的内容
            onCellHtmlData: function (cell, row, col, data){
                console.info(data);
                return data;
            }
        });
    }
    window.onload=function(){
        parent.scrollTo(0,0)
    }
</script>

</body>
</html>
