(function () {
	'use strict';

	var app= angular.module('sgpfc');

	app.controller('createProjectController',['$scope', '$http', '$location', '$cookies',
	                                          function ($scope, $http, $location, $cookies) {

		$http.get('http://localhost:8080/proyectoTFM/rest/users/students').then(function(students) {
			$scope.students = students.data;
		}, function(err) {
			console.error('ERR', err);
			// err.status will contain the status code
		});
		$scope.checkModel = {};
		$scope.checkResults = [];
		$scope.oneAtATime = true;
		$scope.$watchCollection('checkModel', function () {
			$scope.checkResults = [];
			angular.forEach($scope.checkModel, function (value, key) {
				if (value) {
					$scope.checkResults.push(key);
				}
			});
		});
		$scope.status = {
				isCustomHeaderOpen: false,
				isFirstOpen: true,
				isFirstDisabled: false
		};
		
		$http.get('http://localhost:8080/proyectoTFM/rest/categories').then(function(categories) {
			categories.data.forEach(function(category){
				$http.get('http://localhost:8080/proyectoTFM/rest/categories/subcategories'+'?categoryId='+category.categoryId).then(function(subcategories) {
					category.subcategories = subcategories.data
				}, function(err) {
					console.error('ERR', err);  
				})
			})
			$scope.categories = categories.data;
		}, function(err) {
			console.error('ERR', err);  
		})

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
				$scope.project.projectSubcategoryList= $scope.checkResults;
				
				var fd = new FormData();
				fd.append('name', $scope.project.projectName);
				fd.append('code', $scope.project.projectCode);
				fd.append('career', $scope.project.projectCareer);
				fd.append('year', $scope.project.projectYear);
				fd.append('student', $scope.project.projectStudent);
				fd.append('subcategories', $scope.project.projectSubcategoryList);
				if($scope.project.projectLinks != undefined) {
					fd.append('links', $scope.project.projectLinks);
				}
				fd.append('documentation', $scope.project.projectDocumentation);
				fd.append('draft', $scope.project.projectDraft);
				$http.post('http://localhost:8080/proyectoTFM/rest/projects/project', fd, {
					transformRequest: angular.identity,
					headers: {'Content-Type': undefined}
				}).then(function(project) {
					$scope.projectCreated = true;
					$scope.noCreateProject = false;
					//$scope.posts.splice(0,0,offer);
					delete $scope.project;
					$('#filedocumentation').fileinput('clear');
					$('#filedraft').fileinput('clear');
					$scope.checkResults = [];
					$scope.checkModel = {};
					angular.element(document.querySelectorAll("#MyProjectSubcategories")).removeClass("active");
					$scope.createprojectform.$setUntouched();
				}, function(err) {
					console.error('ERR', err); 
					$scope.noCreateProject = true;
					$scope.projectCreated = false;
				})
			}
		};
	}]);
}());