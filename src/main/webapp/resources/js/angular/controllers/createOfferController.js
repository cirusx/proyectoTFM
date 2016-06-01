(function () {
	'use strict';

	var app= angular.module('sgpfc');

	app.controller('createOfferController',['$scope', '$http', '$location', '$cookies', 'postService',
	                                        function ($scope, $http, $location, $cookies, postService) {

		
		$scope.createOffer = function() {
			
			alert("timelimit: "+$scope.offer.offerTimeLimit);
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
				$scope.offer.offerDescription = $scope.htmlVariable;
				postService.postOffer($scope.offer.offerName, $scope.offer.offerTinyDescription, $scope.offer.offerDescription, $scope.offer.offerImage, $scope.offer.offerWithLimit, $scope.offer.offerTimeLimit, $scope.offer.offerPdf,
						function(offer){
					$scope.offerCreated = true;
					$scope.noCreateOffer = false;
					//$scope.posts.splice(0,0,offer);
					delete $scope.offer;
					$('#photofile').fileinput('clear');
					$('#filepdf').fileinput('clear');
					$scope.htmlVariable = "";
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