
var app = angular.module('sgpfc');


app.service('postService', ['$http', function ($http) {
	this.postOffer = function(offerName, offerDescription, content, offerWithLimit, offerTimeLimit, onSuccess, onError  ){
		var fd = new FormData();
		fd.append('name', offerName);
		fd.append('description', offerDescription);
		fd.append('content', content);
		fd.append('withLimit', offerWithLimit);
		fd.append('timeLimit', offerTimeLimit);
		$http.post('http://localhost:8080/proyectoTFM/rest/offers/offer', fd, {
			transformRequest: angular.identity,
			headers: {'Content-Type': undefined}
		})
		.success(onSuccess)
		.error(onError);
	}
}]);