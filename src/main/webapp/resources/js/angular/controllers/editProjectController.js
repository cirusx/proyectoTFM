(function () {
	'use strict';

	var app= angular.module('sgpfc');

	app.controller('editProjectController',['$scope', '$http', '$location', '$cookies', '$routeParams', 'postService',
	                                      function ($scope, $http, $location, $cookies, $routeParams, postService) {

		var projectId =$routeParams.projectId;
		$scope.noEditOffer= false;
		$scope.offerEdited= false;
		$scope.noTeacher= false;
		$scope.noTeacherOrLogged= false;
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

		var i = 0;
		$http.get('http://localhost:8080/proyectoTFM/rest/categories').then(function(categories) {
			$scope.categories = categories.data;
			categories.data.forEach(function(category){
				$http.get('http://localhost:8080/proyectoTFM/rest/categories/subcategories'+'?categoryId='+category.categoryId).then(function(subcategories) {
					$scope.categories[i].subcategories = subcategories.data;
					i++;
				}, function(err) {
					console.error('ERR', err);  
				})
			})

		}, function(err) {
			console.error('ERR', err);  
		})





		$http.get('http://localhost:8080/proyectoTFM/rest/projects/'+ projectId).then(function(project) {
			if (project.data.projectId==null) {
				$location.path("/");
			}else {
				
				/*var projectYearInt = parseInt(project.data.projectYear);*/
				
				$scope.project = project.data;
				/*$scope.project.projectyear = projectYearInt;*/
				
				$http.get('http://localhost:8080/proyectoTFM/rest/projects/student'+'?projectId='+projectId).then(function(student) {
					project.projectStudent = student.data
				}, function(err) {
					console.error('ERR', err);
					// err.status will contain the status code
				})

				$http.get('http://localhost:8080/proyectoTFM/rest/projects/teacher'+'?projectId='+projectId).then(function(teacher) {
					project.projectTeacher = teacher.data
				}, function(err) {
					console.error('ERR', err);
					// err.status will contain the status code
				})

				$http.get('http://localhost:8080/proyectoTFM/rest/projects/subcategories'+'?projectId='+projectId).then(function(subcategories) {
					project.projectSubcategoryList = subcategories.data
				}, function(err) {
					console.error('ERR', err);
					// err.status will contain the status code
				})
				
				$http.get('http://localhost:8080/proyectoTFM/rest/projects/links'+'?projectId='+projectId).then(function(links) {
					project.projectLinks = links.data
				}, function(err) {
					console.error('ERR', err);
					// err.status will contain the status code
				})
				$scope.project = project.data;
			}
		}, function(err) {
			console.error('ERR', err);
			// err.status will contain the status code
		})



$scope.editProject = function() {
			
			$scope.noEditProject = false;
			$scope.projectEdited = false;
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
				postService.postProject($scope.project.projectName, $scope.project.projectCode, $scope.project.projectCareer, $scope.project.projectYear, $scope.project.projectStudent, $scope.project.projectSubcategoryList, $scope.project.projectLinks, $scope.project.projectDocumentation, $scope.project.projectDraft,
						function(project){
					$scope.projectEdited = true;
					$scope.noEditProject = false;
					//$scope.posts.splice(0,0,offer);
					delete $scope.project;
					$('#filedocumentation').fileinput('clear');
					$('#filedraft').fileinput('clear');
					$scope.checkResults = [];
					angular.element(document.querySelectorAll("#MyProjectSubcategories")).removeClass("active");
					$scope.createprojectform.$setUntouched();
				},
				function(){
					$scope.noEditProject = true;
					$scope.projectEdited = false; 
				}
				);
			}
		};
	}]);
}());