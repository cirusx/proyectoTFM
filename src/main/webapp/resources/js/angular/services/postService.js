
var app = angular.module('sgpfc');


app.service('postService', ['$http', function ($http) {
	this.postOffer = function(offerName, offerDescription, offerImage, offerWithLimit, offerTimeLimit, onSuccess, onError  ){
		var fd = new FormData();
		fd.append('name', offerName);
		fd.append('description', offerDescription);
		fd.append('image', offerImage);
		fd.append('withLimit', offerWithLimit);
		fd.append('timeLimit', offerTimeLimit);
		$http.post('http://localhost:8080/proyectoTFM/rest/offers/offer', fd, {
			transformRequest: angular.identity,
			headers: {'Content-Type': undefined}
		})
		.success(onSuccess)
		.error(onError);
	}
	this.postProject = function(projectName, projectCareer, projectYear, projectStudent, projectDocumentation, projectDraft, onSuccess, onError  ){
		var fd = new FormData();
		fd.append('name', projectName);
		fd.append('career', projectCareer);
		fd.append('year', projectYear);
		fd.append('student', projectStudent);
		fd.append('documentation', projectDocumentation);
		fd.append('draft', projectDraft);
		$http.post('http://localhost:8080/proyectoTFM/rest/projects/project', fd, {
			transformRequest: angular.identity,
			headers: {'Content-Type': undefined}
		})
		.success(onSuccess)
		.error(onError);
	}
}]);