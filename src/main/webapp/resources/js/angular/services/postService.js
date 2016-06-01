
var app = angular.module('sgpfc');


app.service('postService', ['$http', function ($http) {
	this.postOffer = function(offerName, offerTinyDescription, offerDescription, offerImage, offerWithLimit, offerTimeLimit, offerPdf, onSuccess, onError  ){
		var fd = new FormData();
		fd.append('name', offerName);
		fd.append('tinydescription', offerTinyDescription);
		fd.append('description', offerDescription);
		fd.append('image', offerImage);
		fd.append('withLimit', offerWithLimit);
		fd.append('timeLimit', offerTimeLimit);
		if (offerPdf != undefined) {
			fd.append('pdf', offerPdf);
		}
		$http.post('http://localhost:8080/proyectoTFM/rest/offers/offer', fd, {
			transformRequest: angular.identity,
			headers: {'Content-Type': undefined}
		})
		.success(onSuccess)
		.error(onError);
	}
	
	this.putOffer = function(offerId, offerName, offerTinyDescription, offerDescription, offerImage, offerWithLimit, offerTimeLimit, offerPdf, onSuccess, onError  ){
		var fd = new FormData();
		fd.append('id', offerId);
		fd.append('name', offerName);
		fd.append('tinydescription', offerTinyDescription);
		fd.append('description', offerDescription);
		fd.append('image', offerImage);
		fd.append('withLimit', offerWithLimit);
		fd.append('timeLimit', offerTimeLimit);
		fd.append('pdf', offerPdf);
		$http.put('http://localhost:8080/proyectoTFM/rest/offers/'+ offerId, fd, {
			transformRequest: angular.identity,
			headers: {'Content-Type': undefined}
		})
		.success(onSuccess)
		.error(onError);
	}
	
	this.postProject = function(projectName, projectCode, projectCareer, projectYear, projectStudent, projectLinks, projectDocumentation, projectDraft, onSuccess, onError  ){
		var fd = new FormData();
		fd.append('name', projectName);
		fd.append('code', projectCode);
		fd.append('career', projectCareer);
		fd.append('year', projectYear);
		fd.append('student', projectStudent);
		fd.append('links', projectLinks);
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