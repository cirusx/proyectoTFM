(function(){

	var app = angular.module('sgpfc', ['smart-table', 'ngRoute', 'ngCookies']);
	
	app.run(['$http', '$cookies', function($http, $cookies) {
		//alert("trying...");
		if ($cookies.get('user')) {
			alert("relogin user "+$cookies.get('user'));
			$http.defaults.headers.common.Authorization = 'Basic '+btoa($cookies.get('user')+':'+$cookies.get('password'));	
		}
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
			templateUrl: '/proyectoTFM/views/recoverpassword.html'
		}).when('/createoffer', {
			templateUrl: '/proyectoTFM/views/createoffer.html',
			controller: 'sgpfcCtrl'
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
					$http.get('http://localhost:8080/proyectoTFM/rest/offers/subcategories'+'?offerId='+offerId).then(function(subcategories) {
			    		offer.data.offerSubcategoryList = subcategories.data
					  }, function(err) {
					    console.error('ERR', err);
					    // err.status will contain the status code
			    	});
					$scope.offer = offer.data;
				}
			}, function(err) {
			    console.error('ERR', err);
			    // err.status will contain the status code
			})
		}
	}]);
	
	app.controller('authController',['$scope', '$http', '$location', '$cookies',
	                                  function ($scope, $http, $location, $cookies) {
	$scope.login = function() {
		var login = $scope.user.email;
		var password = $scope.user.password;
		$cookies.put('user', login);
		$cookies.put('password', password);
		$http.defaults.headers.common.Authorization = 'Basic '+btoa(login+':'+password);
		
		$http.get('http://localhost:8080/proyectoTFM/rest/users/'+login.replace('@', '%40')).then(function(user) {
			alert(JSON.stringify(user));
		}, function(err) {
		    console.error('ERR', err);
		    // err.status will contain the status code
		})
	}
	
	$scope.register = function() {
		alert(JSON.stringify($scope.user));
		$http.post('http://localhost:8080/proyectoTFM/rest/users/create', $scope.user).then(
				function (response) {
					
				},
				function (response) {
					
				}
		);
	}
	}]);

	app.controller('sgpfcCtrl',['$scope', '$http', '$timeout', function($scope, $http, $timeout){
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
		
		}
		
		$scope.getLastOffers = function() {
			  $http.get('http://localhost:8080/proyectoTFM/rest/offers/lastoffers').then(function(lastOffers) {
			   
	
			    lastOffers.data.forEach(function(offer) {
			    	$http.get('http://localhost:8080/proyectoTFM/rest/offers/subcategories'+'?offerId='+offer.offerId).then(function(subcategories) {
			    		offer.offerSubcategoryList = subcategories.data
					  }, function(err) {
					    console.error('ERR', err);
					    // err.status will contain the status code
			    	})
			    	 $scope.lastOfferList = lastOffers.data;
			    	//$http.get(.... /students?offerId=offer.getId
			    	/*$timeout(function() {
			    		offer.subitems = [1, 2, 3];
			    	}, 3000);*/
			    });
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
			alert(JSON.stringify($scope.offer));
			var datetimeStr = "2020/07/19 07:00";
			var datetime = new Date(datetimeStr);
			$scope.offer.offerTimeLimit = datetime;
			$http.post('http://localhost:8080/proyectoTFM/rest/offers/create', $scope.offer).then(
					function (response) {
						
					},
					function (response) {
						
					}
			);
		}
	}]);
})();