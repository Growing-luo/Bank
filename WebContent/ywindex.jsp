<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <title>主页面</title>
        <meta charset="UTF-8" />
        <!--引入主题样式-->
        <link rel="stylesheet" type="text/css" href="css/easyui.css" />
        <!--引入图标样式-->
        <link rel="stylesheet" type="text/css" href="css/easyui.css" />
        <!--引入jQuery文件-->
        <script src="js/jquery.min.js" type="text/javascript" charset="utf-8"></script>
        <!--引入EasyUi的js文件-->
        <script src="js/jquery.easyui.min.js" type="text/javascript" charset="utf-8"></script>
        <script type="text/javascript">
			setInterval("showtime()",1000);   //每隔1s执行一次showtime()	
			function showtime(){
				var d = new Date();           //获取当前时间
				var year = d.getFullYear();   //获取当前年
				var month = (d.getMonth()+1); //获取当前月
				var day = d.getDate();        //获取当前天
				var hour = d.getHours();      //获取当前小时
				var minute = d.getMinutes();  //获取当前分钟
				var second  =d.getSeconds();  //获取当前秒
				var t = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
				document.getElementById("time").innerHTML=t;
			}
		</script>
       	<!--声明js代码域-->
        <script type="text/javascript">
            /*网页js功能*/
            $(function() {
           
            //树状菜单和选项卡的联动
                //给菜单添加单击事件
                $(".treeMenu").tree({
                    onClick:function(node){
                        //控制台打印node内容
                            //console.log(node);
                        //获取attributes属性,判断是菜单还是菜单描述
                        var attrs=node.attributes;
                        if(attrs==null || attrs.url==null){
                            return;
                        }
                        //判断选项卡是否存在
                        var has=$("#div_tabs").tabs("exists",node.text);
                        if(has){
                            //选中存在的选项卡
                            $("#div_tabs").tabs("select",node.text);
                        }else{
                            //创建新的选项卡面板
                            $("#div_tabs").tabs("add",{
                                title:node.text,
                                closable:true,
                                content:"<iframe src="+attrs.url+" style='width:100%;height:98%' frameborder='0'/>"
                            })
                        }
                    }
                })

            })
        </script>
    </head>

    <body class="easyui-layout" onload="showtime()">
        
        <!--布局：西-->
        <div data-options="region:'west',title:'系统菜单',split:true" style="width: 200px;">
            <!--分类效果实现-->
            <div class="easyui-accordion" data-options="fit:true,border:false">
                <div id="" title="交易管理" >
                    <!--创建菜单-->
                    <ul class="treeMenu" class="easyui-tree">
                   		<li data-options="attributes:{url:'trans/query_balance.jsp'}">查询余额</li>
                        <li data-options="attributes:{url:'trans/qukuan.jsp'}">取款</li>
                        <li data-options="attributes:{url:'trans/cunkuan.jsp'}">存款</li>
                        <li data-options="attributes:{url:'trans/zhuanzhang.jsp'}">转账</li>
                        <li data-options="attributes:{url:'trans/query_record.jsp'}">查询交易记录</li>
                        <li data-options="attributes:{url:'https://www.baidu.com'}">百度</li>
                    </ul>
                </div>
                <div id="" title="账户管理">
					<!--创建菜单-->
                    <ul class="treeMenu" class="easyui-tree">
                        <li data-options="attributes:{url:'account/information.jsp'}">账户信息管理</li>
                    </ul>
                </div>
                <div id="" title="客户管理">
					<!--创建菜单-->
                    <ul class="treeMenu" class="easyui-tree">
                        <li data-options="attributes:{url:'customer/information.jsp'}">客户信息管理</li>
                    </ul>
                </div>

            </div>

        </div>
        <!--布局：中间-->
        <div data-options="region:'center'">
            <!--选项卡使用-->
            <div id="div_tabs" class="easyui-tabs" data-options="fit:true,border:false">
                <!--首页-->
                <div id="" title="首页" style="text-align:center;margin-top: 200px">
					<h1 align="center">你好：<%=session.getAttribute("username") %></h1>
					<h1 align="center">欢迎进入中国银行业务员操作系统</h1>
					<br>
					<font size="5px">当前时间：</font>
					<span style="font-size: 30px" id="time"></span>
				
                </div>
            </div>
        </div>

    </body>

</html>