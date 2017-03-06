(function(){
	'use strict'

	var currentSong = {},
		currentScore = [0,0],
		score = 0,
		responseT0 = 0,
		responseT1 = 0

	var preSong_interval = -1,
		song_interval = -1

	$(document).ready(function(){
		requestSong()
	})

	$(document).on('submit', '#submit-answer', function(e){
		e.preventDefault()
		e.stopPropagation()

		var answer = $('input[name="answer"]').val(),
			title = currentSong.chanson.titre,
			author = currentSong.chanson.auteur

		console.log(title, "-", author, "-", answer)
			
		//console.log('title', compareStrings(title, answer))
		//console.log('author', compareStrings(author, answer))

		let titleComparison, authorComparison
		try { titleComparison = compareStrings(title, answer) } catch() {}
		try { authorComparison = compareStrings(author, answer) } catch() {}
		
		if(titleComparison[0] > 75 || titleComparison[1] > 75) {
			currentScore[0]=1
		} else if(authorComparison[0] > 75 || authorComparison[1] > 75) {
			currentScore[1]=1
		}
		updateScoreFrame()
		$('input[name="answer"]').val('').focus()
	})

	function updateScoreFrame() {
		if(currentScore.join('')==='10') {
			$('.current-score').removeClass('green blue orange white').addClass('blue').html('<i class="material-icons white-text medium" style="font-size: 2.75em">music_note</i>')
		} else if(currentScore.join('')==='01') {
			$('.current-score').removeClass('green blue orange white').addClass('green').html('<i class="material-icons white-text medium" style="font-size: 2.75em">mic</i>')
		} else if(currentScore.join('')==='11') {
			$('.current-score').removeClass('green blue orange white').addClass('orange').html('')
			responseT1 = performance.now()
			console.log(responseT1 - responseT0)
		}
	}

	function requestSong() {
		$.ajax({
			type: 'GET',
			url: 'http://localhost:8080/BlindTest/GestionPartie'
		}).done(function(json) {
			/**/console.log(json)

			$.ajax({
			type: 'POST',
			url: 'http://localhost:8080/BlindTest/StatistiquePartie',
			data: { "find": score, "findingTime": (responseT1-responseT0) }
			}).done(function(json_) {
				$('.nickname').html(json_.yourClassement.pseudo)
				processSong(json)
			})
		})
	}

	function processSong(json) {
		$('.index-song').html(++json.indexPlaylist)
		$('.general-score').html(0)
		currentSong = json

		/**/console.log('starting song in '+json.waitingTime+'ms')
		let w = 0
		setTimeout(playSong, json.waitingTime)
		preSong_interval = setInterval(function() {
			w+=100/(currentSong.waitingTime/1000)
			$('.determinate').css('width', (w/10)+'%')
		}, 100)
	}

	function playSong() {
		/**/console.log('playing ', currentSong.chanson.auteur, currentSong.chanson.titre)
		$('input[name="answer"]').prop('disabled', false)
		$('input[name="answer"]').focus()
		console.log("*** TAMER", currentSong.chanson.adresseChanson)
		$('body').append('<audio autoplay><source src="'+currentSong.chanson.adresseChanson+'" type="audio/mp3"></audio>')
		$('.determinate').css('width', '0%')
		clearInterval(preSong_interval)
		responseT0 = performance.now()
		let w = 0
		song_interval = setInterval(function() {
			w+=5
			$('.determinate').css('width', (w/10)+'%')
			if((w/10)==100) clearInterval(song_interval), setTimeout(postSongProcess, 1000)
		}, 100)
	}

	function postSongProcess() {
		let score = -1;
		$('input[name="answer"]').prop('disabled', true)
		if(currentScore.join('')==='10') {
			$($('.score-overview').find('td')[--currentSong.indexPlaylist]).addClass('blue')
			score=1
		} else if(currentScore.join('')==='01') {
			$($('.score-overview').find('td')[--currentSong.indexPlaylist]).addClass('green')
			score=2
		} else if(currentScore.join('')==='11') {
			$($('.score-overview').find('td')[--currentSong.indexPlaylist]).addClass('orange')
			score=3
		}

		/**/console.log("**score", score)

		updateHistory()

		$.ajax({
			type: 'POST',
			url: 'http://localhost:8080/BlindTest/StatistiquePartie',
			data: { "find": score, "findingTime": (responseT1-responseT0) }
		}).done(function(json) {
			/**/console.log(json)

			$('.current-score').removeClass('green blue orange').addClass('white').html('')
			currentScore = [0,0]

			updateScores(json)


			if(currentSong.indexPlaylist==15) {
				clearPanels()
			}
			requestSong()
		})
	}

	function clearPanels() {
		$('.history').html('')
		$('.score-all').html('')
		$('.score-overview').find('td').removeClass('blue green orange white')
	}

	function updateHistory() {
		$('.history').append(`
			<tr class="history-detail">
				<td class="cover"><img src="${currentSong.chanson.imgAlbum}" /></td>
				<td class="info">
					<span class="bold orange-text text-lighten-2">${currentSong.chanson.auteur}</span><br/>
					<span>${currentSong.chanson.titre}</span>
				</td>
			</tr>`)
	}

	function updateScores(json) {
		$('.general-score').html(json.yourClassement.point)
		let scores_all = json.classement
		scores_all.sort((a,b)=>{
			return (a.point < b.point) ? 1 : -1
		})
		$('.score-all').html('')
		for(let player of scores_all)
			$('.score-all').append('<tr><td>'+player.pseudo+' <span class="score lightgrey-text italic">'+player.point+' pts.</span></td></tr>')
	}

	function compareStrings(source, input) {
		console.log("****", source, input)
		source = source || ''
		input = input || ''
		let a = Math.round(100 * (1 - (getEditDistance(source, input) / source.length))),
			rate1 = a,
			arr = source.split(' '),
			arr_ = input.split(' '),
			moyenne = 0,
			j = 0
		arr.forEach((e, i) => {
			var a = getEditDistance(e, arr_[i])
			moyenne += a / e.length
			j++
		})
		let rate2 = Math.round(100 * (1 - (moyenne / j)))
		return [rate1, rate2]
	}
	function getEditDistance(a, b) {
		if (a.length == 0 || b.length == 0) return NaN;

		let matrix = [];
		for (var i = 0; i <= b.length; i++)
			matrix[i] = [i];
		for (var j = 0; j <= a.length; j++)
			matrix[0][j] = j;

		for (i = 1; i <= b.length; i++)
			for (j = 1; j <= a.length; j++)
				if (b.charAt(i - 1) == a.charAt(j - 1))
					matrix[i][j] = matrix[i - 1][j - 1];
				else
					matrix[i][j] = Math.min(matrix[i-1][j-1]+1,
													Math.min(matrix[i][j-1]+1,
																matrix[i-1][j]+1));
		return matrix[b.length][a.length];
	}
})()