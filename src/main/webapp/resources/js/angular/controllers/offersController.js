(function () {
	'use strict';

	var app= angular.module('sgpfc');
	app.controller('offersController',['$scope', '$http', '$location', '$cookies',
	                                   function ($scope, $http, $location, $cookies) {
		$scope.getActiveOffers = function() {
			$http.get('http://localhost:8080/proyectoTFM/rest/offers/activeoffers').then(function(activeOffers) {
				$scope.activeOfferList = activeOffers.data;
			}, function(err) {
				console.error('ERR', err);
				// err.status will contain the status code
			})

		}

		$scope.getRecommendedOffers = function() {
			$http.get('http://localhost:8080/proyectoTFM/rest/offers/recommendedoffers').then(function(recommendedOffers) {
				$scope.recommendedOfferList = recommendedOffers.data;
			}, function(err) {
				console.error('ERR', err);
				// err.status will contain the status code
			})

		}
	}]);
}());