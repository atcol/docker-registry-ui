// This is a manifest file that'll be compiled into application.js.
//
// Any JavaScript file within this directory can be referenced here using a relative path.
//
// You're free to add application-wide JavaScript to this file, but it's generally better 
// to create separate JavaScript files as needed.
//
//= require jquery
//= require_tree .
//= require_self

if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}

var SUCCESS_ALERT_HTML = "<div class='alert alert-dismissable alert-success'> \
  <button type='button' class='close' data-dismiss='alert'>&times;</button> \
  <span id='successMsg'>Done!</span> \
</div>";

var FAIL_ALERT_HTML = "<div class='alert alert-dismissable alert-danger'> \
  <button type='button' class='close' data-dismiss='alert'>&times;</button> \
  <span id='errMsg'>Error!</span> \
</div>";

function showSuccess(msg) {
    $("#banner").prepend(SUCCESS_ALERT_HTML);
    $("#successMsg").innerText = msg
}

function showFail(msg) {
    $("#banner").prepend(FAIL_ALERT_HTML);
    $("#errMsg").innerText = msg
}

