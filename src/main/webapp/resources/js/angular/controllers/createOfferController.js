(function () {
	'use strict';

	var app= angular.module('sgpfc');

	app.controller('createOfferController',['$scope', '$http', '$location', '$cookies', 'postService',
	                                        function ($scope, $http, $location, $cookies, postService) {

		$scope.limit = "0"; 
		 $scope.checkModel = {};
		  $scope.checkResults = [];
		  $scope.oneAtATime = true;
		  $scope.$watchCollection('checkModel', function () {
		    $scope.checkResults = [];
		    angular.forEach($scope.checkModel, function (value, key) {
		      if (value) {
		        $scope.checkResults.push(key);
		      }
		    });
		  });
		  $scope.status = {
		    isCustomHeaderOpen: false,
		    isFirstOpen: true,
		    isFirstDisabled: false
		  };
		var i = 0;
		 $http.get('http://localhost:8080/proyectoTFM/rest/categories').then(function(categories) {
		    	$scope.categories = categories.data;
		    	categories.data.forEach(function(category){
		    		$http.get('http://localhost:8080/proyectoTFM/rest/categories/subcategories'+'?categoryId='+category.categoryId).then(function(subcategories) {
				    	$scope.categories[i].subcategories = subcategories.data;
				    	i++;
					}, function(err) {
						console.error('ERR', err);  
					})
		    	})
		    	 
			}, function(err) {
				console.error('ERR', err);  
			})
			
		$scope.createOffer = function() {
			
			$scope.noCreateOffer = false;
			$scope.offerCreated = false;
			$scope.noTeacher = false;
			$scope.noTeacherOrLogged = false;
			var login = false;
			var rol = false;
			if ($cookies.get('user')) {
				login = true
				if ($cookies.get('rol')== "Teacher") {
					rol = true
				} else {
					$scope.noTeacher = true;
				}
			} else {
				$scope.noTeacherOrLogged= true;
			}
			if(login & rol) {
				if($scope.limit == "0") {
					$scope.offer.offerWithLimit = $scope.limit;
				}else {
					$scope.offer.offerTimeLimit = $scope.limit;
				}
		    	$scope.offer.offerSubcategoryList= $scope.checkResults;
				$scope.offer.offerDescription = $scope.htmlVariable;
				postService.postOffer($scope.offer.offerName, $scope.offer.offerTinyDescription, $scope.offer.offerDescription, $scope.offer.offerSubcategoryList, $scope.offer.offerImage, $scope.offer.offerWithLimit, $scope.offer.offerTimeLimit, $scope.offer.offerPdf,
						function(offer){
					$scope.offerCreated = true;
					$scope.noCreateOffer = false;
					//$scope.posts.splice(0,0,offer);
					delete $scope.offer;
					$('#photofile').fileinput('clear');
					$('#filepdf').fileinput('clear');
					$scope.htmlVariable = "";
					$scope.checkResults = [];
					angular.element(document.querySelectorAll("#MyOfferSubcategories")).removeClass("active");		   			
					$scope.createofferform.$setUntouched();
				},
				function(){
					$scope.noCreateOffer = true;
					$scope.offerCreated = false; 
				}
				);
			}
		};
	}]);
}());