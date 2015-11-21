<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<div class="container" ng-app="myApp" ng-controller="myCtrl">
  <div class="container-fluid" ng-show="showView" ng-include="'<%=request.getContextPath() %>/show_view.html'"></div>
  <div class="container-fluid" ng-hide="showView" >
	  <button ng-click="reload()" class="btn btn-success">
	    <span class="glyphicon glyphicon-refresh"></span>&nbsp;&nbsp;Reload data
	  </button>
	  <table class="table table-striped">
	  <thead>
	    <tr>
	        <th></th>
	        <th>Date</th>
	        <th>Process Time</th>
	        <th>Page</th>
	        <th>User</th>        
	    </tr>
	  </thead>
	  <tbody>
	    <tr ng-repeat="x in pageViews  | orderBy:'-viewTimestamp'">
	        <td>
	            <button class="btn" ng-click="show(x)">
	        		<span class="glyphicon glyphicon-eye-open"></span>&nbsp;&nbsp;Show
	            </button>
	        </td>
	        <td>{{ x.viewTimestamp }} </td>
	        <td>{{ x.processTime }} ms</td>
	        <td>{{ x.page.pageName }}</td>
	        <td>{{ x.viewer.userEmail }}</td>        
	    </tr>
	  </tbody>
	  </table>
  </div>
</div>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
<script src="/liferay-reports/js/main.js"></script>
