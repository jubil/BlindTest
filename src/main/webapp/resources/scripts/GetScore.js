$.ajax({
    type:"GET", 
    url: "http://localhost:8080/BlindTest/StatistiquePartie"
}).done(function(data) {
	/*console.log(data);
	var d = data.yourClassement;
	$("#scorePerso").append('<tr id="lignePerso"><td id="pos">Score</td><td><p id="ligneClass">'+d.pseudo+'</p></td><td>'+d.point+' pts</td></tr>');
	console.log("ok"+data.yourClassement.pseudo);*/

	
	data.classement.sort((a,b)=>{
		return (a.point < b.point) ? 1 : -1	
	})

	var i = 0;
	for (var value of data.classement) {
		if (value.pseudo == data.yourClassement.pseudo) {
			$("#scoreFin").append('<tr id="lignePerso"><td id="pos">'+(++i)+'</td><td><p id="ligneClass">'+value.pseudo+'</p></td><td>'+value.point+' pts</td></tr>');
		}
		else {
			$("#scoreFin").append('<tr><td id="pos">'+(++i)+'</td><td><p id="ligneClass">'+value.pseudo+'</p></td><td>'+value.point+' pts</td></tr>');
		}
	}
})
