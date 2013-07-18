var searchApp = angular.module("searchApp", [ 'ngTable' ]);

searchApp.controller("SearchController", function($scope, pdfSearchService,
		ngTableParams) {

	$scope.submitSearch = function() {
		$scope.results = [];
		var keyword = $scope.searchKeyword;
		pdfSearchService.findResults(keyword, function(results) {
			$scope.results = results;
			$scope.tableParams = new ngTableParams({
				page : 1, // show first page
				total : results.length, // length of data
				count : 10,
				counts : []
				//count per page
			});
			// watch for changes of parameters
			$scope.$watch('tableParams', function(params) {
				// slice array data on pages
				$scope.results = results.slice((params.page - 1) * params.count, params.page
						* params.count);
			}, true);
		});
	};
});
