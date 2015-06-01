$(document).ready(function () {
	var resultTmpl = _.template('\
		<b><%= origin %></b> to <b><%= destination %></b> in <%= duration %> minutes\
		<span class="arrow">&#9660;</span>\
		<div class="details"><%= airline %></div>\
	');
	var cities = [
	    'Sydney',
	    'Canberra',
	    'Adelaide',
	    'Egypt'
	];
	$('#origin').autocomplete({ source: cities });
	$('#destination').autocomplete({ source: cities });
	$('#date').datepicker({ dateFormat: 'dd/mm/yy' });
	$('#queryBtn').click(function (event) {
		event.preventDefault();
		$.post('query', {
			query: $('#query').val()
		}, function (results) {
			console.log(results);
			$('.results').empty();
			results.forEach(function (i) {
				var result = $('<div>');
				$(result).addClass('result');
				$(result).html(resultTmpl(i));
				$('.details', result).height(0);
				$('.details', result).hide();
				$(result).click(function () {
					$('.details', result).show();
					var height = $('.details', result).height();
					$('.details', result).animate({
						height: height == 100 ? 0 : 100
					}, 200, function () {
						if ($('.details', result).height() == 0)
							$('.details', result).hide();
					});
					console.log($('span.arrow .details', result));
					$('span.arrow', result).toggleClass('down');
				});
				$('.results').append(result);
			});
		}, 'json');
	});
});
