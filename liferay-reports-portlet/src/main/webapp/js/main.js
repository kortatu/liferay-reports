var app = angular.module('myApp', []);
app.controller('myCtrl', function($scope,$http) {
    $scope.firstName= "Alvaro";
    $scope.lastName= "Gonzalez";
    $scope.showView = false;
    $scope.reload = function() {
        $http.get("http://127.0.0.1:8081/pageViews?size=200")
        .success(function(response) {$scope.pageViews = response._embedded.pageViews;});
    };
    $scope.reload();
    $scope.show = function(view) {
        $scope.view = view;
        $scope.showView = true;
        /*$http.get(link)
            .success(function(response) {
                $scope.view = response;
                $scope.showView = true;
            });        
        */
    };
    $scope.closeView = function() {
      $scope.showView = false;
      $scope.view = null;
    };
    
});