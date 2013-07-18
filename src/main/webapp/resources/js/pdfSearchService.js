angular
		.module('searchApp')
		.factory(
				'pdfSearchService',
				function() {

					var service = {};

					/**
					 * @param keyword -
					 *            The keyword to search for.
					 * @param callback -
					 *            A function to call when the search is
					 *            complete. The function will be passed a single
					 *            argument which is an array of photo items
					 *            matching the keyword.
					 */
					service.findResults = function(keyword, callback) {

						var matches = [];

						if (keyword) {
							$.ajax({
								type: "GET",
								processData: false,
								url: 'search?query='+keyword,
								contentType: 'application/json; charset=utf-8',
								dataType: 'json',
								success: function(data) {	
									matches = data.aaData;
								},
								error: function(data) {
								// alert ("Error");
								}
							});
						}
						callback(matches);
					};

					return service;

				});
