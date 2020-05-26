<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>毕业论文(设计)选题系统</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${ctx}/static/lib/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${ctx}/static/lib/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${ctx}/static/lib/nprogress/nprogress.css" rel="stylesheet">
    <link href="${ctx}/static/css/custom.min.css" rel="stylesheet">
    <style>
        .badge-danger {
            color: #fff;
            background-color: #dc3545;
        }
    </style>
</head>
<body class="nav-md">
<div class="container body">
    <div class="main_container">
        <!-- header content -->
            <div class="top_nav" >
                <div class="nav_menu "  style="height: 55px;position: fixed " >
                    <nav style="font-size: 14px;"  >
                        <div class="nav toggle " style="">
                            <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                        </div>
                        <div class="nav toggle" style="" >
                            <a href="#" onclick="refreshPage()"><i class="fa fa-refresh" ></i></a>
                        </div>

                        <ul class="nav navbar-default navbar-right" style="text-align: center;background-color:#EDEDED;height: 55px; position: fixed;right: 0px">
                            <li class="" style="display: inline-block;height: 55px">
                                <a href="${ctx}/logout" style="height: 55px;">
                                    <span class="fa fa-power-off" style="position: relative;top: 3px"></span >
                                    <span style="position: relative;top: 3px"> 退出系统</span></a>
                            </li>

                            <li class="dropdown" id="li_1">
                                <a href="javascript:;" class="user-profile dropdown-toggle " data-toggle="dropdown"
                                   id="dropdownMenu1">
                                    <img src="static/img/mouse.jpg" alt=""><span>${UR.userName}【${UR.roleName}】</span>
                                    <span class="fa fa-angle-down" id="spacer1"></span></a>
                                <ul class="dropdown-menu "  aria-labelledby="dropdownMenu1" style="min-width: 100%" >
                                    <li ><a href="#" onclick="selfData()" style="text-align: center">个人中心</a></li>
                                    <li ><a href="#" onclick="updatePass()" style="text-align: center">修改密码</a></li>
                                </ul>
                            </li>
                            <shiro:hasAnyRoles name="审题老师,指导老师,学生">
                                <li class="dropdown" id="li_2"  style="width: 11%;height: 55px" >
                                    <a href="javascript:;" class="user-profile dropdown-toggle " data-toggle="dropdown"  style="height: 55px;">
                                    <span class="fa fa-bell-o fa-lg" id="lingdang" style=""></span>
                                          <span  class="badge badge-danger notReadNum" style="position: absolute;top:6px;right: 28px;font-size: 10px">
                                     </span></a>
                                    <ul class="dropdown-menu "  aria-labelledby="dropdownMenu1" style="min-width: 100%" >
                                        <li ><a href="#" onclick="notice()" style="text-align: center">
                                            通知<span style="color: red;font-size: 14px;font-weight: 600;margin-left: 5px" class="notReadNum"></span>
                                        </a></li>
                                    </ul>
                                </li>
                            </shiro:hasAnyRoles>
                        </ul>
                    </nav>
                </div>
            </div>

        <!-- /header content -->
        <!--/left menu -->
        <!--left menu -->
        <div class="col-md-3 left_col" style="position: fixed;">
            <div class="left_col scroll-view" >
                <div class="navbar nav_title" style="border: 0;">
                    <a href="${ctx}/index" class="site_title"><i class="fa fa-graduation-cap"></i> <span>毕设选题系统</span></a>
                </div>

                <div class="clearfix"></div>

                <%--个人信息--%>
                <div class="profile clearfix">
                    <div class="profile_pic">
                        <img src="static/img/mouse.jpg" class="img-circle profile_img">
                    </div>
                    <div class="profile_info">
                        <span>欢迎使用</span>
                    </div>
                </div>

                <!-- 导航菜单 -->
                <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
                    <div class="menu_section">
                        <ul class="nav side-menu"></ul>
                    </div>
                </div>
                <!-- /导航菜单 -->
            </div>
        </div>
        <!--/left menu -->
        <!-- page content -->
        <div class="right_col" >
            <iframe style="margin-top: 60px"
                    src="${ctx}/main"
                    frameborder="0"
                    scrolling="no"
                    id="main-body"
                    name="main-body"
                    width="100%"
                    height="1500px">
            </iframe>
        </div>
        <!-- /page content -->
    </div>
</div>
        <script src="${ctx}/static/lib/jquery/jquery.js"></script>
        <script src="${ctx}/static/lib/bootstrap/js/bootstrap.js"></script>
        <script src="${ctx}/static/lib/layer/layer.js"></script>
        <script src="${ctx}/static/lib/art-template/template-web.js"></script>
        <script src="${ctx}/static/lib/nprogress/nprogress.js"></script>
        <script src="${ctx}/static/js/custom.js"></script>
        <script id="tpl-menu" type="text/html">
    {{each menuList menu}}
    <li>
        <a><i class="{{menu.icon}}"></i>{{menu.text}}<span class="fa fa-chevron-down"></span></a>
        <ul class="nav child_menu">
            {{each menu.nodes sub_menu}}
            <li>
                {{if sub_menu.nodes}}
                <a href="javascript:;" url="${ctx}/{{sub_menu.url}}">{{sub_menu.text}}</a>
                {{else}}
                <a href="javascript:;">{{sub_menu.text}}<span class="fa fa-chevron-down"></span></a>
                <ul class="nav child_menu">
                    {{each sub_menu.nodes sub_sub_menu}}
                    <li>
                        <a href="javascript:;"
                           url="${ctx}/{{sub_sub_menu.url}}">{{sub_sub_menu.text}}</a>
                    </li>
                    {{/each}}
                </ul>
                {{/if}}
            </li>
            {{/each}}
        </ul>
    </li>
    {{/each}}
</script>

<script>
    $.ajax({
        url: '${ctx}/menu',
        type: 'get',
        dataType: 'json',
        success: function (response) {
            if (response.code == 0) {
                var html = template('tpl-menu', {
                    menuList: response.data
                });
                $('#sidebar-menu .side-menu').html(html);
                init_sidebar();
                // 点击左侧菜单
                $('.child_menu a').click(function () {
                    $('.child_menu li').removeClass('active');
                    var url = $(this).attr('url');
                    if (url) {
                        $('#main-body').attr('src', url);
                    }
                });
            } else {
                window.parent.parent.layer.alert(response.msg, {icon: 5, offset: 't'});
            }
        }
    });
    //刷新页面
    function refreshPage() {
        var url = $('#main-body').attr('src');
        $('#main-body').attr('src', url);
    }
    //个人中心
    function selfData() {
        $('#main-body').attr('src', '${ctx}/sys/user/data');
    }
    //修改密码
    function updatePass() {
        $('#main-body').attr('src', '${ctx}/sys/user/password');
    }
    //消息通知
    function notice() {
        $('#main-body').attr('src', '${ctx}/sys/${noticeUrl}/receiveNotice');
    }
    //获取未读通知数量
    $(function () {
        getNumOfNotRead();
    });
    function getNumOfNotRead() {
        $.ajax({
            url: '${ctx}/sys/notice/getNumOfNotRead',
            type: 'get',
            success: function (response) {
                if(response>0&&response<=99){
                    $(".notReadNum").show();
                    $(".notReadNum").html(response)
                }else if(response==0) {
                    $(".notReadNum").hide();
                }else if(response>99){
                    $(".notReadNum").show();
                    $(".notReadNum").html("99+")
                }

            }
        });
    }
</script>
</body>
