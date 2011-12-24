

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
	        "bPaginate": true,
	        "bInfo": true,
	        "bSort": true,
	    });
	});

	$(function() {
		$("input[type='text'],input[type='password'],input[type='date'],input[type='datetime'],input[type='number']").addClass("ui-inputfield ui-inputtext ui-widget ui-corner-all");
	});

	$(function() {
		$("input[type='submit']").button();
	});

	$(function() {
		$("input[type='date']").datepicker();
	});

	$(function() {
		$("input[type='datetime']").datetimepicker({
			ampm: true
		});
	});

	$(function() {
		$( ".buttonBox a, .button, #cancel" ).button();
	});

	$(function() {
		$('select').selectmenu({width: 200});
	});

	
});

function reloadSiteMessages(id) {
	$('#siteMessageTable').load('/sitemessages/list?id='+id);
};
