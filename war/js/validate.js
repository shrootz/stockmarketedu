function validateBuyStock() {
    var ticker = document.forms["buystock"]["Stock Ticker"].value;
    var quantity = document.forms["buystock"]["Number of Shares"].value;
    if (ticker == null || ticker == "") {
        alert("Stock Ticker must be filled in");
        return false;
    }
    if (quantity == null || quantity == "") {
        alert("Number of Shares must be filled in");
        return false;
    }
    if (Number(quantity) != quantity || quantity <= 0) {
        alert("Number of Shares must be a decimal value greater than 0");
        return false;
    }
    return true;
}

function validateSellStock() {
    var ticker = document.forms["sellstock"]["Stock Ticker"].value;
    var quantity = document.forms["sellstock"]["Number of Shares"].value;
    if (ticker == null || ticker == "") {
        alert("Stock Ticker must be filled in");
        return false;
    }
    if (quantity == null || quantity == "") {
        alert("Number of Shares must be filled in");
        return false;
    }
    if (Number(quantity) != quantity || quantity <= 0) {
        alert("Number of Shares must be a decimal value greater than 0");
        return false;
    }
    return true;
}

function validateCreateClass() {
    var cash = document.forms["create"]["Starting Cash"].value;
    var students = document.forms["create"]["Student Emails"].value;
    var stocks = document.forms["create"]["Permitted Stocks"].value;

    if (cash == null || cash == "") {
        alert("Cash must be filled out");
        return false;
    }
    if (students == null || students == "") {
        alert("Student Emails must be filled out");
        return false;
    }
    if (stocks == null || stocks == "") {
        alert("Permitted Stocks must be filled out");
        return false;
    }
	if (Number(cash) != cash || cash <= 0) {
        alert("Starting Cash must be a decimal value greater than 0");
        return false;
    }
    var emails = students.split(" ");
    for(var i = 0; i < emails.length; i++) {
    	if(validateEmail(emails[i]) == false) {
    		alert(emails[i] + " is not a valid email");
    		return false;
    	}
    }
    return true;
}

function validateConfigureClass() {
    var students = document.forms["configure"]["Student Emails"].value;
    var stocks = document.forms["configure"]["Permitted Stocks"].value;

    if ((students == null || students == "") && (stocks == null || stocks == "")) {
        alert("At least one field must be filled out");
        return false;
    }

    var emails = students.split(" ");
    for(var i = 0; i < emails.length; i++) {
    	if(!validateEmail(emails[i])) {
    		alert(emails[i] + " is not a valid email");
    		return false;
    	}
    }   
    return true;
}

function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}
