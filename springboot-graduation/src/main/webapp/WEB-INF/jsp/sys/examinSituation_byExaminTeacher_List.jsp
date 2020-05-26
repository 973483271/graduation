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
<!-- 模态框（Modal） -->
<div class="modal fade" id="makeTeacherDataModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
    <div class="modal-dialog" style="z-index:10001"  >
        <div class="modal-content"  >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="makTeaDataModalLabel">指导老师信息</h4>
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
<div class="page-title">
    <div class="title_left" >
        <h3>审题情况</h3>
    </div>
</div>
<div class="x_panel">
    <div class="row x_title"  style="height: 48px">
        <h2>课题列表</h2>
        <div class="input-group col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search" style="float: right;width: 210px" >
            <input type="text" class="form-control" placeholder="通过/不通过" id="inputData">
            <span class="input-group-btn">
                    <button class="btn btn-default" type="button" id="btn-search">搜索</button>
            </span>
        </div>
    </div>
    <div class="x_content"style="display:inline">

    </div>
    <table id="data-table"></table>
</div><br/><br/>

<script src="${ctx}/static/lib/jquery/jquery.js"></script>
<script src="${ctx}/static/lib/bootstrap/js/bootstrap.js"></script>
<script src="${ctx}/static/lib/layer/layer.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script src="${ctx}/static/lib/nprogress/nprogress.js"></script>
<script src="${ctx}/static/js/custom.js"></script>
<script src="${ctx}/static/lib/jquery-star-rating/lib/jquery.raty.js"></script>
<script src="${ctx}/static/lib/echarts/js/echarts.min.js"></script>

<script>
    <%--选题--%>
    var list_url = '${ctx}/sys/examinteacher/getExaminSituation';
    // 初始化表格数据
    var dataTable = $('#data-table').bootstrapTable('destroy').bootstrapTable({
        url: list_url,                      //  请求后台的URL
        method: "get",                      //  请求方式
        uniqueId: "tNO",                     //  每一行的唯一标识，一般为主键列
        cache: false,                       //  设置为 false 禁用 AJAX 数据缓存， 默认为true
        pagination: true,                   //  是否显示分页
        sidePagination: "client",           //  分页方式：client客户端分页，server服务端分页
        pageSize: 10,                       //  每页的记录行数
        pageList:[5,10,20],
        queryParamsType: '',
        queryParams: function (param) {
            return {
                pageNum: param.pageNumber,
                pageSize: param.pageSize,
                inputData: $("#inputData").val()
            }
        },
        columns: [{
            field: 'tNo',
            title: '课题编号'
        }, {
            field: 'tMaketeacher',
            title: '指导老师工号',
            visible:false
        }, {
            field: 'tTitle',
            title: '课题名称',
            sortable: true
        },{
            field: 'tTeacherName',
            title: '指导教师',
            formatter: function(value,row) {
                return  '<a href="#makeTeacherDataModal" onclick="getTeacherData(\'' + row.tMaketeacher + '\')">' + value + '</a>'
            },
            sortable: true
        },{
            field: 'tEducation',
            title: '课题所属组织',
            sortable: true
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
            },
            sortable: true
        },{
            field: 'tMaketime',
            title: '出题时间',
            sortable: true
        },{
            field: 'tExamintime',
            title: '审题时间',
            sortable: true
        },{
            field: 'tNo',
            title: '查看详情',
            formatter: function(value){
                return '<a href="${ctx}/sys/teacher/assignTopicShow/'+value+'" class="btn btn-default btn-sm" style="margin-bottom: 0px"><span>课题详情</span></a>'
        }},{
            field: 'tStatus',
            title: '课题状态',
            align: 'center',
            formatter: function(value){
                if(value==-1) {
                    return '<span style="color: #9F0908;size: 30px;font-weight: 900"">未通过</span>'
                }else {
                    return '<span style="color: #1ABB9C;size: 30px;font-weight: 900">通过</span>'
                }
            },
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
    //查看指导师详情
    function getTeacherData(s) {
        $.ajax({
            url: '${ctx}/sys/student/getAssignTopicTeacherData/'+s,
            type: 'get',
            success: function (response) {
                if (response.code == 0) {
                    $("#id").text(response.teaData.teaId);
                    $("#name").text(response.teaData.teaName);
                    $("#education").text(response.teaData.teaEducation);
                    $("#telphone").text(response.teaData.teaTelphone);
                    $("#email").text(response.teaData.teaEmail);
                    $("#college").text(response.teaData.teaCollege);
                    $("#makeTeacherDataModal").modal()

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
