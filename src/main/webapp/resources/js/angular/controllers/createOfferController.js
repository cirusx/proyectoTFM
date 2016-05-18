(function () {
    'use strict';
 
    var app= angular.module('sgpfc');
app.controller('createOfferController',['$scope', '$http', '$location', '$cookies', multipartForm,
	                                  function ($scope, $http, $location, $cookies, multipartForm) {
	
		$scope.createOffer = function() {
			alert(JSON.stringify($scope.offer));
			
			var datetimeStr1 = "2020/07/19 07:00";
			var datetimeStr2 = "2020/07/19 08:00";
			var datetime;
			
			if($scope.offer.offerWithLimit == 0) {
				$scope.offer.offerWithLimit = false;
			}else{
				$scope.offer.offerWithLimit = true;
			}
			if($scope.offer.offerTimeLimit == 1) {
				datetime = new Date(datetimeStr1);
				$scope.offer.offerTimeLimit = datetime;
			}else if($scope.offer.offerTimeLimit == 2){
				datetime = new Date(datetimeStr2);
				$scope.offer.offerTimeLimit = datetime;
			}else{
				$scope.offer.offerTimeLimit = null;
			}
			
			alert(JSON.stringify($scope.offer));
			$http.post('http://localhost:8080/proyectoTFM/rest/offers/create', $scope.offer).then(
					function (response) {
						alert("La Oferta ha sido creada correctamente");
					},
					function (response) {
						alert("La Oferta no se ha podido crear correctamente");
					}
			);
		}
		
		$scope.Submit = function(){
			var uploadUrl = '/upload';
			multipartForm.post(uploadUrl, $scope.offer);
		}
		
	}]);
}());