$(document).ready(function () {
	$('#queryBtn').click(function (event) {
		event.preventDefault();
		$.post('query', {
			query: $('#query').val()
		}, function (results) {
			console.log(results);
		}, 'json');
	});
});