(function () {
	'use strict';

	var app= angular.module('sgpfc');
	app.controller('offersController',['$scope', '$http', '$location', '$cookies',
	                                   function ($scope, $http, $location, $cookies) {

		$http.get('http://localhost:8080/proyectoTFM/rest/offers/activeoffers').then(function(activeOffers) {
			activeOffers.data.forEach(function(offer) {
				$http.get('http://localhost:8080/proyectoTFM/rest/offers/subcategories'+'?offerId='+offer.offerId).then(function(subcategories) {
					offer.offerSubcategoryList = subcategories.data
				}, function(err) {
					console.error('ERR', err);
					// err.status will contain the status code
				})
				$scope.activeOfferList = activeOffers.data;
			});
		}, function(err) {
			console.error('ERR', err);
			// err.status will contain the status code
		})

		$scope.predicate = 'offerId';
		$scope.reverse = true;
		$scope.order = function(predicate) {
			$scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
			$scope.predicate = predicate;
			var elem = document.getElementById("orderLabel");
			if (elem.innerHTML=="Ofertas Antiguas primero:") elem.innerHTML = "Ofertas Nuevas primero:";
			else elem.innerHTML = "Ofertas Antiguas primero:";
		};

		$scope.getRecommendedOffers = function() {
			$http.get('http://localhost:8080/proyectoTFM/rest/offers/recommendedoffers').then(function(recommendedOffers) {
				recommendedOffers.data.forEach(function(offer) {
					$http.get('http://localhost:8080/proyectoTFM/rest/offers/subcategories'+'?offerId='+offer.offerId).then(function(subcategories) {
						offer.offerSubcategoryList = subcategories.data
					}, function(err) {
						console.error('ERR', err);
						// err.status will contain the status code
					})
					$scope.recommendedOfferList = recommendedOffers.data;
				});
			}, function(err) {
				console.error('ERR', err);
				// err.status will contain the status code
			})
		}
	}]);
}());