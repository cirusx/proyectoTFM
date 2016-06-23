(function () {
	'use strict';

	var app= angular.module('sgpfc');

	app.controller('createOfferController',['$scope', '$http', '$location', '$cookies',
	                                        function ($scope, $http, $location, $cookies) {

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
		
		$http.get('http://localhost:8080/proyectoTFM/rest/categories').then(function(categories) {
			categories.data.forEach(function(category){
				$http.get('http://localhost:8080/proyectoTFM/rest/categories/subcategories'+'?categoryId='+category.categoryId).then(function(subcategories) {
					category.subcategories = subcategories.data
				}, function(err) {
					console.error('ERR', err);  
				})
			})
			$scope.categories = categories.data;
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
				
				var fd = new FormData();
				fd.append('name', $scope.offer.offerName);
				fd.append('tinydescription', $scope.offer.offerTinyDescription);
				fd.append('description', $scope.offer.offerDescription);
				fd.append('subcategories', $scope.offer.offerSubcategoryList);
				if ($scope.offer.offerImage != undefined) {
					fd.append('image', $scope.offer.offerImage);
				}
				fd.append('withLimit', $scope.offer.offerWithLimit);
				fd.append('timeLimit', $scope.offer.offerTimeLimit);
				if ($scope.offer.offerPdf != undefined) {
					fd.append('pdf', $scope.offer.offerPdf);
				}
				$http.post('http://localhost:8080/proyectoTFM/rest/offers/offer', fd, {
					transformRequest: angular.identity,
					headers: {'Content-Type': undefined}
				}).then(function(offer) {
					$scope.offerCreated = true;
					$scope.noCreateOffer = false;
					delete $scope.offer;
					$('#photofile').fileinput('clear');
					$('#filepdf').fileinput('clear');
					$scope.htmlVariable = "";
					$scope.checkResults = [];
					$scope.checkModel = {};
					angular.element(document.querySelectorAll("#MyOfferSubcategories")).removeClass("active");		   			
					$scope.createofferform.$setUntouched();
				}, function(err) {
					console.error('ERR', err);  
					$scope.noCreateOffer = true;
					$scope.offerCreated = false;
				})
			}
		};
	}]);
}());