(function () {
	'use strict';

	var app= angular.module('sgpfc');

	app.controller('editOfferController',['$scope', '$http', '$location', '$cookies', '$routeParams', 'postService',
	                                        function ($scope, $http, $location, $cookies, $routeParams, postService) {

		
		$scope.noEditOffer= false;
		$scope.offerEdited= false;
		$scope.noTeacher= false;
		$scope.noTeacherOrLogged= false;
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
		
		var offerId =$routeParams.offerId;
		
		$http.get('http://localhost:8080/proyectoTFM/rest/offers/'+ offerId).then(function(offer) {
		    $scope.offer = offer.data;
		    $scope.htmlVariable = offer.data.offerDescription
		    $http.get('http://localhost:8080/proyectoTFM/rest/offers/users'+'?offerId='+offerId).then(function(users) {
				offer.data.offerRegistrationList = users.data;
				$http.get('http://localhost:8080/proyectoTFM/rest/offers/subcategories'+'?offerId='+offerId).then(function(subcategories) {
					offer.data.offerSubcategoryList = subcategories.data
					subcategories.data.forEach(function(subcategory){
						$scope.checkModel[subcategory.subcategoryId]= true;
					})
				}, function(err) {
					console.error('ERR', err);
					// err.status will contain the status code
				})
			}, function(err) {
				console.error('ERR', err);
				// err.status will contain the status code
			})
		    $scope.offer = offer.data;
		  }, function(err) {
		    console.error('ERR', err);
		    // err.status will contain the status code
		})
		
		
		
		

		
		
		$scope.editOffer = function() {
			
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
				postService.putOffer($scope.offer.offerId, $scope.offer.offerName, $scope.offer.offerDescription, $scope.offer.offerImage, $scope.offer.offerWithLimit, $scope.offer.offerTimeLimit, $scope.offer.offerPdf,
						function(offer){
					$scope.offerEditted = true;
					$scope.noEditOffer = false;
					//$scope.posts.splice(0,0,offer);
				},
				function(){
					$scope.noEditOffer = true;
					$scope.offerEdited = false; 
				}
				);
			}
		};
	}]);
}());