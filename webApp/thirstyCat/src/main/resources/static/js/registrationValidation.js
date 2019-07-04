
$(document).ready(function() {

    $("form[name='registration-form']").validate({

        rules: {
            username: {
                required: true,
                minlength: 4
            },
            password: {
                required: true,
                minlength: 4
            }  
        },
        messages: {
            username: {
                required: "Username is required.",
                minlength: "Username must be at least 4 characters long."
            },
            password: {
                required: "Password is required",
                minlength: "Password must be at least 4 characters long."
            }
        },
        errorClass: "error",
        validClass: "valid"
    })




})