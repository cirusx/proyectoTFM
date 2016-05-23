(function () {
	'use strict';

	var app= angular.module('sgpfc');
	app.controller('myOffersController',['$scope', '$rootScope', '$http', '$location', '$cookies',
	                                 function ($scope, $rootScope, $http, $location, $cookies) {
		
		
			$http.get('http://localhost:8080/proyectoTFM/rest/users/myoffers').then(function(myOfferList) {
				myOfferList.data.forEach(function(offer) {
			    	$http.get('http://localhost:8080/proyectoTFM/rest/offers/subcategories'+'?offerId='+offer.offerId).then(function(subcategories) {
			    		offer.offerSubcategoryList = subcategories.data
					  }, function(err) {
					    console.error('ERR', err);
					    // err.status will contain the status code
			    	})
			    	 $scope.myOfferList = myOfferList.data;
			    });
			  }, function(err) {
			    console.error('ERR', err);
			    // err.status will contain the status code
			})
		
	}]);
}());