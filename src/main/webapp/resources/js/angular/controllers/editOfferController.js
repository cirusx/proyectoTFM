(function () {
	'use strict';

	var app= angular.module('sgpfc');

	app.controller('editOfferController',['$scope', '$http', '$location', '$cookies', '$routeParams',
	                                      function ($scope, $http, $location, $cookies, $routeParams) {

		var quarter1 = "1579413600000";
		var quarter2 = "1589864400000";
		var offerId =$routeParams.offerId;
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

		$http.get('http://localhost:8080/proyectoTFM/rest/offers/'+ offerId).then(function(offer) {
			if (offer.data.offerId==null) {
				$location.path("/");
			}else {
				$scope.offer = offer.data;
				$scope.htmlVariable = offer.data.offerDescription
				if (offer.data.offerTimeLimit == quarter1){
					$scope.limit = '1';
				}else if(offer.data.offerTimeLimit == quarter2){
					$scope.limit = '2';
				}else{
					$scope.limit = '0';
				}
				
				$http.get('http://localhost:8080/proyectoTFM/rest/offers/users'+'?offerId='+offerId).then(function(users) {
					offer.data.offerRegistrationList = users.data;
				}, function(err) {
					console.error('ERR', err);
					// err.status will contain the status code
				})
				
				$http.get('http://localhost:8080/proyectoTFM/rest/offers/subcategories'+'?offerId='+offerId).then(function(subcategoriesSelected) {
						offer.data.offerSubcategoryList = subcategoriesSelected.data
						subcategoriesSelected.data.forEach(function(subcategorySelected){
							$scope.checkModel[subcategorySelected.subcategoryId]= true;
						})
					}, function(err) {
						console.error('ERR', err);
						// err.status will contain the status code
					})
				
				$scope.offer = offer.data;
			}
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
			if($cookies.get('user') == $scope.offer.teacher.email) {
				$scope.incorrectTeacher = false;
			} else {
				$scope.incorrectTeacher = true;
			}
			if(login & rol & !$scope.incorrectTeacher) {
				if($scope.limit == "0") {
					$scope.offer.offerWithLimit = $scope.limit;
				}else {
					$scope.offer.offerTimeLimit = $scope.limit;
				}
				$scope.offer.teacher = $scope.offer.teacher.userId;
				$scope.offer.offerDescription = $scope.htmlVariable;
				$scope.offer.offerSubcategoryList = $scope.checkResults;
				if ($scope.deleteOfferPdf == true) {
					$scope.offerPdfFile = true;
				}
				var fd = new FormData();
				fd.append('id', $scope.offer.offerId);
				fd.append('name', $scope.offer.offerName);
				fd.append('tinydescription', $scope.offer.offerTinyDescription);
				fd.append('description', $scope.offer.offerDescription);
				if ($scope.offerImageFile != undefined) {
					fd.append('image', $scope.offerImageFile);
				}
				fd.append('withLimit', $scope.offer.offerWithLimit);
				fd.append('timeLimit', $scope.offer.offerTimeLimit);
				if ($scope.offerPdfFile != undefined) {
					fd.append('pdf', $scope.offerPdfFile);
				}
				fd.append('teacher', $scope.offer.teacher);
				fd.append('subcategories', $scope.offer.offerSubcategoryList);
				$http.put('http://localhost:8080/proyectoTFM/rest/offers/'+ offerId, fd, {
					transformRequest: angular.identity,
					headers: {'Content-Type': undefined}
				}).then(function(offer) {
					$scope.offerEdited = true;
					$scope.noEditOffer = false;
					$scope.checkResults = [];
					$scope.checkModel = {};
					$('#confirmEditOfferModal').modal('show');
					$('#confirmEditOfferModal').appendTo("body");
					$location.path("/users/myoffers");
					
				}, function(err) {
					console.error('ERR', err);
					$scope.noEditOffer = true;
					$scope.offerEdited = false; 
				})
			}
		};
	}]);
}());