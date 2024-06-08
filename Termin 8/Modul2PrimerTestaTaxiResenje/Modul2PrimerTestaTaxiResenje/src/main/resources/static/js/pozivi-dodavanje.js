window.onload = function() {
	let datumIVreme = document.querySelector("input[name=datumIVreme]");
	let ulica = document.querySelector("input[name=ulica]");
	let broj = document.querySelector("input[name=broj]");
	let voziloId = document.querySelector("select[name=voziloId]");

	let button = document.querySelector("button");

	function validacijaDatumIVreme() {
		return new Date(datumIVreme.value) <= new Date();
	}	
	function validacijaUlica() {
		return ulica.value != "";
	}
	function validacijaBroj() {
		return parseInt(broj.value) > 0;
	}
	function validacijaVoziloId() {
		return voziloId.value > 0;
	}
	function validacija() {
		return validacijaDatumIVreme() && validacijaUlica() && validacijaBroj() && validacijaVoziloId();
	}

	let datumIVremeCallback = function(event) {
		let error = event.target.nextElementSibling;
		error.textContent = validacijaDatumIVreme()? "": "Datum i vreme ne smeju biti u budućnosti!";

		button.disabled = !validacija();
	};
	datumIVreme.onchange = datumIVremeCallback;
	datumIVreme.onkeyup = datumIVremeCallback;
	datumIVreme.onblur = datumIVremeCallback;

	let ulicaCallback = function(event) {
		let error = event.target.nextElementSibling;
		error.textContent = validacijaUlica()? "": "Ulica ne sme biti prazna!";

		button.disabled = !validacija();
	};
	ulica.onchange = ulicaCallback;
	ulica.onkeyup = ulicaCallback;
	ulica.onblur = ulicaCallback;

	let brojCallback = function(event) {
		let error = event.target.nextElementSibling;
		error.textContent = validacijaBroj()? "": "Broj mora biti pozitivan!";

		button.disabled = !validacija();
	};
	broj.onchange = brojCallback;
	broj.onkeyup = brojCallback;
	broj.onblur = brojCallback;

	let voziloIdCallback = function(event) {
		let error = event.target.nextElementSibling;
		error.textContent = validacijaVoziloId()? "": "Vozilo mora biti odabrano!";
	
		button.disabled = !validacija();
	};
	voziloId.onchange = voziloIdCallback;
	voziloId.onkeyup = voziloIdCallback;
	voziloId.onblur = voziloIdCallback;

	document.querySelector("form").onsubmit = function(event) {
		if (!validacija()) {
			event.preventDefault();
		}
	}

	button.disabled = !validacija();
}

