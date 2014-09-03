/* =jcarouselite
============================================================================== */
	
$(document).ready(function() {

	/* Past Events page
	-------------------------------------------------------------------------- */
	
		$("#past-event-photos .previews").jCarouselLite({
	    	visible: 4,
   		 	start: 0,
   		 	scroll: 4,
	        btnNext: ".next",
	        btnPrev: ".prev",
	        easing: "easeInOutQuart",
	        speed: 800
	    });



	    
}); // end jQuery ready
