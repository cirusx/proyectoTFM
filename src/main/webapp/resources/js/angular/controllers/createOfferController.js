(function () {
	'use strict';

	var app= angular.module('sgpfc');

	app.controller('createOfferController',['$scope', '$http', '$location', '$cookies', 'postService',
	                                        function ($scope, $http, $location, $cookies, postService) {

		
		$scope.postPhoto = function() {
		    postService.postPhoto($scope.offer.offerName, $scope.offer.offerDescription, $scope.offer.content, $scope.offer.offerWithLimit, $scope.offer.offerTimeLimit,
		      function(photo){
		        $scope.posts.splice(0,0,photo);
		      },
		      function(){alert("Problem with server during post"); }
		    );
		  };
		
		$scope.createOffer = function() {

			
			$http.post('http://localhost:8080/proyectoTFM/rest/offers/create', $scope.offer).then(
					function (response) {
						alert("La Oferta ha sido creada correctamente");
					},
					function (response) {
						alert("La Oferta no se ha podido crear correctamente");
					}
			);
		}

	}]);
}());