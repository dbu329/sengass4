$(document).ready(function () {
	var resultTmpl = _.template('\
		<%= origin %> to <%= destination %> in <%= duration %> minutes\
		<span class="arrow">&#9660;</span>\
		<div class="details"></div>\
	');
	
	$('#test').prop('checked', true);
	
	
	$('#queryBtn').click(function (event) {
		event.preventDefault();
		$.post('query', {
			query: $('#query').val()
		}, function (results) {
			console.log(results);
			$('.results').empty();
			results.forEach(function (i) {
				var result = $('<div>');
				result.addClass('result');
				result.html(resultTmpl(i));
				result.click(function () {
					var height = $('.details', result).height();
					$('.details', result).animate({
						height: height == 100 ? 0 : 100
					}, 200);
					console.log($('span.arrow .details', result));
					$('span.arrow', result).toggleClass('down');
				});
				$('.results').append(result);
			});
		}, 'json');
	});
});