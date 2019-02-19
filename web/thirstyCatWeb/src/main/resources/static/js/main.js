$(document).ready(function() {
	console.log("halloooo")
	
	$("#catStatsSection").hide();
	
	/* When toggle buttons are clicked, change their appearance */
	$("#toggleButtonTC, #toggleButtonCatStats").click(function() {
		$("#toggleButtonTC").toggleClass("buttonSelected buttonUnselected");
		$("#toggleButtonCatStats").toggleClass("buttonSelected buttonUnselected");
		
		$("#thirstyCatImageSection").toggle("clip");
		$("#catStatsSection").toggle("clip");
	})
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
})


