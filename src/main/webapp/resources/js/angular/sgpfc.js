(function(){

//	var app = angular.module('sgpfc', ['smart-table', 'ngRoute', 'ngCookies']);
	
	angular.module('Authentication', []);
	angular.module('Home', []);

	var app = angular.module('sgpfc', [
	    'Authentication',
	    'Home',
	    'ngRoute',
	    'ngCookies'
	])
	 
	.run(['$rootScope', '$location', '$cookieStore', '$http',
	    function ($rootScope, $location, $cookieStore, $http) {
	        // keep user logged in after page refresh
	        $rootScope.globals = $cookieStore.get('globals') || {};
	        if ($rootScope.globals.currentUser) {
	            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
	        }
	 
	        $rootScope.$on('$locationChangeStart', function (event, next, current) {
	            // redirect to login page if not logged in
	            if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
	                $location.path('/login');
	            }
	        });
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
			controller: 'LoginController'
		}).when('/register', {
			templateUrl: '/proyectoTFM/views/register.html'
		}).when('/recoverpassword', {
			templateUrl: '/proyectoTFM/views/recoverpassword.html'
		}).when('/createoffer', {
			templateUrl: '/proyectoTFM/views/createoffer.html'
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
	}]);
	
	app.controller('authController',['$scope', '$http', '$location', '$cookies',
	                                  function ($scope, $http, $location, $cookies) {
/*	$scope.login = function() {
		
		var aaa= null;
		var login = $scope.user.email;
		var password = $scope.user.password;
		$cookies.put('user', login+':'+password);
		$http.defaults.headers.common.Authorization = 'Basic'+btoa(login+':'+password);
	
	}*/
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

		$scope.createOffer = function() {

			$http({
				method: 'POST',
				url: 'http://localhost:8080/proyectoTFM/rest/offers/create',
				data: $scope.offer,
			});
		}
	});
	
	angular.module('Authentication')
	
	.controller('LoginController',
    ['$scope', '$rootScope', '$location', 'AuthenticationService',
    function ($scope, $rootScope, $location, AuthenticationService) {
        // reset login status
        AuthenticationService.ClearCredentials();
 
        $scope.login = function () {
            $scope.dataLoading = true;
            AuthenticationService.Login($scope.username, $scope.password, function(response) {
                if(response.success) {
                    AuthenticationService.SetCredentials($scope.username, $scope.password);
                    $location.path('/');
                } else {
                    $scope.error = response.message;
                    $scope.dataLoading = false;
                }
            });
        };
    }])
	 
	.factory('AuthenticationService',
	    ['$http', '$cookieStore', '$rootScope', '$timeout',
	    function ($http, $cookieStore, $rootScope, $timeout) {
	        var service = {};

	        service.Login = function (username, password, callback) {

	            /* Dummy authentication for testing, uses $timeout to simulate api call
	             ----------------------------------------------*/
	            $timeout(function(){
	                var response = { success: username === 'test' && password === 'test' };
	                if(!response.success) {
	                    response.message = 'Username or password is incorrect';
	                }
	                callback(response);
	            }, 1000);


	            /* Use this for real authentication
	             ----------------------------------------------*/
	            //$http.post(''http://localhost:8080/proyectoTFM/rest/login', { username: username, password: password })
	            //    .success(function (response) {
	            //        callback(response);
	            //    });
	            
	            // Simple GET request example:

	        };
	 
	        service.SetCredentials = function (username, password) {
	            var authdata = btoa(username + ':' + password);
	 
	            $rootScope.globals = {
	                currentUser: {
	                    username: username,
	                    authdata: authdata
	                }
	            };
	 
	            $http.defaults.headers.common['Authorization'] = 'Basic ' +btoa(username+':'+password);; // jshint ignore:line
	            $cookieStore.put('globals', $rootScope.globals);
	        };
	 
	        service.ClearCredentials = function () {
	            $rootScope.globals = {};
	            $cookieStore.remove('globals');
	            $http.defaults.headers.common.Authorization = 'Basic ';
	        };
	 
	        return service;
	    }]);
	
	angular.module('Home')
	 
	.controller('HomeController',
	    ['$scope',
	    function ($scope) {
	      
	    }]);
})();