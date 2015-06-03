$(document).ready(function () {
	/*var resultTmpl = _.template('\
		<b><%= origin %></b> to <b><%= destination %></b> in <%= duration %> minutes\
		<span class="arrow">&#9660;</span>\
		<div class="details"><%= airline %></div>\
	');*/
	var resultTmpl = _.template('\
		<b>Option <%= n %></b> <%= Math.floor(duration/60) %> hours, <%= Math.floor(duration%60) %> minutes, $<%= price %>, <%= Math.floor(points/60) %> frequent flyer points\
		<span class="arrow">&#9660;</span>\
		<div class="details">\
			<% _.each(flights, function (flight) { %>\
				Depart:\
				<%= flight.date %> <%= flight.time %>\
				<%= flight.origin %> to <%= flight.destination %> - <%= flight.airline %> (<%= Math.floor(flight.duration/60) %> hours,	<%= flight.duration%60 %> mins)\
				<br/>\
			<% }) %>\
		</div>\
	');
	$.get('cities', function (results) {
		$('#origin').autocomplete({ source: results });
		$('#destination').autocomplete({ source: results });
	})
	
	$.get('airlines', function (airlines) {
		$('#airline').autocomplete({ source: airlines });
	})
	
	$('#date').datepicker({ dateFormat: 'dd/mm/yy' });
	$('#queryBtn').click(function (event) {
		event.preventDefault();
		$.post('query', {
			origin: $('#origin').val(),
			destination: $('#destination').val(),
			date: $('#date').val(),
			time: $('#time').val(),
			preference1: $('#preference1').val(),
			preference2: $('#preference2').val(),
			preference3: $('#preference3').val(),
			airline: $('#airline').val(),
			ips: $('#ips').val()
		}, function (results) {
			console.log(results);
			$('.results').empty();
			var n = 1;
			results.forEach(function (i) {
				var result = $('<div>');
				$(result).addClass('result');
				i.n = n++;
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
