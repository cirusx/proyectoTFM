(function(){

	var app = angular.module('sgpfc', ['smart-table', 'ngRoute', 'ngCookies']);

	app.config(['$routeProvider', function($routeProvider) {
		$routeProvider.when('/offers', {
			templateUrl: '/proyectoTFM/views/offers.html'
		}).when('/offers/:offerId', {
			templateUrl: '/proyectoTFM/views/offer.html',
			controller: 'offerController'
		}).when('/projects', {
			templateUrl: '/proyectoTFM/views/projects.html'
		}).when('/login', {
			templateUrl: '/proyectoTFM/views/login.html',
			controller: 'authController'
		}).when('/register', {
			templateUrl: '/proyectoTFM/views/register.html'
		}).when('/recoverpassword', {
			templateUrl: '/proyectoTFM/views/recoverpassword.html'
		}).when('/createoffer', {
			templateUrl: '/proyectoTFM/views/createoffer.html',
			controller: 'offerController'
		}).when('/createproject', {
			templateUrl: '/proyectoTFM/views/createproject.html'
		}).otherwise({
			redirectTo: '/',
			templateUrl: '/proyectoTFM/views/home.html'
		})
	}]);
	
	app.filter('myStrictFilter', function($filter){
	    return function(input, predicate){
	        return $filter('filter')(input, predicate, true);
	    }
	});

/*	app.filter('unique', function() {
	    return function (arr, field) {
	        var o = {}, i, l = arr.length, r = [];
	        for(i=0; i<l;i+=1) {
	            o[arr[i][field]] = arr[i];
	        }
	        for(i in o) {
	            r.push(o[i]);
	        }
	        return r;
	    };
	 })*/
	
	app.controller('offerController',['$scope', '$http', '$location', '$routeParams',
	                                  function ($scope, $http, $location, $routeParams) {
		
	      //Get ID out of current URL
		var offerId = $scope.offer_Id = $routeParams.offerId;
		if (offerId != undefined) {
			$http.get('http://localhost:8080/proyectoTFM/rest/offers/'+ offerId).then(function(offer) {
				if (offer.data.offerId==null) {
					$location.path("/");
				}else {
					$scope.offer = offer.data;
				}
			}, function(err) {
			    console.error('ERR', err);
			    // err.status will contain the status code
			})
		}
		
		
		$scope.createOffer = function() {
			var offer = $scope.offer
			$http.post('http://localhost:8080/proyectoTFM/rest/offers/create', $scope.offer, {headers: {'Content-Type': 'application/json'} }).
		    success(function(data) {
		        // this callback will be called asynchronously
		        // when the response is available
		    	var x = data.offer
		        console.log(data);
		      });
			/*.
		      error(function(data, status, headers, config) {
		        // called asynchronously if an error occurs
		        // or server returns response with an error status.
		      });
			
			$http({
				method: 'POST',
				url: 'http://localhost:8080/proyectoTFM/rest/offers/create',
				data: $scope.offer
			})*/
		}
	}]);
	
	app.controller('authController',['$scope', '$http', '$location', '$cookies',
	                                  function ($scope, $http, $location, $cookies) {
	$scope.login = function() {
		
		
		var login = $scope.user.email;
		var password = $scope.user.password;
		$cookies.put('user', login+':'+password);
		$http.defaults.headers.common.Authorization = 'Basic'+btoa(login+':'+password).sucess()
	
	}
	}]);

	app.controller('sgpfcCtrl', function($scope, $http){
		/*$scope.getOffers = function() {
			
			$http({
				method: 'GET',
				url: 'http://localhost:8080/proyectoTFM/rest/offers',
			});
		
		}*/
		
		$scope.getOffer = function(offerId) {
			  $http.get('http://localhost:8080/proyectoTFM/rest/offers/'+ offerId).then(function(offer) {
			    $scope.offer = offer.data;
			  }, function(err) {
			    console.error('ERR', err);
			    // err.status will contain the status code
			})
		
		}

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
		
		$scope.getRecommendedOffers = function() {
			  $http.get('http://localhost:8080/proyectoTFM/rest/offers/recommendedoffers').then(function(recommendedOffers) {
			    $scope.recommendedOfferList = recommendedOffers.data;
			  }, function(err) {
			    console.error('ERR', err);
			    // err.status will contain the status code
			})
		
		}
		
		$scope.getHomeRecommendedOffers = function() {
			  $http.get('http://localhost:8080/proyectoTFM/rest/offers/homerecommendedoffers').then(function(homeRecommendedOffers) {
			    $scope.homeRecommendedOfferList = homeRecommendedOffers.data;
			  }, function(err) {
			    console.error('ERR', err);
			    // err.status will contain the status code
			})
		
		}
		
		$scope.getLastOffers = function() {
			  $http.get('http://localhost:8080/proyectoTFM/rest/offers/lastoffers').then(function(lastOffers) {
			    $scope.lastOfferList = lastOffers.data;
			  }, function(err) {
			    console.error('ERR', err);
			    // err.status will contain the status code
			})
		
		}
		
		$scope.getProjects = function() {
			  $http.get('http://localhost:8080/proyectoTFM/rest/projects').then(function(projects) {
			    $scope.projectList = projects.data;
			  }, function(err) {
			    console.error('ERR', err);
			    // err.status will contain the status code
			})
		}

		
	});
})();