var searchApp = angular.module("searchApp", []);

searchApp.controller("SearchController", function($scope, pdfSearchService) {

	$scope.submitSearch = function() {
		$scope.results = [];
		var keyword = $scope.searchKeyword;
		pdfSearchService.findResults(keyword, function(results) {
			$scope.results = results;
			$scope.result_text = "No result found";
			$('.results-div').hide();
			if(results.length > 0){
				$('.results-div').show();
				$scope.result_text = "Results of "+keyword;
			}
			$scope.$apply();
		});
	};
});
