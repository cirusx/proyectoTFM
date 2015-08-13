(function(){
	var app = angular.module('sgpfc', []);
	
//	'ngResource'
	
	app.controller('sgpfcCtrl', function($scope, $http){
		$scope.getOffers = function() {
			
			console.log("ok");
			$http.get('http://localhost:8080/proyectoTFM/rest/offers').succes(function(offerList) {
					console.log(offerList);
					$scope.offers= offerList;
				})
				.
				error(function(data, status, heaers, config) {
					
				});
		}
		
$scope.createOffer = function() {
			
			console.log("ok");
			$http.post('http://localhost:8080/proyectoTFM/rest/offers/create').succes(function(offer) {
				console.log(offer);
				$scope.offer= offer;
				})
				.
				error(function(data, status, heaers, config) {
					
				});
		}
	});
})();