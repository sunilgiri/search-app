function downloadFile(result) {
	var filePath = result.url;
	$.ajax({
		type : "GET",
		processData : false,
		url : 'export?filePath=' + filePath,
		contentType : 'application/pdf; charset=utf-8',
		success : function(data) {
			console.log("File Downloaded Successfully");
		}
	});
}