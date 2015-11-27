/**
 * 
 */

$(document).ready(function() {
    var text_max = 255;
    var textareafeedback = $('.textarea_feedback'); 
    textareafeedback.html(text_max + ' characters remaining');

    $('.textarea').keyup(function() {
        var text_length = $('.textarea').val().length;
        var text_remaining = text_max - text_length;
        
        console.log(text_remaining + ' characters remaining');
        textareafeedback.html(text_remaining + ' characters remaining');
    });
});