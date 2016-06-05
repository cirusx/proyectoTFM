(function () {
	'use strict';

	var app= angular.module('sgpfc');
	app.controller('offerController',['$scope', '$http', '$location', '$routeParams', '$cookies',
	                                  function ($scope, $http, $location, $routeParams, $cookies) {

		var offerId = $scope.offerId = $routeParams.offerId;
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

					$http.get('http://localhost:8080/proyectoTFM/rest/offers/users'+'?offerId='+offerId).then(function(users) {
						offer.data.offerRegistrationList = users.data
						$scope.offerRegisterButton = 1;
						if ($cookies.get('user')) {
							var rol = $cookies.get('rol');
							if (rol == "Student") {
								for (var user in users.data) {
									var email = users.data[user].email;
									var cookiemail= $cookies.get('user')
									if ( email == cookiemail){
										$scope.enableButton = 2;
									}
								}
							} else if($cookies.get('rol')== "Teacher"){
								$scope.enableButton = 3;
							}
						} 
					}, function(err) {
						console.error('ERR', err);
						// err.status will contain the status code
					});

					if(offer.data.offerWithLimit == true) {
						var offerTimeLimit = offer.data.offerTimeLimit;
						var actualDate = Date.now();
						if (actualDate >= offerTimeLimit){
							offer.data.offerClose = true;
							$scope.offer = offer.data;
							$http.put('http://localhost:8080/proyectoTFM/rest/offers/'+offerId+'/close').then(function(cerrar) {
								$scope.offerClose = true;
							}, function(err) {
								console.error('ERR', err);
								// err.status will contain the status code
							});
						}
					}
					$scope.offer = offer.data;
				}
			}, function(err) {
				console.error('ERR', err);
				// err.status will contain the status code
			})
		}

		$scope.offerUserRegister = function() {
			var offer = $scope.offer;
			var login = false;
			var rol = false;
			if ($cookies.get('user')) {
				login = true
				if ($cookies.get('rol')== "Student") {
					rol = true
				} else {
					//alert("No puedes inscribirte ya que no eres un alumno");
					$scope.NoStudent= true;
				}
			} else {
				//alert("No puedes registrarte ya que no estas registrado como alumno o logeado");
				$scope.NoStudentOrLogged= true;
			}
			if(login & rol) {
				$http.post('http://localhost:8080/proyectoTFM/rest/offers/'+offer.offerId+'/students').then(
						function (offer) {
							$scope.offerRegisterError = false;
							$http.get('http://localhost:8080/proyectoTFM/rest/offers/users'+'?offerId='+offerId).then(function(users) {
								offer.data.offerRegistrationList = users.data
								$scope.offerRegisterError = false;
								$scope.enableButton = 2;
								$scope.offer = offer.data;
								$location.path("/offers/"+offer.data.offerId);		
							}, function(err) {
								console.error('ERR', err);
								// err.status will contain the status code
							});
						},
						function (offer) {
							$scope.offerRegisterError = true;
						});
			}
		}
	}]);
}());