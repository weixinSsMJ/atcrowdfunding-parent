<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	<link rel="stylesheet" href="${ctx}/static/css/doc.min.css">
	<link rel="stylesheet" href="${ctx}/static/ztree/zTreeStyle.css">
	<style>
		.tree li {
			list-style-type: none;
			cursor: pointer;
		}
	</style>
</head>

<body>
<%pageContext.setAttribute("title", "项目标签"); %>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<div>
				<a class="navbar-brand" style="font-size: 32px;"
				   href="${ctx}/static#">众筹平台 - ${title }</a>
			</div>
		</div>

		<!-- 顶部栏 -->
		<%@include file="/include/top-nav.jsp" %>

	</div>
</nav>

<div class="container-fluid">
	<div class="row">

		<!-- 侧边栏 -->
		<%@include file="/include/side-bar.jsp" %>

		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

			<div class="panel panel-default">
				<div class="panel-heading">
					<i class="glyphicon glyphicon-th-list"></i> 项目标签列表
				</div>
				<div class="panel-body">
					<ul id="treeDemo" class="ztree"></ul>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 添加标签模态框 -->
<div class="modal fade" id="addTagModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">添加 标签</h4>
			</div>
			<div class="modal-body">


				<form id = "addForm">

					<input type= "hidden"  name = "pid"/>
					<div class="form-group">
						<label >标签名称</label>
						<input type="text" class="form-control" name ="name" placeholder="标签名称">
					</div>

				</form>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" id = "addBtn" class="btn btn-primary">保存</button>
			</div>
		</div>
	</div>
</div>

<!-- 修改标签模态框 -->
<div class="modal fade" id="updateTagModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">修改 标签</h4>
			</div>
			<div class="modal-body">

				<form id = "updateForm">

					<input type= "hidden"  name = "id"/>
					<div class="form-group">
						<label >标签名称</label>
						<input type="text" class="form-control" name ="updateName" placeholder="标签名称">
					</div>

				</form>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id = "updateBtn">保存</button>
			</div>
		</div>
	</div>
</div>


<%@ include file="/include/common-js.jsp" %>
<script type="text/javascript">

	var treeObj = null;

	//文档加载完成初始化树
	$(function(){

		initTree();

	});

	function initTree(){

		var setting = {
			data: {
				simpleData: {
					enable: true,
					pIdKey: "pid"
				}
			},
			view: {
				addDiyDom: showIcon,
				addHoverDom: showBtn,
				removeHoverDom:removeBtn
			}
		};

		var zNodes =null;


		//发送ajax请求查询树数据
		$.ajax({

			url:"${ctx}/tag/list",

			type:"get",

			success:function(data){

				zNodes = data;

				zNodes.push({id:0,name:"众筹平台项目标签"});

				treeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);

				treeObj.expandAll(true);


			}

		})



	}

	/*显示图标  */
	function showIcon(treeId, treeNode){

		/* glyphicon glyphicon-th-large */

		if(treeNode.pid == 0 || treeNode.pid == null){

			$("#" + treeNode.tId + "_ico").removeClass();

			var span = $("<span class = 'glyphicon glyphicon-th-large'></span>");

			$("#" + treeNode.tId + "_ico").after(span);

		}else{

			$("#" + treeNode.tId + "_ico").removeClass();

			var span = $("<span class = 'glyphicon glyphicon-tag'></span>");

			$("#" + treeNode.tId + "_ico").after(span);

		}

	}

	/* 悬浮显示图标 */
	function showBtn(treeId, treeNode){

		var tId = treeNode.tId;

		var addBtn = $("<a  class = 'btn btn-info dropdown-toggle btn-xs' title = '添加' style='margin-left:10px;padding-top:0px;'><i menuid ='"+treeNode.id +"' class = 'fa fa-fw fa-plus rbg '></i></a> ");

		var updateBtn = $("<a  class = 'btn btn-info dropdown-toggle btn-xs' title = '修改' style='margin-left:10px;padding-top:0px;'><i menuid ='"+treeNode.id +"' class = 'fa fa-fw fa-edit rbg '></i></a> ");

		var delBtn = $("<a  class = 'btn btn-info dropdown-toggle btn-xs' title = '删除' style='margin-left:10px;padding-top:0px;'><i menuid ='"+treeNode.id +"' class = 'fa fa-fw fa-times rbg '></i></a> ");

		var btnGroup = $("<span id='"+ tId +"_btnGroup' class ='crudBtnGroup'></span>");

		/*针对不同情况进行判断  */
		var length = 0;
		try{
			length = treeNode.children.length;
		}catch(e){
			length = 0;
		}

		if(treeNode.pid == 0 && length > 0){

			//父菜单有子元素  修改 添加
			btnGroup.append(addBtn).append(updateBtn);

		}else if(treeNode.pid == 0 && length == 0){

			//父菜单没有子元素 修改  添加 删除
			btnGroup.append(addBtn).append(updateBtn).append(delBtn);

		}else if(treeNode.pid > 0){

			//子菜单  删除 修改  分配菜单
			btnGroup.append(updateBtn).append(delBtn);
		}else if(treeNode.id == 0){

			btnGroup.append(addBtn);

		}


		if($("#" + tId +"_a").nextAll("#" + tId +"_btnGroup").length == 0){

			$("#"+tId+"_a").after(btnGroup);

		}

	}


	//移除按扭组
	function removeBtn(treeId,treeNode){

		var tId = treeNode.tId;

		$("#" + tId +"_a").nextAll("#" + tId +"_btnGroup").remove();
	}


	//给所有按钮添加点击事件
	$("#treeDemo").on("click",".crudBtnGroup",function(e){

		var target = e.target;

		if($(target).hasClass("fa fa-fw fa-plus rbg ")){

			//给pid赋值
			var pid = $(target).attr("menuid");
			$("input[name ='pid']").val(pid);

			addTag();


		}

		if($(target).hasClass("fa fa-fw fa-edit rbg ")){

			var id = $(target).attr("menuid");

			$("input[name ='id']").val(id);

			updateTag(id);

		}

		if($(target).hasClass("fa fa-fw fa-times rbg ")){
			var id = $(target).attr("menuid");

			var name = $(target).parents("li").children("a").eq(0).children("span").eq(2).text();
			delTag(id,name);
		}

	});

	//添加方法
	function addTag(){

		$("input[name = 'name']").val("");

		//打开模态框
		$("#addTagModal").modal({
			show:'true',
			backdrop:'static'
		})

	}

	//保存标签
	$("#addBtn").click(function(){

		var pid = $("input[name ='pid']").val();
		var name = $("input[name = 'name']").val();

		if(name == ""){

			layer.msg("标签名称不能为空");
		}else{

			$.ajax({

				url:"${ctx}/tag/save",

				data:{"pid":pid,"name":name},

				type:"post",

				success:function(data){

					if(data == "ok"){

						layer.msg("添加成功");

						//关闭模态框
						$("#addTagModal").modal('hide');

						//初始化树
						initTree();
					}else{

						layer.msg("添加失败");
					}
				}

			})

		}

	});


	//修改
	function updateTag(id){

		//回显数据
		$.ajax({

			url:"${ctx}/tag/get",

			data:{"id":id},

			type:"get",

			success:function(data){

				$("input[name = 'updateName']").val(data.name);
			}

		});

		//打开模态框
		$("#updateTagModal").modal({
			show:'true',
			backdrop:'static'
		})


	}


	//修改标签
	$("#updateBtn").click(function(){

		var name = $("input[name = 'updateName']").val();

		var id = $("input[name ='id']").val();


		if(name == ""){

			layer.msg("标签名字不能为空");

		}else{

			$.ajax({

				url:"${ctx}/tag/update",

				data:{"id":id,"name":name},

				type:"post",

				success:function(data){

					if(data == "ok"){

						layer.msg("修改成功");

						//关闭模态框
						$("#updateTagModal").modal('hide');

						//初始化树
						initTree();

					}else{

						layer.msg("修改失败");

					}

				}


			})

		}

	});

	function delTag(id,name){



		layer.confirm("你确定要删除 " + name + " 这个标签吗?",{icon:5,title:"提示"},function(){

			$.ajax({

				url:"${ctx}/tag/delete",

				data:{"id":id},

				type:"get",

				success: function(data){


					if(data == "ok"){

						layer.msg("删除成功");

						initTree();

					}else{

						layer.msg("删除失败");

					}
				}


			})


		},function(){

			layer.msg("您刚才点了取消");

		})


	}


</script>

</body>
</html>
