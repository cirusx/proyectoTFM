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

		$http.get('http://localhost:8080/proyectoTFM/rest/users/students').then(function(students) {
			$scope.students = students.data
		}, function(err) {
			console.error('ERR', err);
			// err.status will contain the status code
		})

		$http.get('http://localhost:8080/proyectoTFM/rest/projects/'+ projectId).then(function(project) {
			if (project.data.projectId==null) {
				$location.path("/");
			}else {
				$scope.project = project.data;

				$http.get('http://localhost:8080/proyectoTFM/rest/projects/student'+'?projectId='+projectId).then(function(student) {
					project.data.projectStudent = student.data
				}, function(err) {
					console.error('ERR', err);
					// err.status will contain the status code
				})

				$http.get('http://localhost:8080/proyectoTFM/rest/projects/teacher'+'?projectId='+projectId).then(function(teacher) {
					project.data.projectTeacher = teacher.data
				}, function(err) {
					console.error('ERR', err);
					// err.status will contain the status code
				})

				$http.get('http://localhost:8080/proyectoTFM/rest/projects/subcategories'+'?projectId='+projectId).then(function(subcategoriesSelected) {
					project.data.projectSubcategoryList = subcategoriesSelected.data
					subcategoriesSelected.data.forEach(function(subcategorySelected){
						$scope.checkModel[subcategorySelected.subcategoryId]= true;
					})
				}, function(err) {
					console.error('ERR', err);
					// err.status will contain the status code
				})

				$http.get('http://localhost:8080/proyectoTFM/rest/projects/links'+'?projectId='+projectId).then(function(links) {
					project.data.projectLinks = links.data
				}, function(err) {
					console.error('ERR', err);
					// err.status will contain the status code
				})
				$scope.project = project.data;
			}
		}, function(err) {
			console.error('ERR', err);
			// err.status will contain the status code
		});

		$scope.editProject = function() {
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
			if($cookies.get('user') == $scope.project.projectTeacher.email) {
				$scope.incorrectTeacher = false;
			} else {
				$scope.incorrectTeacher = true;
			}
			if(login & rol & !$scope.incorrectTeacher) {
				$scope.project.projectTeacher = $scope.project.projectTeacher.userId;
				$scope.project.projectStudent = $scope.project.projectStudent.userId;
				$scope.project.projectSubcategoryList= $scope.checkResults;
				postService.putProject($scope.project.projectId, $scope.project.projectName, $scope.project.projectCode, $scope.project.projectCareer, $scope.project.projectYear, $scope.project.projectStudent, $scope.project.projectSubcategoryList, $scope.project.projectLinks, $scope.project.projectDocumentation, $scope.project.projectDraft,$scope.project.projectTeacher,
						function(project){
					$scope.projectEdited = true;
					$scope.noEditProject = false;
					//$scope.posts.splice(0,0,offer);
					/*delete $scope.project;
					$('#filedocumentation').fileinput('clear');
					$('#filedraft').fileinput('clear');
					$scope.checkResults = [];
					angular.element(document.querySelectorAll("#MyProjectSubcategories")).removeClass("active");
					$scope.createprojectform.$setUntouched();*/
				},
				function(){
					$scope.noEditProject = true;
					$scope.projectEdited = false; 
				});
			}
		};
	}]);
}());