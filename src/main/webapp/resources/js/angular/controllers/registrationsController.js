(function () {
	'use strict';

	var app= angular.module('sgpfc');
	app.controller('registrationsController',['$scope', '$rootScope', '$http', '$location', '$cookies',
	                                 function ($scope, $rootScope, $http, $location, $cookies) {
		
		
			$http.get('http://localhost:8080/proyectoTFM/rest/users/myregistrations').then(function(registerOfferList) {
				registerOfferList.data.forEach(function(offer) {
			    	$http.get('http://localhost:8080/proyectoTFM/rest/offers/subcategories'+'?offerId='+offer.offerId).then(function(subcategories) {
			    		offer.offerSubcategoryList = subcategories.data
					  }, function(err) {
					    console.error('ERR', err);
					    // err.status will contain the status code
			    	})
			    	 $scope.myRegistrationsList = registerOfferList.data;
			    });
			  }, function(err) {
			    console.error('ERR', err);
			    // err.status will contain the status code
			})
		
	}]);
}());