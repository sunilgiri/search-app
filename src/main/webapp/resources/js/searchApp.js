var searchApp = angular.module("searchApp", ['ui.bootstrap']);

searchApp.directive('fileUpload', function () {
    return {
        scope: true,        //create a new scope
        link: function (scope, el, attrs) {
            el.bind('change', function (event) {
                var files = event.target.files;
                //iterate files since 'multiple' may be specified on the element
                for (var i = 0;i<files.length;i++) {
                    //emit event upward
                    scope.$emit("fileSelected", { file: files[i] });
                }                                       
            });
        }
    };
});

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
    //listen for the file selected event
	$scope.files = [];
    $scope.$on("fileSelected", function (event, args) {
        $scope.$apply(function () {            
            //add the file object to the scope's files collection
        	var file = args.file;
        	if(file.type == "application/pdf") file.response = "resources/images/success.png"
        	else  file.response = "resources/images/error.png"
        	$scope.files.push(file);
        });
    });
    $scope.deleteFile = function(index) {
    	$scope.files.splice(index,1)
    };
});
