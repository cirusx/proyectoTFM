(function(){

	var app = angular.module('sgpfc', ['smart-table', 'ngRoute', 'ngCookies', 'ngMessages', 'textAngular', 'ngSanitize', 'ngAnimate', 'ui.bootstrap']);

	app.run(['$http', '$cookies', '$window', '$rootScope', function($http, $cookies, $window, $rootScope) {
		if ($cookies.get('user')) {
			$rootScope.logged = true;
			var rol = $cookies.get('rol');
			$rootScope.loggedType = rol;
			$http.defaults.headers.common.Authorization = 'Basic '+btoa($cookies.get('user')+':'+$cookies.get('password'));	
		}
			
	}]);
	
	app.config(['$routeProvider', function($routeProvider) {
		
		$routeProvider.when('/offers', {
			templateUrl: '/proyectoTFM/views/offers.html',
			controller: 'offersController'
		}).when('/offers/:offerId', {
			templateUrl: '/proyectoTFM/views/offer.html',
			controller: 'offerController'
		}).when('/offers/teacher/:offerId/edit', {
			templateUrl: '/proyectoTFM/views/editoffer.html',
			controller: 'editOfferController'
		}).when('/offers/teacher/:offerId', {
			templateUrl: '/proyectoTFM/views/offer.html',
			controller: 'myOfferController'
		}).when('/projects', {
			templateUrl: '/proyectoTFM/views/projects.html',
			controller: 'projectsController'
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
			controller: 'myProjectsController'
		}).when('/projects/mymanagedprojects', {
			templateUrl: '/proyectoTFM/views/managedprojects.html',
			controller: 'managedProjectsController'
		}).when('/users/myoffers', {
			templateUrl: '/proyectoTFM/views/myoffers.html',
			controller: 'myOffersController'
		}).when('/users/myprofile', {
			templateUrl: '/proyectoTFM/views/profile.html',
			controller: 'profileController'
		}).when('/users/myregistrations', {
			templateUrl: '/proyectoTFM/views/registrations.html',
			controller: 'registrationsController'
		}).when('/createoffer', {
			templateUrl: '/proyectoTFM/views/createoffer.html',
			controller: 'createOfferController'
		}).when('/createproject', {
			templateUrl: '/proyectoTFM/views/createproject.html',
			controller: 'createProjectController'
		}).otherwise({
			redirectTo: '/',
			templateUrl: '/proyectoTFM/views/home.html',
			controller: 'homeController'	
		})
	}]);
	
	app.controller('sgpfcCtrl',['$scope', '$http', '$cookies', '$location', function($scope, $http, $cookies, $location){
	}]);

})();