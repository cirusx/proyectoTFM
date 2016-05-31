(function () {
	'use strict';

	var app= angular.module('sgpfc');

	app.controller('editOfferController',['$scope', '$http', '$location', '$cookies', '$routeParams', 'postService',
	                                        function ($scope, $http, $location, $cookies, $routeParams, postService) {

		$scope.noEditOffer= false;
		$scope.offerEdited= false;
		$scope.noTeacher= false;
		$scope.noTeacherOrLogged= false;
		
		var offerId =$routeParams.offerId;
		
		$http.get('http://localhost:8080/proyectoTFM/rest/offers/'+ offerId).then(function(offer) {
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