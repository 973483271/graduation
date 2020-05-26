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
        .SwitchIcon {
            margin-top:1px;
        }

        #toggle-button1,#toggle-button2,#toggle-button3 {
            display: none;
        }

        .button-label {
            position: relative;
            display: inline-block;
            width: 80px;
            height: 30px;
            background-color: #ccc;
            box-shadow: #ccc 0px 0px 0px 2px;
            border-radius: 30px;
            overflow: hidden;
        }

        .circle {
            position: absolute;
            top: 0;
            left: 0;
            width: 30px;
            height: 30px;
            border-radius: 50%;
            background-color: #fff;
        }

        .button-label .text {
            line-height: 30px;
            font-size: 18px;
            text-shadow: 0 0 2px #ddd;
        }

        .on {
            color: #fff;
            display: none;
            text-indent: -45px;
        }

        .off {
            color: #fff;
            display: inline-block;
            text-indent: 34px;
        }

        .button-label .circle {
            left: 0;
            transition: all 0.3s;
        }
        /*出题*/
        #toggle-button1:checked + label.button-label .circle {
            left: 50px;
        }

        #toggle-button1:checked + label.button-label .on {
            display: inline-block;
        }

        #toggle-button1:checked + label.button-label .off {
            display: none;
        }

        #toggle-button1:checked + label.button-label {
            background-color: #19e236;
        } /*选题*/
        #toggle-button2:checked + label.button-label .circle {
            left: 50px;
        }

        #toggle-button2:checked + label.button-label .on {
            display: inline-block;
        }

        #toggle-button2:checked + label.button-label .off {
            display: none;
        }

        #toggle-button2:checked + label.button-label {
            background-color: #19e236;
        }
        /*审题*/
        #toggle-button3:checked + label.button-label .circle {
            left: 50px;
        }

        #toggle-button3:checked + label.button-label .on {
            display: inline-block;
        }

        #toggle-button3:checked + label.button-label .off {
            display: none;
        }

        #toggle-button3:checked + label.button-label {
            background-color: #19e236;
        }


    </style>
</head>
<body class="content_col">
<div class="page-title">
    <div class="title_left" style="float: left" >
        <h3>过程控制管理</h3>
    </div>
</div>
<div class="x_panel">
    <div class="row x_title">
        <h2>过程控制</h2>
    </div>
    <table id="data-table"  style="min-width:500px;"></table>
</div>

<script src="${ctx}/static/lib/jquery/jquery.js"></script>
<script src="${ctx}/static/lib/bootstrap/js/bootstrap.js"></script>
<script src="${ctx}/static/lib/layer/layer.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table.js"></script>
<script src="${ctx}/static/lib/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script src="${ctx}/static/lib/nprogress/nprogress.js"></script>
<script src="${ctx}/static/js/custom.js"></script>

<script>
    <%--选题--%>
    var list_url = '${ctx}/sys/user/getProcessControlData';
    // 初始化表格数据
    var dataTable = $('#data-table').bootstrapTable('destroy').bootstrapTable({
        url: list_url,                      //  请求后台的URL
        method: "get",                      //  请求方式
        uniqueId: "qId",                     //  每一行的唯一标识，一般为主键列
        cache: false,                       //  设置为 false 禁用 AJAX 数据缓存， 默认为true
        pagination: true,                   //  是否显示分页
        sidePagination: "client",           //  分页方式：client客户端分页，server服务端分页
        pageSize: 15,                       //  每页的记录行数
        queryParamsType: '',
        queryParams: function (param) {
            return {
                pageNum: param.pageNumber,
                pageSize: param.pageSize
            }
        },
        columns: [
            {
                field: 'qId',
                title: '过程名称',
                visible:false
            },

            {
                field: 'qName',
                title: '过程名称'
            },
             {
                field: 'qCron',
                title: '截止日期',
                 width:'300px',
                 formatter: function(value,row){
                    if(row.qName=="出题"){
                        return '<input id="'+row.qName+'" type="date" style="width: 250px" min="${nowTime}" value="${defaultTime[0].qCron}"/>'
                    }else if(row.qName=="审题"){
                        return '<input id="'+row.qName+'" type="date" style="width: 250px" min="${nowTime}" value="${defaultTime[1].qCron}"/>'
                    }else {
                        return '<input id="'+row.qName+'" type="date" style="width: 250px" min="${nowTime}" value="${defaultTime[2].qCron}"/>'
                    }

                 }
            },{
                field: 'qStatus',
                title: '操作',
                align: 'center',
                formatter: function(value,row){  // 自定义方法，添加按钮组
                    if(value=='OFF'){
                        return '<div class="SwitchIcon">'+
                            '<div class="toggle-button-wrapper">'+
                            '<input type="checkbox" id="toggle-button'+row.qId+'" name="switch" onclick="SwitchClick(this,\''+row.qName+'\')">'+
                            '<label for="toggle-button'+row.qId+'" class="button-label">'+
                            '<span class="circle"></span>'+
                            '<span class="text on">ON</span>'+
                            '<span class="text off">OFF</span>'+
                            '</label>'+
                            '</div>'+
                            '</div>'
                    }else{
                        return '<div class="SwitchIcon">'+
                            '<div class="toggle-button-wrapper">'+
                            '<input type="checkbox" id="toggle-button'+row.qId+'" name="switch" onclick="SwitchClick(this,\''+row.qName+'\')" checked="false"  >'+
                            '<label for="toggle-button'+row.qId+'" class="button-label">'+
                            '<span class="circle"></span>'+
                            '<span class="text on">ON</span>'+
                            '<span class="text off">OFF</span>'+
                            '</label>'+
                            '</div>'+
                            '</div>'
                    }
                }

            }]
    });

    // 刷新表格--选题
    function refreshTable() {
        dataTable.bootstrapTable('refresh', {
            url: list_url,
            pageSize: 15,
            pageNumber: 1
        });
    }
    function SwitchClick(e,r) {
        if (e.checked) {
            if(document.getElementById(r).value==""){
                window.parent.layer.msg("请设置截止时间", {icon: 2, time: 1000, offset: 't'})
                refreshTable();
            }else {
                $.ajax({
                    url: '${ctx}/sys/user/setProcessControlData/',
                    contentType: "application/json; charset=UTF-8",//发送给服务器的是json数据
                    type: 'post',
                    dateType: 'json',
                    data: JSON.stringify({"qCron":document.getElementById(r).value,"qName":r,"qStatus":"ON"}),
                    success: function (response) {
                        if (response.code == 0) {
                            window.parent.layer.msg(response.msg, {icon: 1, time: 1000, offset: 't'});
                            window.location.href = '${ctx}/sys/user/processControl'
                        } else {
                            window.parent.layer.alert(response.msg, {icon: 5, offset: 't'});
                        }
                    }
                });
            }
        }
        else {
            $.ajax({
                url: '${ctx}/sys/user/setProcessControlData/',
                contentType: "application/json; charset=UTF-8",//发送给服务器的是json数据
                type: 'post',
                dateType: 'json',
                data: JSON.stringify({"qCron":"","qName":r,"qStatus":"OFF"}),
                success: function (response) {
                    if (response.code == 0) {
                        window.parent.layer.msg(response.msg, {icon: 1, time: 1000, offset: 't'});
                        window.location.href = '${ctx}/sys/user/processControl'
                    } else {
                        window.parent.layer.alert(response.msg, {icon: 5, offset: 't'});
                    }
                }
            });
        }
    }
    window.onload=function(){
        parent.scrollTo(0,0)
    }
</script>

</body>
</html>
