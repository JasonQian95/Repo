/**
 * This File Contains all necessary javascript functions to validate client sign
 * up form.
 */

var req_count; // request variable for countries
var req_state; // request variable for provinces


window.onload = refresh; // on window loading populate country select element

// ---------------------------------Ajax Calls for countries and
// provinces-----------------------------------------

function refresh() {
	document.getElementById("email").value = '';
	document.getElementById("email2").value = '';
	document.getElementById("password").value = '';
	document.getElementById("name").value = '';
	document.getElementById("lastname").value = '';
}

function hide(elem){
	document.getElementById(elem).style.visibility = "hidden";	
}

// -------------------First Email Processing-------------------------

function isEmailEmpty() {
	var val = document.getElementById("email");
	if (val.value == '') {
		$("#email_err").show();
		val.style.borderColor = "red";
		document.getElementById("email_err").style.visibility = "visible";
		document.getElementById("email_err").innerHTML = 'Please Enter a Valid Email Address';
		return true;
    }else{
    	$("#email_err").hide();
		val.style.borderColor = "#dedede";
		return false;
    }
	
}

// -----------------------------Second Email Processing
// -----------------------------------

function isEmail2Empty() {
	var val = document.getElementById("email2");
	if (val.value == '') {
		$("#email2_err").show();
		val.style.borderColor = "red";
		document.getElementById("email2_err").style.visibility = "visible";
		document.getElementById("email2_err").innerHTML = 'Please Re-Enter The Email Address';
		return true;
	}else{
	  	$("#email2_err").hide();
		val.style.borderColor = "#dedede";
		return false;
    }  
}

// -----------------------------Password Processing


function isPassEmpty() {
	
	var val = document.getElementById("password");

	if (val.value == '') {
		val.style.borderColor = "red";
		
		$("#pass_err").show();
		document.getElementById("pass_err").style.visibility = "visible";
		document.getElementById("pass_err").innerHTML = 'Please Enter a Valid Password.';
		return true;
	} else {
		$("#pass_err").hide();
		val.style.borderColor = "#dedede";
		return false;
    }
}


// -----------------------------Name and Last name Processing
// -----------------------------------

function isNameEmpty() {

	var name = document.getElementById("name");
	
	if (name.value == '') {
		
		$("#name_err").show();
		name.style.borderColor = "red";
		document.getElementById("name_err").style.visibility = "visible";
		document.getElementById("name_err").innerHTML = 'Please Enter Teachers Name.';
		return true;
	} else {
		$("#name_err").hide();
		name.style.borderColor = "#dedede";
		return false;
	}
}

function isLastNameEmpty() {

	var lastname = document.getElementById("lastname");

	if (lastname.value == '') {
		
		$("#lastname_err").show();

		lastname.style.borderColor = "red";
		document.getElementById("lastname_err").style.visibility = "visible";
		document.getElementById("lastname_err").innerHTML = 'Please Enter Teachers Last Name.';
		return true;
	} else {
		$("#lastname_err").hide();
		lastname.style.borderColor = "#dedede";
		return false;
		
	}

}

// ------------------------------ form processing before
// submission-----------------------

function isEmail(elem_name) {
	
	var regx = /^[=`{|}~/_a-z0-9A-Z!#$%&'*+-]+([\.][=`{|}~/_a-z0-9A-Z!#$%&'*+-]+)*@[a-z0-9A-Z]+[-]?[a-z0-9A-Z]+([\.]([a-z0-9A-Z]+[-]?[a-z0-9A-Z]+))+$/;
	var element = document.getElementById(elem_name);
	var value = element.value;
	
	if ( !regx.test(value) ){
		$("#"+elem_name+"_err").show();
		element.style.borderColor = "red";
		document.getElementById(elem_name+"_err").style.visibility = "visible";
		document.getElementById(elem_name+"_err").innerHTML = 'You Entered an Invalid Email.';
	}else{
    	$("#"+elem_name+"_err").hide();
		element.style.borderColor = "#dedede";
    }
					
	return regx.test(value);
}

function isPassword() {
	
	var regx = /(?=((.)*[a-z]))(?=((.)*[A-Z]))(?=((.)*[0-9])){8,}/;
	var val = document.getElementById("password");
	
	if ( !regx.test(val.value) ) {
		val.style.borderColor = "red";
		$("#pass_err").show();
		document.getElementById("pass_err").style.visibility = "visible";
		document.getElementById("pass_err").innerHTML = 'Password Must be Between 6-15 Characters, and Consist of at least one UpperCase Letter, '+
		                                                'one LowerCase Letter, one digit.';
	}else {
		$("#pass_err").hide();
		val.style.borderColor = "#dedede";
	}	
	
	
	return regx.test(val.value);
}


function isWord(elem_name) {
	
	var regx = /^[a-zA-Z]+$/;
	
	var element = document.getElementById(elem_name);
	var value = element.value;
		
	if ( !regx.test(value) ){
		$("#"+elem_name+"_err").show();
		element.style.borderColor = "red";
		document.getElementById(elem_name+"_err").style.visibility = "visible";
		document.getElementById(elem_name+"_err").innerHTML = elem_name + ' Can Only Consist Of English Characters.';
	}else{
    	$("#"+elem_name+"_err").hide();
		element.style.borderColor = "#dedede";
    }
					
	return regx.test(value);
	
}


function check_form() {

	var allchecked = 0;
	var email1, email2;

	if ( isEmailEmpty() ) {
		allchecked = -1 ;
    }else if ( !isEmail("email") ) {
    	allchecked = -1 ;
	}
	
	if ( isEmail2Empty()) {
		allchecked = -1 ;
    }else if ( !isEmail("email2") ) {
    	allchecked = -1 ;
	}
	
	if ( isPassEmpty()) {
		allchecked = -1 ;
    }else if ( !isPassword() ) {
    	allchecked = -1 ;    	
    }
	
	if ( isNameEmpty()) {
		allchecked = -1 ;
    }else if ( !isWord("name") ) {
    	allchecked = -1 ;    	
    }
	
	if ( isLastNameEmpty()) {
		allchecked = -1 ;
    }else if ( !isWord("lastname") ) {
    	allchecked = -1 ;    	
    }
			
    var em1 = document.getElementById("email");
    var em2 = document.getElementById("email2");
		
    if ( !(em1.value == em2.value) && !isEmailEmpty() && !isEmail2Empty() ) {
		
		allchecked = -1 ;
		
		$("#email2_err").show();

		em2.style.borderColor = "red";
		document.getElementById("email2_err").style.visibility = "visible";
		document.getElementById("email2_err").innerHTML = 'You Re-Entered a Different Email. Emails Must be Same';
	}    

    if (allchecked != 0) {
		return false;
    }else{
    	return true;    	
    }
	
    
}

