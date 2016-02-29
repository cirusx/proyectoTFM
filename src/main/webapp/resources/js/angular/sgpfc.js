(function(){
	var app = angular.module('sgpfc', ['smart-table', 'ui.grid']);

//	'ngResource'

	app.controller('sgpfcCtrl', function($scope, $http){
		/*$scope.getOffers = function() {
			
			$http({
				method: 'GET',
				url: 'http://localhost:8080/proyectoTFM/rest/offers',
			});
		
		}*/
		$scope.getOffers = function() {
			  $http.get('http://localhost:8080/proyectoTFM/rest/offers').then(function(offers) {
			    $scope.offerList = offers.data;
			  }, function(err) {
			    console.error('ERR', err);
			    // err.status will contain the status code
			})
		
		}
		
		$scope.getActiveOffers = function() {
			  $http.get('http://localhost:8080/proyectoTFM/rest/offers/activeoffers').then(function(activeOffers) {
			    $scope.activeOfferList = activeOffers.data;
			  }, function(err) {
			    console.error('ERR', err);
			    // err.status will contain the status code
			})
		
		}

		$scope.createOffer = function() {

			$http({
				method: 'POST',
				url: 'http://localhost:8080/proyectoTFM/rest/offers/create',
				data: $scope.offer,
			});
		}
		
		
	});
})();