let months = ["JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"];
let selectedMonth = document.getElementById("month").innerText;
let selectedYear = parseInt(document.getElementById("year").innerText);
let id = parseInt(document.getElementById("id").value);
let dataValues = document.getElementById("data");
let values = JSON.parse(dataValues.textContent);
google.charts.load("current", { packages: ["corechart"] });
google.charts.load('current', { 'packages': ['line'] });
google.charts.setOnLoadCallback(createPieGraphForBudgetAnalysis);
google.charts.setOnLoadCallback(createIncomeVsExpenseGraph);
google.charts.setOnLoadCallback(createExpenseAnalysis);
google.charts.setOnLoadCallback(createComparisonOverMonths);

function checkMonthAndYear() {
	let currentMonthNumber = new Date().getMonth();
	let currentYear = new Date().getFullYear();
	let currentMonth;
	switch (currentMonthNumber) {
		case 0: currentMonth = "JANUARY";
			break;
		case 1: currentMonth = "FEBRUARY";
			break;
		case 2: currentMonth = "MARCH";
			break;
		case 3: currentMonth = "APRIL";
			break;
		case 4: currentMonth = "MAY";
			break;
		case 5: currentMonth = "JUNE";
			break;
		case 6: currentMonth = "JULY";
			break;
		case 7: currentMonth = "AUGUST";
			break;
		case 8: currentMonth = "SEPTEMBER";
			break;
		case 9: currentMonth = "OCTOBER";
			break;
		case 10: currentMonth = "NOVEMBER";
			break;
		case 11: currentMonth = "DECEMBER";
			break;
		default: console.log("Invalid value");
	}
	if (selectedMonth === currentMonth && currentYear == selectedYear) {
		document.getElementById("monthIncreaseButton").disabled = true;
	}
	else {
		document.getElementById("monthIncreaseButton").disabled = false;
	}
	if (selectedYear < currentYear) {
		document.getElementById("yearIncreaseButton").disabled = false;
	}
	else {
		document.getElementById("yearIncreaseButton").disabled = true;
	}
}

function changeMonth(operation) {
	let index = months.indexOf(selectedMonth);
	if (operation === "+") {
		if (index < 11) {
			selectedMonth = months[index + 1];
		}
		else {
			selectedMonth = months[0];
			selectedYear = selectedYear + 1;
		}
	}
	else {
		if (index > 0) {
			selectedMonth = months[index - 1];
		}
		else {
			selectedMonth = months[11];
			selectedYear = selectedYear - 1;
		}
	}
	submitNewDate();
	document.getElementById("month").innerText = selectedMonth;
}

function changeYear(operation) {
	let currentMonthNumber=new Date().getMonth();
	let currentMonth;
	switch (currentMonthNumber) {
		case 0: currentMonth = "JANUARY";
			break;
		case 1: currentMonth = "FEBRUARY";
			break;
		case 2: currentMonth = "MARCH";
			break;
		case 3: currentMonth = "APRIL";
			break;
		case 4: currentMonth = "MAY";
			break;
		case 5: currentMonth = "JUNE";
			break;
		case 6: currentMonth = "JULY";
			break;
		case 7: currentMonth = "AUGUST";
			break;
		case 8: currentMonth = "SEPTEMBER";
			break;
		case 9: currentMonth = "OCTOBER";
			break;
		case 10: currentMonth = "NOVEMBER";
			break;
		case 11: currentMonth = "DECEMBER";
			break;
		default: console.log("Invalid value");
	}
	let selectedMonthNumber=months.indexOf(selectedMonth);
	if (operation === "+") {
		selectedYear = selectedYear + 1;
		if(selectedMonthNumber>currentMonthNumber){
			selectedMonth=currentMonth;
		}
	}
	else {
		selectedYear = selectedYear - 1;
	}
	submitNewDate();
	document.getElementById("year").innerText = selectedYear;
}

function submitNewDate() {
	let form = document.createElement("form");
	form.method = "POST";
	form.action = "/filter-by-month-and-year";
	let monthInput = document.createElement("input");
	monthInput.type = "hidden";
	monthInput.name = "month";
	monthInput.value = selectedMonth;
	let yearInput = document.createElement("input");
	yearInput.type = "hidden";
	yearInput.name = "year";
	yearInput.value = selectedYear;
	let idInput = document.createElement("input");
	idInput.type = "hidden";
	idInput.name = "id";
	idInput.value = id;
	form.appendChild(monthInput);
	form.appendChild(yearInput);
	form.appendChild(idInput);
	document.body.appendChild(form);
	form.submit();
}

function createPieGraphForBudgetAnalysis() {
	if (values.remainingBudget != 0 && values.totalExpense != 0) {
		let data = google.visualization.arrayToDataTable([
			['Category', 'Amount'],
			['Remaining Budget', values.remainingBudget],
			['Total Expense', values.totalExpense],
		]);
		let options = {
			title: 'Budget Analysis',
			is3D: true,
			titleTextStyle: {
				fontSize: 20,
			},
			legend: {
				textStyle: {
					fontSize: 15,
				},
			}
		};
		let chart = new google.visualization.PieChart(document.getElementById('budgetAnalysis'));
		chart.draw(data, options);
	}
	else {
		let div = document.getElementById('budgetAnalysis');
		let subDiv = document.createElement("div");
		subDiv.style.alignContent = "center";
		subDiv.style.height = "400px";
		let h3 = document.createElement("h3");
		h3.innerText = "No Data";
		h3.style.textAlign = "center";
		subDiv.appendChild(h3);
		div.appendChild(subDiv);
	}

}

function createIncomeVsExpenseGraph() {
	let data = google.visualization.arrayToDataTable([
		['Category', 'Amount'],
		['Income', values.totalIncome],
		['Expense', values.totalExpense],
	]);
	let options = {
		title: 'Income Vs Expense',
		titleTextStyle: {
			fontSize: 20,
		}
	};
	let chart = new google.visualization.BarChart(document.getElementById('incomeVsExpense'));
	chart.draw(data, options);
}

function createExpenseAnalysis() {
	let chartData = [['Expense Type', 'Amount']]
	values.expenseAnalysis.map((element) => chartData.push([element.expenseType, element.amount]))
	let data = google.visualization.arrayToDataTable(chartData);
	let options = {
		title: 'Expense Analysis',
		pieHole: 0.4,
		width: 800,
		titleTextStyle: {
			fontSize: 20,
		},
		legend: {
			textStyle: {
				fontSize: 15,
			},
		}
	};
	let chart = new google.visualization.PieChart(document.getElementById('expenseAnalysis'));
	chart.draw(data, options);
}

function createComparisonOverMonths() {
	let data = new google.visualization.DataTable();
	data.addColumn('string', 'Month');
	data.addColumn('number', 'Income');
	data.addColumn('number', 'Expense');
	data.addColumn('number', 'Savings');
	let chartData = [];
	let arraySize = values.monthlyComparison.length;
	if (arraySize > 0) {
		values.monthlyComparison.map((element) => {
			chartData.push([element.month, element.list[0].amount, element.list[1].amount, element.list[2].amount]);
		})
		data.addRows(chartData);
		let options = {
			chart: {
				title: 'Monthly Financial Overview',
				subtitle: 'in Indian Rupees'
			},
			width: 1000,
			height: 350,
			titleTextStyle: {
				fontSize: 20,
			},
			vAxis: {
				viewWindow: {
					min: 0
				},
				title: 'Amount'
			},
			hAxis: {
				title: 'Month'
			}
		};
		let chart = new google.charts.Line(document.getElementById('comparisonOverMonths'));
		chart.draw(data, google.charts.Line.convertOptions(options));
	}
	else {
		let div = document.getElementById('comparisonOverMonths');
		let subDiv = document.createElement("div");
		subDiv.style.alignContent = "center";
		subDiv.style.height = "400px";
		let h3 = document.createElement("h3");
		h3.innerText = "Insufficient Data Available";
		h3.style.textAlign = "center";
		subDiv.appendChild(h3);
		div.appendChild(subDiv);
	}
}

window.addEventListener("load", checkMonthAndYear);
