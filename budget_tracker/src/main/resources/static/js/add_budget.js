function checkExpense() {
	let selectedValue = document.getElementById("category").value;
	let expenseType = document.getElementById("expenseType");
	if (selectedValue === "EXPENSE") {
		expenseType.disabled = false;
	}
	else {
		expenseType.disabled = true;
	}
}

function setDate() {
	let date = new Date();
	let day = date.getDate();
	let month = date.getMonth() + 1;
	let year = date.getFullYear();
	let dayString = "";
	let monthString = "";
	if (day < 10) {
		dayString = "0" + day;
	}
	else {
		dayString = day;
	}
	if (month < 10) {
		monthString = "0" + month;
	}
	else {
		monthString = month;
	}
	let dateString = year + "-" + monthString + "-" + dayString;
	document.getElementById("date").setAttribute("max", dateString);
}

window.addEventListener("load", setDate);