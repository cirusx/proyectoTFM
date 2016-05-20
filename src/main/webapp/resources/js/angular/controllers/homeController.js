(function () {
    'use strict';
 
    var app= angular.module('sgpfc');
app.controller('homeController',['$scope', '$http', '$location', '$cookies',
	                                  function ($scope, $http, $location, $cookies) {
		
//		$scope.getHomeRecommendedOffers = function() {
			  $http.get('http://localhost:8080/proyectoTFM/rest/offers/homerecommendedoffers').then(function(homeRecommendedOffers) {
				  homeRecommendedOffers.data.forEach(function(offer) {
				    	$http.get('http://localhost:8080/proyectoTFM/rest/offers/subcategories'+'?offerId='+offer.offerId).then(function(subcategories) {
				    		offer.offerSubcategoryList = subcategories.data
						  }, function(err) {
						    console.error('ERR', err);
						    // err.status will contain the status code
				    	})
				    	 $scope.homeRecommendedOfferList = homeRecommendedOffers.data;
				    	//$http.get(.... /students?offerId=offer.getId
				    	/*$timeout(function() {
				    		offer.subitems = [1, 2, 3];
				    	}, 3000);*/
				    });
				  }, function(err) {
				    console.error('ERR', err);
				    // err.status will contain the status code
				})
		
//		}
		
//		$scope.getLastOffers = function() {
			  $http.get('http://localhost:8080/proyectoTFM/rest/offers/lastoffers').then(function(lastOffers) {
			    lastOffers.data.forEach(function(offer) {
			    	$http.get('http://localhost:8080/proyectoTFM/rest/offers/subcategories'+'?offerId='+offer.offerId).then(function(subcategories) {
			    		offer.offerSubcategoryList = subcategories.data
					  }, function(err) {
					    console.error('ERR', err);
					    // err.status will contain the status code
			    	})
			    	 $scope.lastOfferList = lastOffers.data;
			    });
			  }, function(err) {
			    console.error('ERR', err);
			    // err.status will contain the status code
			})
//		}
		
		
	}]);
}());