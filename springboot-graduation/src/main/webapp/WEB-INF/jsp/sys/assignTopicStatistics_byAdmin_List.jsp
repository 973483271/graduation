<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>毕设选题系统 - 出题统计页面 - 管理员</title>
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
    <div class="modal-dialog" style="width: 900px">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">专业指导教师出题信息详情</h4>
            </div>
            <div class="modal-body" >
                <table id="data-table2"  style="min-width:600px;"></table>
                <br><br>
                <div id="teaTopicNum" style="width: 500px;height:300px;"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<div class="x_panel">
    <div class="page-title">
        <div class="title_left" >
            <h3>各专业出题情况统计</h3>
        </div>
        <div class="title_right">
            <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="输入学院/专业" id="tTitle">
                    <span class="input-group-btn">
                    <button class="btn btn-default" type="button" id="btn-search">搜索</button>
                </span>
                </div>
            </div>
        </div>
    </div>
    <div class="x_content">
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
<script src="${ctx}/static/lib/echarts/js/echarts.min.js"></script>


<script>
    var list_url = '${ctx}/sys/admin/assignTopicSituation';
    // 初始化表格数据
    var dataTable = $('#data-table').bootstrapTable('destroy').bootstrapTable({
        url: list_url,                      //  请求后台的URL
        method: "get",                      //  请求方式
        uniqueId: "eduId",                     //  每一行的唯一标识，一般为主键列
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
                tTitle: $("#tTitle").val(),
                eduId: null
            }
        },
        columns: [
        {
            field: 'education',
            title: '专业名称',
            sortable: true
        },{
            field: 'college',
            title: '所属学院(部)',
                sortable: true
        }, {
            field: 'studentNum',
            title: '专业人数',
            sortable: true
        },{
            field: 'topicNum',
            title: '过审题数',
             sortable: true
        },{
            field: 'resultNum',
            title: '统计结果 (过审题数-专业人数)',
            formatter: function(value,row){
                if(row.studentNum>row.topicNum){
                    return '<span style="color: #DF072E;font-size: 20px">'+value+'</span>'+'<a href="#myModal" onclick="assignTopicSituation(\''+row.eduId+'\')" style="float: right">'+' [详情]'+'</a>'
                }else {
                    return '<span style="color: #5A738E;font-size: 20px">'+value+'</span>'+'<a href="#myModal" onclick="assignTopicSituation(\''+row.eduId+'\')" style="float: right">'+' [详情]'+'</a>'
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
    //查看指定专业的出题详情用于统计图显示
    function assignTopicSituation(s) {
        $.ajax({
            url: '${ctx}/sys/admin/getAssignTopicDetails/' + s,
            type: 'get',
            success: function (response) {
                if (response.code == 0) {
                    // 基于准备好的dom，初始化echarts实例
                    var myChart = echarts.init(document.getElementById('teaTopicNum'));
                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '指导教师题目过审率'
                        },
                        tooltip: {
                            formatter: '{b}:\n{c}%'
                        },
                        legend: {
                            data:['过审率']
                        },
                        xAxis: {
                            data: $.parseJSON(response.data.xAxisData)
                        },
                        yAxis: {
                            min:0,
                            max:100
                        },
                        series: [{
                            name: '过审率',
                            type: 'bar',
                            data: $.parseJSON(response.data.yAxisData)
                        }]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                    $("#myModal").modal();
                } else {
                    window.parent.layer.alert(response.msg, {icon: 5, offset: 't'});
                }
            }
        });

        var aeatd_url = '${ctx}/sys/admin/getAppointEducationAssignTopicDetails/'+s;
        // 初始化表格数据
        var dataTable = $('#data-table2').bootstrapTable('destroy').bootstrapTable({
            url: aeatd_url,                      //  请求后台的URL
            method: "get",                      //  请求方式
            uniqueId: "teacherId",                     //  每一行的唯一标识，一般为主键列
            cache: false,                       //  设置为 false 禁用 AJAX 数据缓存， 默认为true
            pagination: true,                   //  是否显示分页
            sidePagination: "client",           //  分页方式：client客户端分页，server服务端分页
            pageSize: 5,                       //  每页的记录行数
            queryParamsType: '',
            queryParams: function (param) {
                return {
                    pageNum: param.pageNumber,
                    pageSize: param.pageSize,
                    teacherId: null
                }
            },
            columns: [
                {
                    field: 'teacherName',
                    title: '指导教师',
                    sortable: true
                },{
                    field: 'teacherTopicNum',
                    title: '出题数',
                    sortable: true
                }, {
                    field: 'teacherPassNum',
                    title: '过审题数',
                    sortable: true
                },{
                    field: 'teacherPassingRate',
                    title: '过审率',
                    formatter: function(value){
                       return '<span style="color: #DF072E;">'+value+'%'+'</span>'
                    },
                    sortable: true
                }]
        });
    }
    window.onload=function(){
        parent.scrollTo(0,0)
    }
</script>

</body>
</html>
