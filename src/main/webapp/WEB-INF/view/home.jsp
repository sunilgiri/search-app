<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head lang="en">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>QB Search</title>
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.css" />" type="text/css" />
<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />" type="text/css" />
<!-- <link rel="stylesheet" href="<c:url value="/resources/css/ng-table.css" />"	type="text/css" /> -->
<script src="<c:url value="/resources/lib/angular.js"/>"></script>
<script src="<c:url value="/resources/lib/jquery-1.9.1.js" />"></script>
<!-- <script src="<c:url value="/resources/lib/ng-table.src.js"/>" ></script>  -->
<script src="<c:url value="/resources/js/searchApp.js"/>"></script>
<script src="<c:url value="/resources/js/pdfSearchService.js"/>"></script>

</head>
<body ng-app="searchApp">
	<div ng-controller="SearchController">
		<div class="search-div">
			<h1>PDF Search</h1>
			<form ng-submit="submitSearch()">
				<input type="text" class="search-text" placeholder="Enter search term" ng-model="searchKeyword"> 
					<input type="submit" id="search-btn" value="Search" class="btn btn-primary">
						<span class="help-block">{{result_text}}</span>
			</form>
		</div>
		<div class="results-div">
			<table class="table">
				<tr>
					<th>Title</th>
					<th>Author</th>
					<th>Download</th>
				</tr>
				<tr ng-repeat="result in results">
					<td>{{result.title}}</td>
					<td>{{result.author}}</td>
					<td title=" "><a href="{{result.path}}" target="_blank"
						class="btn btn-success">Download</a></td>
				</tr>
			</table>
		</div>

	</div>
</body>

</html>
