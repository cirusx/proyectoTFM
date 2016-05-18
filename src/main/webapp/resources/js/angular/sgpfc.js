(function(){

	var app = angular.module('sgpfc', ['smart-table', 'ngRoute', 'ngCookies']);
	
	app.run(['$http', '$cookies', '$window', function($http, $cookies, $window) {
		//alert("trying...");
		if ($cookies.get('user')) {
			alert("relogin user "+$cookies.get('user'));
			$http.defaults.headers.common.Authorization = 'Basic '+btoa($cookies.get('user')+':'+$cookies.get('password'));	
		}
		
		/*$window.onbeforeunload = function() {
            // Clearing all cookies now!
            if ($cookies.get('user')) {
			$cookies.remove("user"); 
            $cookies.remove("password");
		}
            
        };*/
	}]);
	

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
			templateUrl: '/proyectoTFM/views/register.html',
			controller: 'authController'
		}).when('/recoverpassword', {
			templateUrl: '/proyectoTFM/views/recoverpassword.html',
			controller: 'authController'
		}).when('/projects/myprojects', {
			templateUrl: '/proyectoTFM/views/myprojects.html',
			controller: 'authController'
		}).when('/createoffer', {
			templateUrl: '/proyectoTFM/views/createoffer.html',
			controller: 'createOfferController'
		}).when('/createproject', {
			templateUrl: '/proyectoTFM/views/createproject.html'
		}).otherwise({
			redirectTo: '/',
			templateUrl: '/proyectoTFM/views/home.html',
			controller: 'homeController'	
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
	

	app.controller('sgpfcCtrl',['$scope', '$http', '$cookies', '$location', function($scope, $http, $cookies, $location){

	
		
		$scope.getOffer = function(offerId) {
			  $http.get('http://localhost:8080/proyectoTFM/rest/offers/'+ offerId).then(function(offer) {
			    $scope.offer = offer.data;
			    var datetime;
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
		

		$scope.getProjects = function() {
			  $http.get('http://localhost:8080/proyectoTFM/rest/projects').then(function(projects) {
			    $scope.projectList = projects.data;
			  }, function(err) {
			    console.error('ERR', err);
			    alert("No se han logrado conseguir los proyectos");
			})
		}

	
		
	}]);
	
	
	

})();