/*
 * common.js
 *
 *  version --- 3.6
 *  updated --- 2011/09/06
 */


/* !stack ------------------------------------------------------------------- */
jQuery(document).ready(function($) {
	addCss();
	$('#toTop').click(function() {
		$('body,html').animate({scrollTop:0},500);
	});
	$("#gNavi .btn-gnavi").click(function(){
		$("#gNavi .show-mobile").slideToggle();
	});
});

/* !Addition Fitst & Last --------------------------------------------------- */
var addCss = (function(){
	$('.section:first-child:not(:last-child)').addClass('first');
	$('.section:last-child').addClass('last');
	$('li:first-child:not(:last-child)').addClass('first');
	$('li:last-child').addClass('last');
});