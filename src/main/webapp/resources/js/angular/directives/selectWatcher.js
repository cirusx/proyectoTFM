(function () {
	'use strict';

	var app= angular.module('sgpfc');
	app.directive('selectWatcher', function ($timeout) {
		return {
			link: function (scope, element, attr) {
				var last = attr.last;
				if (last === "true") {
					$timeout(function () {
						$(element).parent().selectpicker('val', 'any');
						$(element).parent().selectpicker('refresh');
					});
				}
			}
		};
	});
}());