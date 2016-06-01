(function () {
	'use strict';

	var app= angular.module('sgpfc');
	app.controller('projectsController',['$scope', '$rootScope', '$http', '$location', '$cookies',
	                                     function ($scope, $rootScope, $http, $location, $cookies) {

		$http.get('http://localhost:8080/proyectoTFM/rest/projects').then(function(projects) {
			projects.data.forEach(function(project) {
				$http.get('http://localhost:8080/proyectoTFM/rest/projects/student'+'?projectId='+project.projectId).then(function(student) {
					project.projectStudent = student.data
				}, function(err) {
					console.error('ERR', err);
					// err.status will contain the status code
				})

				$http.get('http://localhost:8080/proyectoTFM/rest/projects/teacher'+'?projectId='+project.projectId).then(function(teacher) {
					project.projectTeacher = teacher.data
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
			$scope.projectList = projects.data;
		}, function(err) {
			console.error('No se han logrado conseguir los proyectos', err);
			
		})
	}]);
}());