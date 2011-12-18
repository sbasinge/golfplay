

$(document).ready(function() {
//	$("#loading").hide();
//	
//	$("#loading").ajaxStart(function(){
//		console.log("start ajax request");
//		   $(this).show();
//	});
//
//	$("#loading").ajaxStop(function(){
//	      $(this).hide();
//	});

	$(function() {
	    oTable = $('#content #dataTable').dataTable({
	        "bJQueryUI": true,
	        "bLengthChange": false,
	        "bFilter": false,
	        "bPaginate": false,
	        "bInfo": false,
	        "bSort": true,
	    });
	});

	$(function() {
		$( "#tabs1234" ).tabs({
		});
	});

	$(function() {
		$( ".crudNew a" ).button();
	});

	$(function() {
		$( "#crudListAdd a" ).button();
	});

	$(function() {
		$("input[type='text'],input[type='password']").addClass("ui-inputfield ui-inputtext ui-widget ui-corner-all");
	});

	$(function() {
		$("input[type='submit']").button();
	});

});
