(function () {
	'use strict';

	var app= angular.module('sgpfc');

	app.controller('editOfferController',['$scope', '$http', '$location', '$cookies', '$routeParams', 'postService',
	                                      function ($scope, $http, $location, $cookies, $routeParams, postService) {

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
				postService.putOffer($scope.offer.offerId, $scope.offer.offerName, $scope.offer.offerTinyDescription, $scope.offer.offerDescription, $scope.offerImageFile, $scope.offer.offerWithLimit, $scope.offer.offerTimeLimit, $scope.offerPdfFile, $scope.offer.teacher, $scope.offer.offerSubcategoryList,
						function(offer){
					$scope.offerEdited = true;
					$scope.noEditOffer = false;
					$('#confirmEditOfferModal').modal('show');
					$('#confirmEditOfferModal').appendTo("body");
					$location.path("/users/myoffers");
					
			/*		angular.element('#myModalShower').trigger('click');*/
					
					//$scope.posts.splice(0,0,offer);
					/*delete $scope.offer;
					$('#photofile').fileinput('clear');
					$('#filepdf').fileinput('clear');
					$scope.htmlVariable = "";
					$scope.checkResults = [];
					angular.element(document.querySelectorAll("#MyOfferSubcategories")).removeClass("active");		   			
					$scope.createofferform.$setUntouched();*/
				},
				function(){
					$scope.noEditOffer = true;
					$scope.offerEdited = false; 
				});
			}
		};
	}]);
}());