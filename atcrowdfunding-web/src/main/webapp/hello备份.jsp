<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- 四大域对象
1、page：只是当前页面有效
2、request：当次请求有效
3、session：跨页面共享数据；
4、application：整个项目都用；

 -->

<!-- 相对路径转发到这个页面就会出问题，推荐都使用绝对路径 -->
<!-- 在页面上的绝对路径，/是代表以服务器开头，会丢失项目名。我们需要加上项目名 -->
<!-- 1、导入样式文件 .css -->
<link rel="stylesheet"
	href="${ctx }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="${ctx }/static/jquery/jquery-2.1.1.min.js"></script>
<!-- 2、导入js文件 -->
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<style type="text/css">
.container  div div{
	background-color: red;
	border: 1px solid;
}
</style>
</head>
<body>

	<div class="container">
	
		<div class="row">
			<div class="col-sm-4 col-lg-1">
				${ctx }
			</div>
			<div class="col-sm-4 col-lg-1">
				1111
			</div>
			<div class="col-sm-4 col-lg-1">
				1111
			</div>
			<div class="col-sm-4 col-lg-1">
				1111
			</div>
			<div class="col-sm-4 col-lg-1">
				1111
			</div>
			<div class="col-sm-4 col-lg-1">
				1111
			</div>
		</div>
		
		<!-- <a href="getAdmin?id=1">查询1号admin</a>

		<button class="btn btn-primary">哈哈</button>


		<table class="table table-striped table-bordered table-hover">
			<tr>
				<th>姓名</th>
				<th>age</th>
				<th>gender</th>
			</tr>

			<tr>
				<td class="success">zhangsan</td>
				<td>18 <span class="glyphicon glyphicon-cd"></span></td>
				<td>nv</td>
			</tr>
			<tr>
				<td>lisi</td>
				<td>20</td>
				<td>

					<div class="dropdown">
						<button class="btn btn-default dropdown-toggle" type="button"
							id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="true">
							Dropdown <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li><a href="#">Separated link</a></li>
						</ul>
					</div>
				</td>
			</tr>
			<tr class="danger">
				<td>laowang</td>
				<td>18</td>
				<td>nv</td>
			</tr>
		</table> -->


	</div>

</body>
</html>