$(document).ready(function () {
	$('#queryBtn').click(function () {
		$.post('query', {
			query: $('#query').val()
		}, function (results) {
			console.log('query done');
			console.log(results);
		}, 'text');
	});
});