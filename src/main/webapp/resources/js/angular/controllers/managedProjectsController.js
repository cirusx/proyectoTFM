(function () {
	'use strict';

	var app= angular.module('sgpfc');
	app.controller('managedProjectsController',['$scope', '$rootScope', '$http', '$location', '$cookies',
	                                     function ($scope, $rootScope, $http, $location, $cookies) {

		
		$http.get('http://localhost:8080/proyectoTFM/rest/users/mymanagedprojects').then(function(projects) {
			projects.data.forEach(function(project) {
				$http.get('http://localhost:8080/proyectoTFM/rest/projects/student'+'?projectId='+project.projectId).then(function(student) {
					project.projectStudent = student.data
				}, function(err) {
					console.error('ERR', err);
					// err.status will contain the status code
				})

				$http.get('http://localhost:8080/proyectoTFM/rest/projects/subcategories'+'?projectId='+project.projectId).then(function(subcategories) {
					project.projectSubcategoryList = subcategories.data
				}, function(err) {
					console.error('ERR', err);
					// err.status will contain the status code
				})
				
				$http.get('http://localhost:8080/proyectoTFM/rest/projects/links'+'?projectId='+project.projectId).then(function(links) {
					project.projectLinks = links.data
				}, function(err) {
					console.error('ERR', err);
					// err.status will contain the status code
				})

			})
			$scope.myManagedProjectList = projects.data;
		}, function(err) {
			console.error('No se han logrado conseguir los proyectos', err);
		})
		
		
		
		$scope.deleteProject = function(projectId) {
			$http.delete('http://localhost:8080/proyectoTFM/rest/projects/'+projectId).then(function(del) {
				$scope.projectDelete = true;
				$http.get('http://localhost:8080/proyectoTFM/rest/projects/').then(function(projects) {
					$scope. myManagedProjectList = projects.data;
				}, function(err) {
					console.error('ERR', err);
					// err.status will contain the status code
				});
			}, function(err) {
				console.error('ERR', err);
				// err.status will contain the status code
			});
		}
		
		$scope.setProjectId = function (projectId) {
            $scope.projectId = projectId;
		}

	}]);
}());