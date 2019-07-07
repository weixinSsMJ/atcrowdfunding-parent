<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link rel="stylesheet"
	href="${ctx}/static/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/static/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctx}/static/css/main.css">
<link rel="stylesheet" href="${ctx}/static/ztree/zTreeStyle.css">
<style>
.tree li {
	list-style-type: none;
	cursor: pointer;
}

table tbody tr:nth-child(odd) {
	background: #F4F4F4;
}

table tbody td:nth-child(even) {
	color: #C00;
}
</style>
</head>

<body>

	<%pageContext.setAttribute("title", "角色维护"); %>
	<!-- 顶部导航 -->
	<%@include file="/include/top-nav.jsp"%>

	<div class="container-fluid">
		<div class="row">
			<%@include file="/include/side-bar.jsp"%>

			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input id="searchConditionInput" class="form-control has-success" type="text"
										placeholder="请输入查询条件">
								</div>
							</div>
							<button type="button" class="btn btn-warning" id="searchBtn">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button id="deleteAllRoleBtn" type="button" class="btn btn-danger"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<button type="button" class="btn btn-primary"
							style="float: right;" id="openRoleAddModelBtn">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input type="checkbox" id="allCheckBtn"></th>
										<th>名称</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody id="content">
								</tbody>
								<tfoot>
									<tr>
										<td colspan="6" align="center">
											<ul class="pagination" id="pageInfoBar">
												<%-- <li class="disabled"><a href="${ctx}/static/#">上一页</a></li>
												<li class="active"><a href="${ctx}/static/#">1 <span
														class="sr-only">(current)</span></a></li>
												<li><a href="${ctx}/static/#">2</a></li>
												<li><a href="${ctx}/static/#">3</a></li>
												<li><a href="${ctx}/static/#">4</a></li>
												<li><a href="${ctx}/static/#">5</a></li>
												<li><a href="${ctx}/static/#">下一页</a></li> --%>
											</ul>
										</td>
									</tr>

								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!--  模态框-->

	<!-- Modal添加 -->
	<div class="modal fade" id="roleAddModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="roleAddModalTitle">角色新增</h4>
				</div>
				<div class="modal-body">
					<form action="${ctx }/role/add" method="post" id="roleAddForm">
					  <div class="form-group">
					    <label>角色名</label>
					    <input type="email" class="form-control" id="rolename_input" name="name">
					  </div>
					</form>	
				
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveRoleAddBtn">保存</button>
				</div>
			</div>
		</div>
	</div>


	<!-- Modal修改-->
	<div class="modal fade" id="roleUpdateModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">角色修改</h4>
				</div>
				<div class="modal-body">
					<form action="${ctx }/role/add" method="post" id="roleUpdateForm">
					  <div class="form-group">
					  	<input type="hidden" name="id">
					    <label>角色名</label>
					    <input type="email" class="form-control" id="rolename_input" name="name">
					  </div>
					</form>	
				
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateRoleAddBtn">修改</button>
				</div>
			</div>
		</div>
	</div>


	<!-- Modal,权限维护模态框-->
	<div class="modal fade" id="rolePermissionModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">权限维护</h4>
				</div>
				<div class="modal-body">
						<ul id="treeDemo" class="ztree"></ul>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateRolePermissionBtn">修改</button>
				</div>
			</div>
		</div>
	</div>

	<%@include file="/include/common-js.jsp" %>
	
	<!-- 分页带条件查询  -->
	<script type="text/javascript">
		var globalPn = 1;
		var globalPs = 3;
		var globalCondition = "";
		var globalTotal = 0;

		//1、ajax不用管页面数据回显；跨页面数据共享；
		
		$(function(){
			//页面加载完成
			//1、获取所有数据；三个参数分别是页码，每页大小，查询条件
			getAllRoles(globalPn,globalPs,globalCondition);
	
		});
		
		
		//1、发送请求获取所有数据
		function getAllRoles(pn,ps,condition){
			/*
			1、$.get(url,function(data){data});发送get请求
			2、$.post(url,{name:"zs",age:"18"},function(data){data});发送post请求
			3、$.ajax({});   {url:"xxx",type:"post",success:function(data){}}可以定制请求的所有
			*/
			//debugger;
			//1、ajax的两大步，1）、找服务器要数据  2）、拿到数据以后想方设法显示ok
			$.get("${ctx}/role/list?pn="+pn+"&ps="+ps+"&condition="+condition,function(data){
				//1、构造表格的内容
				buildTableContent(data.list);
				//2、构造页面的分页条
				buildTablePageInfo(data);
			});

		}

		//1、构造表格的内容
		function buildTableContent(data){
			//1、清空数据
			$("#content").empty();
			
			$.each(data,function(){
				var id = this.id;
				var name = this.name;
				//1、准备好多按钮的单元格内容,复杂元素创建出来，手动修改里面的内容
				var btnTd = $("<td></td>");
				btnTd.append('<button type="button" rid="'+id+'" class="btn btn-success btn-xs rolePermissonAssignBtn"><i class=" glyphicon glyphicon-check"></i></button> ');
				btnTd.append('<button type="button" rid="'+id+'" class="btn btn-primary btn-xs roleItemUpdateBtn"><i class=" glyphicon glyphicon-pencil"></i></button> ');
				btnTd.append('<button type="button" rid="'+id+'" class="btn btn-danger btn-xs roleItemDeleteBtn"><i class="  glyphicon glyphicon-remove"></i></button> ');
				
				
				//2、每遍历一个数据就是一行tr，tr里面太多，拼串不好做；
				//var tr = '<tr><td>'+id+'</td><td><input type="checkbox"></td><td>'+name+'</td><td></td></tr> ';
				var tr = $("<tr></tr>");
				tr.append("<td>" + id + "</td>")
				  .append("<td><input rid='"+id+"' type='checkbox' class='checkItem'></td>")
				  .append("<td>"+name+"</td>")
				  .append(btnTd)
				  .appendTo($("#content"));
				//a.append(b); 给a里面放一个b；
				//a.appendTo(b);把a放到b里面

				//$("<tr></tr>")：$(html字符串)；创建出这个对象
			})
		}

		//2、构造页面的分页条
		function buildTablePageInfo(data) {
			$("#pageInfoBar").empty();
			globalTotal = data.pages;
			//0、首页、上一页
			var first = "<li tonum='1'><a>首页</a></li>";
			var prev = "<li tonum='"+data.prePage+"'><a>上一页</a></li>";
			$("#pageInfoBar").append(first);
			$("#pageInfoBar").append(prev);

			//1、显示连续分页
			$.each(data.navigatepageNums,function() {
				//2、判断是否是当前页
				var li = "";
				if (data.pageNum == this) {
					li = '<li tonum="'+this+'" class="active"><a>'+ this+ ' <span class="sr-only">(current)</span></a></li>';
				} else {
					li = '<li tonum="'+this+'"><a>' + this+ '</a></li>';
				}
				$("#pageInfoBar").append(li);
			});

			//2、下一页、末页
			var next = "<li tonum='"+data.nextPage+"'><a>下一页</a></li>";
			var last = "<li tonum='"+data.lastPage+"'><a>末页</a></li>";
			$("#pageInfoBar").append(next);
			$("#pageInfoBar").append(last);

		}

		//3、给分页条的每个按钮绑定单击事件，跳转到指定位置
		//3.1）、如何给未来的元素绑事件；
		//$(已经存在父元素).on("click","目标元素选择器",function(){//回到函数})
		$("#pageInfoBar").on("click","li",function(){
			//console.log(this);
			//$(this).prop()：获取和设置原生属性的值；
			//1、只要是操作原生属性。都用prop()
			//2、只要是操作自定义属性。都用attr();
			var pn = $(this).attr("tonum");
			globalPn = pn;
			getAllRoles(pn,globalPs,globalCondition);
		});

		//4、click只能给已有的元素绑定事件；
		$("#searchBtn").click(function(){
			globalCondition= $("#searchConditionInput").val();
			getAllRoles(1,globalPs,globalCondition);
		})
	</script>
	<!-- 增删改的实现 -->
	<script type="text/javascript">
		//1、点击新增打开模态框
		$("#openRoleAddModelBtn").click(function(){
			$("#roleAddModal").modal({
				backdrop:'static',
				show:true
			});

			//
		});

		//2、点击保存，提交表单给服务器保存数据
		$("#saveRoleAddBtn").click(function(){
			var val = $("#roleAddForm input[name='name']").val();
			//1）、提交表单，href，等等这些都属于页面跳转方式，而不是ajax；ajax提交携带数据，只需要把数据组装成对象，放在参数位置即可
			//-注意：在参数的位置，写k=v&k=v或者写对象都行
			$.post("${ctx}/role/add",{"name":val},function(data){
				//
				if(data == "ok"){
					//数据库保存成功...
					layer.msg("角色保存成功");
					//关闭模态框
					$("#roleAddModal").modal('hide');
					//跳到最后一页
					globalCondition = "";
					getAllRoles(globalTotal+1000,globalPs,globalCondition);
				}
			});
			
		});

		//3、点击单个删除按钮进行删除
		$("#content").on("click",".roleItemDeleteBtn",function(){
			var name = $(this).parents("tr").find("td:eq(2)").text();
			var that = this;
			deleteRole($(that).attr("rid"));

		});

		function deleteRole(ids){
			layer.confirm("确认删除【"+ids+"】吗？",{icon: 3, title:'删除提示'},function(){
				//1、发送请求删除这些数据
				//2、js中会产生this漂移；
				$.get("${ctx}/role/delete?ids="+ids,function(data){
					layer.msg("角色删除完成...");
					if(data == "ok"){
						//回到当前页码；调用分页回到之前页面
						getAllRoles(globalPn,globalPs,globalCondition);
					}
				})
			},
			function(){
				//取消的毁掉函数
				layer.msg("角色删除已经取消....");
			}
			);
		}

		//4、多个删除
		$("#allCheckBtn").click(function(){
			$(".checkItem").prop("checked",$(this).prop("checked"));
		});

		$("#content").on("click",".checkItem",function(){
			$("#allCheckBtn").prop("checked",$(".checkItem:checked").length==$(".checkItem").length)
		});
		$("#deleteAllRoleBtn").click(function(){
			var ids = "";
			$.each($(".checkItem:checked"),function(){
				ids += $(this).attr("rid")+",";
			});
			deleteRole(ids);
		});

	</script>
	<!-- 修改的实现 -->
	<script type="text/javascript">
		//1、给修改按钮绑定单击事件，打开模态框进行回显
		$("#content").on("click",".roleItemUpdateBtn",function(){
			var rid = $(this).attr("rid");
			//2、查出当前被点击的role的详细信息，并在模态框中显示
			$.get("${ctx}/role/get?id="+rid,function(data){
				//3、获取到data数据；
				$("#roleUpdateForm input[name='name']").val(data.name);
				$("#roleUpdateForm input[name='id']").val(data.id);
				
				$("#roleUpdateModal").modal({
					backdrop:'static',
					show:true
				});
			})
		});
		//2、点击修改，发送请求数据进行修改
		$("#updateRoleAddBtn").click(function(){
			var name = $("#roleUpdateForm input[name='name']").val();
			var id = $("#roleUpdateForm input[name='id']").val();
			//1、给服务器发送修改请求
			$.post("${ctx}/role/update",{"id":id,"name":name},function(data){
				layer.msg(data);
				$("#roleUpdateModal").modal("hide");
				getAllRoles(globalPn,globalPs,globalCondition);
			})
		})
	</script>
	<!-- 给角色权限树的实现 -->
	<script type="text/javascript">
		//1、页面刷新进来就查出所有的权限，并把权限树展示好。只不过模态框没打开，但是权限树的数据是ok；
	 	var ztreeObj = null;
		$(function(){
			initPermissionTree();
		});
		//初始化权限树
	    function initPermissionTree(){
	    	//1、导入js和css
	        //2、准备好树形节点；
	        //3、显示在ul里面
	    	var setting = {
	    			data: {
	    				simpleData: {
	    					enable: true,
	    					pIdKey: "pid",
	    				},
	    				key:{
	    					url: "haha",
	    					name: "title"
	        			}
	    			},
	    			view:{
	    				addDiyDom: showMyIcon//显示自定义图标
	        		},
	        		check: {
	    				enable: true
	    			}
	    	};
	    	var zNodes = null;
	    	//4、找服务器要到真正的permission节点数据
	    	//ajax异步无刷新；
	    	//异步？下面的方法不用等待异步方法执行完成
	    	//同步？下面的方法必须等待上面的方法执行完成
	    	$.get("${ctx}/permission/list",function(data){
	    		zNodes = data;
	    		zNodes.push({id:0,title:"系统所有权限"});//给数组添加一个数据
	    		//5、初始化树
	    		ztreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);

	    		//6、将整个ztree展开
	    		ztreeObj.expandAll(true);
	    		
	        });
	    	//4、将zNode展示在id为treeDemo的ul里面
	    	
	    }
	  //自定义显示图标的回调函数，要显示每个节点的时候都会被调用
		function showMyIcon(treeId, treeNode){
			//treeId:对应 zTree 的 treeId，便于用户操控
			//treeNode:需要显示自定义控件的节点 JSON 数据对象
			//console.log(treeId);//整个树ul的id
			//console.log(treeNode);//当前节点；
			//treeDemo_1_ico：是图标的元素的id
			//treeDemo_1_span：是显示文本的元素的id
			var tId = treeNode.tId;
			var iconSpan = $("<span class='"+treeNode.icon+"'></span>");
			$("#"+tId+"_ico").removeClass();//清除默认样式
			$("#"+tId+"_ico").after(iconSpan);//使用自己的图标span;
		}

		

		
	</script>
		
	<!-- 角色权限维护功能 -->
	<script type="text/javascript">
		var globalRid = ""; 
		//========================以上是权限树===================
		$("#content").on("click",".rolePermissonAssignBtn",function(){
			globalRid = $(this).attr("rid");
			//0、查出这个角色之前的permisson信息，回显在权限树中；
			$.get("${ctx}/permission/role/get?rid="+globalRid,function(data){
				//ztreeObj.checkNode(nodes[i], true, true);
				//清空掉树的上次勾选状态
				ztreeObj.checkAllNodes(false);
				$.each(data,function(){
					//知道了权限id；按照权限id，去ztreeObj中找到这个id对应得treeNode；
					var treeNode = ztreeObj.getNodeByParam("id", this.id, null);
					//勾选数据库需要回显的数据
					ztreeObj.checkNode(treeNode, true, false);
				})
				//ztreeObj.checkNode(,true,false);
			});
			//1、打开模态框
			$("#rolePermissionModal").modal({
				show:true,
				backdrop:'static'
			})
		});
		$("#updateRolePermissionBtn").click(function(){
			//1、准备 上一次点击的角色id;
			//2、准备选中的权限id;
			var permissionIds = "";
			$.each(ztreeObj.getCheckedNodes(true),function(){
				if(this.id != 0){
					permissionIds += this.id +",";
				}
			});
			console.log("角色的id："+globalRid+"===>修改的权限的id："+permissionIds);
			//@RequestParam("rid")Integer rid,@RequestParam("permissionIds")String permissionIds
			$.post("${ctx}/permission/role/assign",{"rid":globalRid,"permissionIds":permissionIds},function(data){
				if(data == "ok"){
					layer.msg("分配完成....");
					$("#rolePermissionModal").modal("hide");
				}
			});
			
		});
	</script>
</body>
</html>
