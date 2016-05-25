
var app = angular.module('sgpfc');


app.service('postService', ['$http', function ($http) {
	this.postPhoto = function(offerName, offerDescription, offerImage, onSuccess, onError  ){
		var fd = new FormData();
		fd.append('name', offerName);
		fd.append('description', offerDescription);
		fd.append('content', offerImage);
		$http.post('http://localhost:8080/proyectoTFM/rest/offers/photo', fd, {
			transformRequest: angular.identity,
			headers: {'Content-Type': undefined}
		})
		.success(onSuccess)
		.error(onError);
	}
}]);