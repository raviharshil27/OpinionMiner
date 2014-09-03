/*

Javascript for App Portfolio
Created by Adrian Pelletier
http://www.designkindle.com

*/

// Begin jQuery
	
$(document).ready(function() {

	/* =Local Scroll
    -------------------------------------------------------------------------- */
    	
    	// Main Navigation
    	
    	$("#mainNav").localScroll();
    	
    	// "To the Top" links
    	
    /* =Cycle - Used for phone slideshow at the top of the page
    -------------------------------------------------------------------------- */
    
    	$("#slideshow").cycle({
			fx: 'fade',
			speed:  1000,
			timeout:  3500
		});

    /* =Featured Work Thumbnail - Realistic Navigation Hover created by Adrian Pelletier - http://www.adrianpelletier.com/2009/05/31/create-a-realistic-hover-effect-with-jquery-ui/
    -------------------------------------------------------------------------- */
    
    	// Append shadow image to normal featured work item
    	
    	//$("#featuredWork").append('<img class="shadow" src="images/work-shadow-lg.jpg" width="290" height="43" alt="" />');
    
    	// Animate buttons, shrink and fade shadow
    	
    	$("#featuredWork").hover(function() {
    		var e = this;
    	    $(e).find("a").stop().animate({ marginTop: "-14px" }, 250, function() {
    	    	$(e).find("a").animate({ marginTop: "-10px" }, 250);
    	    });
    	    $(e).find("img.shadow").stop().animate({ width: "70%", height: "36px", marginLeft: "50px", opacity: 0.375 }, 250);
    	},function(){
    		var e = this;
    	    $(e).find("a").stop().animate({ marginTop: "4px" }, 250, function() {
    	    	$(e).find("a").animate({ marginTop: "0px" }, 250);
    	    });
    	    $(e).find("img.shadow").stop().animate({ width: "100%", height: "43px", marginLeft: "0", opacity: 1 }, 250);
    	});
    	
    /* =Small Work Thumbnails - Realistic Navigation Hover created by Adrian Pelletier - http://www.adrianpelletier.com/2009/05/31/create-a-realistic-hover-effect-with-jquery-ui/
    -------------------------------------------------------------------------- */
    
    	// Append shadow image to normal sized work item
    	
    	//  $("#workThumbs span").not("#featuredWork").append('<img class="shadow" src="images/work-shadow-sm.jpg" width="148" height="43" alt="" />');
    
    	// Animate buttons, shrink and fade shadow
    	
    	$("#workThumbs span").not("#featuredWork").hover(function() {
    		var e = this;
    	    $(e).find("a").stop().animate({ marginTop: "-14px" }, 250, function() {
    	    	$(e).find("a").animate({ marginTop: "-10px" }, 250);
    	    });
    	    $(e).find("img.shadow").stop().animate({ width: "70%", height: "33px", marginLeft: "23px", opacity: 0.375 }, 250);
    	},function(){
    		var e = this;
    	    $(e).find("a").stop().animate({ marginTop: "4px" }, 250, function() {
    	    	$(e).find("a").animate({ marginTop: "0px" }, 250);
    	    });
    	    $(e).find("img.shadow").stop().animate({ width: "100%", height: "43px", marginLeft: "0", opacity: 1 }, 250);
    	});

    /* =Carousel
    -------------------------------------------------------------------------- */
    
    	// work thumbnails
    
    	var thumbsCallback = function(visible){
    		// If current group of thumbs is the last set, disable "next" button
    		if($(visible).hasClass("last")){
    			$("#nextWork").addClass("disabled");
    		}
    		// If current group of thumbs is first set, disable "prev" button
    		if($(visible).hasClass("first")){
    			$("#prevWork").addClass("disabled");
    		}
    	}
    
    	$("#workThumbs").jCarouselLite({
    		btnNext: "#nextWork",
    		btnPrev: "#prevWork",
    		speed: 900,
    		visible: 1,
    		circular: false,
    		easing: "easeInOutExpo",
            afterEnd: thumbsCallback
    	});
    	
    	// testimonials
    	
    	var testimonialsCallback = function(visible){
    		// If current group of thumbs is the last set, disable "next" button
    		if($(visible).hasClass("last")){
    			$("#nextTestimonials").addClass("disabled");
    		}
    		// If current group of thumbs is first set, disable "prev" button
    		if($(visible).hasClass("first")){
    			$("#prevTestimonials").addClass("disabled");
    		}
    	}
    
    	$("#testimonials").jCarouselLite({
    		btnNext: "#nextTestimonials",
    		btnPrev: "#prevTestimonials",
    		speed: 900,
    		visible: 1,
    		circular: false,
    		easing: "easeInOutExpo",
            afterEnd: testimonialsCallback
    	});
    	
    /* =FancyBox Work Thumbnails
    -------------------------------------------------------------------------- */


    /* =Social links
    -------------------------------------------------------------------------- */
	
		$(".social li").hover(function(){
			$(this).find("a").stop().animate({ marginTop: "-5px" }, 200);
		},function(){
			$(this).find("a").stop().animate({ marginTop: "0" }, 200);
		});
		
	/* =Clear Form Fields
    -------------------------------------------------------------------------- */
		
		$("input, textarea").searchField();
						
// End document ready
	
});
