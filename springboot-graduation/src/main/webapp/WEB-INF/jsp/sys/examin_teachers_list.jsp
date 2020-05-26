<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>毕设选题系统 - 显示审题小组成员列表</title>
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

<div class="page-title">
    <div class="title_left" style="width: 200px">
        <h3>审题小组信息管理</h3>
    </div>
    <div  class="search_row" style="float: right">
        <div class="form-group" style="float:left" >      <%-- 通过左浮动使多个div在一行显示--%>
            <div class="search_label" >工号:</div>    <input type="text" class="editText" id="teaId" onkeyup="this.value=this.value.replace(/\D/g,'')" />
        </div>
        <div class="form-group" style="float:left" >
            <div class="search_label" >姓名:</div>    <input type="text" class="editText" id="teaName" onkeyup="this.value=this.value.replace(/[\d]/g,'')"  />
        </div>
        <div class="form-group" style="float:left" >
            <div class="search_label">学院(部):</div>
            <select class="" id="teaCollege"  style="height: 25px">
                <option value="" >全部</option>
                <c:forEach var="coll" items="${teaCollege}">
                    <option value="${coll.collName}">
                            ${coll.collName}
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group" style="float:left" >
            <div class="search_label">职称:</div>
            <select class="" id="teaEducation"  style="height: 25px">
                <option value="">全部</option>
                <option value="助教">助教</option>
                <option value="讲师">讲师</option>
                <option value="副教授">副教授</option>
                <option value="教授">教授</option>
            </select>
        </div>
        <div class="form-group" style="float:left" >
            <div class="search_label">账号:</div>
            <select class="" id="status"  style="height: 25px">
                <option value="" >全部</option>
                <option value="0">正常</option>
                <option value="1">禁用</option>
                <option value="2">锁定</option>
            </select>
        </div>
        <div class="form-group" style="float:left" >
            <div class="search_label"></div>        <button class="editText " type="button" id="btn-search" style="position: relative;top: 18px;left: 2px">搜索</button>
        </div>
    </div>
</div>
<div class="x_panel">
    <div class="row x_title">
        <h2>审题小组成员列表</h2>

    </div>
    <div class="x_content">
        <div id="toolbar" class="btn-toolbar">
            <div class="btn-group">
                <a href="${ctx}/sys/admin/addExamin_teacher" class="btn btn-default btn-sm"><i class="fa fa-plus"></i>新增</a>

                <button type="button" class="btn btn-default btn-sm" id="btn-update"><i class="fa fa-pencil"></i>修改</button>

                <button type="button" class="btn btn-default btn-sm" id="btn-delete"><i class="fa fa-times"></i>删除</button>

                <button type="button" class="btn btn-default btn-sm" id="btn-batchAdd"><i class="fa fa-plus"></i>批量导入</button>
            </div>
        </div>
        <table id="data-table"></table>

        <form class="form-horizontal" id="data-form" onsubmit="return false" data-parsley-validate  enctype="multipart/form-data"  method="post" style="display: none">
            <div class="form-group">
                <input type="file" id="importExel" name="file" />
            </div>
        </form>
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
    var list_url = '${ctx}/sys/admin/examin_teachersList';
    // 初始化表格数据
    var dataTable = $('#data-table').bootstrapTable('destroy').bootstrapTable({
        url: list_url,                      //  请求后台的URL
        method: "get",                      //  请求方式
        uniqueId: "examinteaId",                     //  每一行的唯一标识，一般为主键列
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
                examinteaId: $("#teaId").val(),
                examinteaName: $("#teaName").val(),
                status: $("#status").val(),
                examinteaEducation:$("#teaEducation").val(),
                teaCollege:$("#teaCollege").val()
            }
        },
        columns: [{
            checkbox: true,
        }, {
            field: 'examinteaId',
            title: '工号',
            sortable: true
        },{
            field: 'examinteaName',
            title: '姓名',
            sortable: true
        },{
            field: 'teaCollege',
            title: '学院(部)',
            sortable: true
        },{
            field: 'examinteaResEducation',
            title: '负责专业',
            sortable: true
        },{
            field: 'examinteaEducation',
            title: '职称',
            sortable: true
        },{
            field: 'examinteaTelphone',
            title: '手机号',
            sortable: true
        }, {
            field: 'examinteaEmail',
            title: '邮箱',
            sortable: true
        }, {
            field: 'examinteaUpdateTime',
            title: '修改时间',
            sortable: true
        }, {
            field: 'status',
            title: '账号状态',
            formatter: function(value){  // 自定义方法，添加按钮组
                if(value==0){
                    return '<a href="####" class="btn btn-info btn-sm" style="margin-bottom: 0px">' + '<span >正常</span></a>'
                }else if(value==1){
                    return '<a href="####" class="btn btn-danger btn-sm" style="margin-bottom: 0px">'+ '<span>禁用</span></a>'
                }else{
                    return '<a href="####" class="btn btn-warning btn-sm" style="margin-bottom: 0px">'+ '<span >锁定</span></a>'
                }
            },
            sortable: true
        }]
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
            window.location.href = '${ctx}/sys/admin/examin_teachersUpdate/' + rows[0].examinteaId;
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
                    url: '${ctx}/sys/admin/examin_teachersDelete/' + rows[0].examinteaId,
                    type: 'get',
                    success: function (response) {
                        if (response.code == 0) {
                            window.parent.layer.msg(response.msg, {icon: 1, time: 1000, offset: 't'});
                            refreshTable();
                        } else {
                            window.parent.layer.alert(response.msg, {icon: 5, offset: 't'});
                        }
                    }
                });
            })
        } else {
            window.parent.layer.confirm("确认批量删除?", {icon: 3, offset: 't'}, function () {
                var ids = new Array();//要删除的用户的id的集合
                for (var i = 0; i < rows.length; i++) {
                    ids.push(rows[i].examinteaId);
                }
                $.ajax({
                    url: '${ctx}/sys/admin/examin_teachersDeleteBatch',
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
                        }
                    }
                });
            });
        }
    });
    //批量导入数据
    $("#btn-batchAdd").click(function () {
        alert("请选择字段名为(用户名,姓名,学院(部),负责专业,职称,手机号,邮箱)的Excel表导入");
        $("#importExel").click();
    });

    $(document).off('change','#importExel').on('change','#importExel',function(){
        window.parent.layer.confirm("确认导入?", {icon: 3, offset: 't'}, function () {
            var formData = new FormData($("#data-form")[0]);
            $.ajax({
                url: '${ctx}/sys/admin/examin_teachersBatchAdd',
                type: 'post',
                processData: false,  //必须false才会避开jQuery对 formdata 的默认处理
                contentType: false,  //必须false才会自动加上正确的Content-Type
                data: formData,
                dataType: 'json',
                success: function (response) {
                    if (response.code == 0) {
                        window.parent.layer.msg(response.msg, {icon: 1, time: 500, offset: '0px'});
                        window.location.href = '${ctx}/sys/admin/examin_teacher';
                    } else {
                        window.parent.layer.alert(response.msg, {icon: 5, offset: '0px'});
                    }
                }
            })
        });
    });
    window.onload=function(){
        parent.scrollTo(0,0)
    }
</script>
</body>
</html>
