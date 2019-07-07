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

	<link rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctx}/static/css/font-awesome.min.css">
	<link rel="stylesheet" href="${ctx}/static/css/main.css">
	<style>
		.tree li {
			list-style-type: none;
			cursor:pointer;
		}
		table tbody tr:nth-child(odd){background:#F4F4F4;}
		table tbody td:nth-child(even){color:#C00;}
	</style>
</head>

<body>
<%pageContext.setAttribute("title", "资质维护"); %>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<div><a class="navbar-brand" style="font-size:32px;" href="${ctx}/static#">众筹平台 - ${title }</a></div>
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
					<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
				</div>
				<div class="panel-body">
					<form class="form-inline" role="form" style="float:left;">
						<div class="form-group has-feedback">
							<div class="input-group">
								<div class="input-group-addon">查询条件</div>
								<input id = "queryText" class="form-control has-success" type="text" placeholder="请输入查询条件">
							</div>
						</div>
						<button type="button" id = "serach" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
					</form>
					<button type="button" id = "delBatch" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
					<button id = "addCert" type="button" class="btn btn-primary" style="float:right;" ><i class="glyphicon glyphicon-plus"></i> 新增</button>
					<br>
					<hr style="clear:both;">
					<div class="table-responsive">
						<table class="table  table-bordered">
							<thead>
							<tr >
								<th width="30">#</th>
								<th width="30"><input id = "checkAll" type="checkbox"></th>
								<th>名称</th>
								<th width="100">操作</th>
							</tr>
							</thead>
							<tbody>


							</tbody>

							<tfoot>
							<tr>
								<td colspan="6" align="center">
									<ul id ="ul" class="pagination">

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

<!--新增模态框  -->
<!-- Modal -->
<div class="modal fade" id="addModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">新增 证书</h4>
			</div>
			<div class="modal-body">

				<form>
					<div class="form-group">
						<label for="exampleInputEmail1">证书名称</label>
						<input type="text" class="form-control" id="addText" placeholder="name">
					</div>
				</form>


			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" id = "saveButton" class="btn btn-primary">新增</button>
			</div>
		</div>
	</div>
</div>


<!--修改模态框  -->
<!-- Modal -->
<div class="modal fade" id="updateModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title">证书 修改</h4>
			</div>
			<div class="modal-body">

				<form>

					<div class="form-group">
						<input type ="hidden" name="id" id = "idText" >
						<label for="exampleInputEmail1">证书名称</label>
						<input type="text" class="form-control" id="updateText" placeholder="name">
					</div>
				</form>


			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" id = "updateButton" class="btn btn-primary">修改</button>
			</div>
		</div>
	</div>
</div>


<%@ include file="/include/common-js.jsp" %>


<!--分页  -->
<script type="text/javascript">

	var pn = "1";
	var condition ="";
	var totalPage = 1;
	var pageNum;
	//文档加载完成后发送ajax请求
	$(function() {

		getData(pn,condition);

	});

	function getData(pn,condition) {

		$.ajax({

			url : "${ctx}/cert/get?pn=" +pn + "&condition=" +condition,
			type : "get",
			dataType : "json",
			success : function(data) {
				//解析表格
				getTable(data);
				//解析分页栏
				getPageBar(data);
			}

		});
	}


	function getTable(data){

		//清空表格
		$("tbody").empty();

		$.each(data.list,function(){

			var $mdbtn = $("<button type='button' title = '修改' id ='modify' name = "+this.id+" class='btn btn-primary btn-xs mdBtn'><i class=' glyphicon glyphicon-pencil'></i></button> ");
			var $delbtn = $('<button id = "delBtn" title = "删除"  type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button> ');
			var $tr = $("<tr></tr>");
			var $id = $("<td>"+this.id+"</td>");
			var $input = $("<td><input class = 'checkItem' type='checkbox'></td>");
			var $tname = $("<td>"+this.name+"</td>");

			var $btnTd = $("<td></td>").append($mdbtn).append($delbtn);

			$tr.append($id).append($input).append($tname).append($btnTd).appendTo($("tbody"));

		})

	}

	function getPageBar(data){

		totalPage = data.pages;
		pageNum = data.pageNum;
		//清空导航栏
		$("#ul").empty();
		var $ul = $("#ul");

		var $first = $("<li num = "+1+"><a>首页</a></li>");
		var $pre = $("<li num = "+data.prePage+"><a>上一页</a></li>");

		var $li;

		$ul.append($first).append($pre);
		$.each(data.navigatepageNums,function(){


			if(data.pageNum == this){
				//当前页高亮显示
				$li = $("<li  num ="+this+" class='active'><a >"+this+"<span class='sr-only'>(current)</span></a></li>");
				$ul.append($li);
			}else{
				$li = $("<li num = "+this+"><a>"+this+"</a></li>");
				$ul.append($li);
			}




		});

		var $next = $("<li num = "+data.nextPage+"><a>下一页</a></li>");
		var $last = $("<li num = "+data.pages+"><a>末页</a></li>");

		$ul.append($next);
		$ul.append($last);
	}

	//给每一个页码添加点击事件
	$("#ul").on("click","li",function(){

		//拿到页码
		pn = $(this).attr("num");
		/* //拿到condition
        condition = $("#queryText").val(); */

		getData(pn,condition);
	});

	//给查询按钮添加点击事件
	$("#serach").click(function(){

		condition = $("#queryText").val();

		getData(1,condition);

	})


</script>

<!--证书增删改  -->
<script type="text/javascript">

	$("#addCert").click(function(){

		$("#addText").val("");

		$('#addModel').modal({
			backdrop:'static',
			show:'true'
		})

	});

	//点击保存按钮发送ajax请求保存角色信息
	$("#saveButton").click(function(){

		//获取角色内容
		var name = $("#addText").val();


		$.ajax({
			url:"${ctx}/cert/add",

			data:{"name" : name},

			success:function(data){



				if(data == "ok"){
					//新增成功
					layer.msg("新增证书完成");
					//关闭模态框
					$('#addModel').modal('hide');
					//跳到最后一页
					//拿到condition
					condition = "";

					getData(totalPage + 1000,condition);

				}

			}

		});

	});


	//点击删除按钮删除单个证书
	$("tbody").on("click","#delBtn",function(){

		//获取id
		var id = $(this).parents("tr").children("td").first().text();
		var name = $(this).parents("tr").children("td").eq(2).text();

		layer.confirm("你确定要删除 " + name +" 这个证书吗?",{icon:"5",title:"提示"},function(){

					//确定
					//发送ajax请求删除
					$.ajax({

						url:"${ctx}/cert/del",

						data:{"id":id},

						success:function(data){

							if(data == "ok"){

								layer.msg("删除成功");
								//回到本页

								getData(pageNum,condition);
							}

						}
					})

				},
				function(){

					//取消
					layer.msg("用户点了取消");
				})


	});


	//全选与全不选
	$("#checkAll").click(function(){

		//获取选中状态
		var checked = $("#checkAll").prop("checked");

		//给其他选中框赋值
		$(".checkItem").prop("checked",checked);

	});

	//子勾选全选
	$("tbody").on("click",".checkItem",function(){



		$("#checkAll").prop("checked",$(".checkItem").length == $(".checkItem:checked").length);

	});

	//批量删除
	$("#delBatch").click(function(){

		var ids = "";
		var name ="";

		//获取被选中的长度
		var len = $(".checkItem:checked").length;

		if(len == 0){
			layer.msg("请选择要删除的证书");
		}else{
			//勾选了要删除的角色
			//获取勾选中的角色id和name
			$.each($(".checkItem:checked"),function(){

				ids += $(this).parents("tr").children("td").first().text() + ",";
				name += $(this).parents("tr").children("td").eq(2).text() + " ";
			});



			layer.confirm("你确定要删除 " + name +"这些证书吗?",{icon:3,title:"提示"},function(){

						$.ajax({

							url:"${ctx}/cert/delBatch",

							data:{"ids":ids},

							success:function(data){

								if(data == "ok"){

									layer.msg("删除成功");
									getData(pageNum,condition);
								}
							}
						})

					},
					function(){
						//取消
						layer.msg("用户点了取消");

					})
			//发送ajax请求批量删除
		}

	});

	//修改

	$("tbody").on("click",".mdBtn",function(){
		//查询数据回显

		var id = $(this).parents("tr").children("td").first().text();

		$.ajax({

			url:"${ctx}/cert/getSingleCert",

			data:{"id":id},

			success:function(data){

				$("#updateText").val(data.name);

				$("#idText").val(data.id);

			}
		});

		$('#updateModel').modal({
			backdrop:'static',
			show:'true'
		})
	});

	//点击保存按钮发送ajax请求保存角色信息
	$("#updateButton").click(function(){

		//获取角色内容
		var name = $("#updateText").val();

		var id = $("#idText").val();


		if(name == ""){

			layer.msg("证书名不能为空");

		}else{


			$.ajax({
				url:"${ctx}/cert/update",

				data:{"id" : id,"name" : name},

				success:function(data){



					if(data == "ok"){
						//修改成功
						layer.msg("修改成功");
						//关闭模态框
						$('#updateModel').modal('hide');
						//跳到当前页

						getData(pageNum,condition);

					}

				}

			});

		}
	})


</script>
</body>
</html>
