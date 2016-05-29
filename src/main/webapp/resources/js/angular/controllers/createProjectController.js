(function () {
	'use strict';

	var app= angular.module('sgpfc');

	app.controller('createProjectController',['$scope', '$http', '$location', '$cookies', 'postService',
	                                        function ($scope, $http, $location, $cookies, postService) {
		
		$http.get('http://localhost:8080/proyectoTFM/rest/users/students').then(function(students) {
			$scope.students = students.data;
		}, function(err) {
			console.error('ERR', err);
			// err.status will contain the status code
		});
		

		$scope.createProject = function() {
			
			$scope.noCreateProject = false;
			$scope.projectCreated = false;
			$scope.noTeacher = false;
			$scope.noTeacherOrLogged = false;
			var login = false;
			var rol = false;
			if ($cookies.get('user')) {
				login = true
				if ($cookies.get('rol')== "Teacher") {
					rol = true
				} else {
					$scope.noTeacher = true;
				}
			} else {
				$scope.noTeacherOrLogged= true;
			}
			if(login & rol) {
				
				postService.postProject($scope.project.projectName, $scope.project.projectCareer, $scope.project.projectYear, $scope.project.projectStudent, $scope.project.projectLinks, $scope.project.projectDocumentation, $scope.project.projectDraft,
						function(offer){
					$scope.projectCreated = true;
					$scope.noCreateProject = false;
					$scope.posts.splice(0,0,offer);
				},
				function(){
					$scope.noCreateProject = true;
					$scope.projectCreated = false; 
				}
				);
			}
		};
	}]);
}());