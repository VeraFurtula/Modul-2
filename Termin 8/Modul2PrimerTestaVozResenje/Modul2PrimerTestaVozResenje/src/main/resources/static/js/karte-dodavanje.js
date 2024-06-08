window.onload = function() {
	let vozId = document.querySelector("select[name=vozId]");
	let datumIVremeProdaje = document.querySelector("input[name=datumIVremeProdaje]");
	let kupac = document.querySelector("input[name=kupac]");
	let razred = document.querySelector("input[name=razred]");

	let button = document.querySelector("button");

	function validacijaVozId() {
		return vozId.value > 0;
	}
	function validacijaDatumIVremeProdaje() {
		return new Date(datumIVremeProdaje.value) <= new Date();
	}
	function validacijaKupac() {
		return kupac.value != "";
	}
	function validacijaRazred() {
		let vrednost = parseInt(razred.value);
		return vrednost > 0 && vrednost <= 2;
	}
	function validacija() {
		return validacijaVozId() && validacijaDatumIVremeProdaje() && validacijaKupac() && validacijaRazred();
	}

	let vozIdCallback = function(event) {
		let error = event.target.nextElementSibling;
		error.textContent = validacijaVozId()? "": "Voz mora biti odabran!";
	
		button.disabled = !validacija();
	};
	vozId.onchange = vozIdCallback;
	vozId.onblur = vozIdCallback;
	vozId.onkeyup = vozIdCallback;

	let datumIVremeProdajeCallback = function(event) {
		let error = event.target.nextElementSibling;
		error.textContent = validacijaDatumIVremeProdaje()? "": "Datum i vreme prodaje ne smeju biti u budućnosti!";

		button.disabled = !validacija();
	};
	datumIVremeProdaje.onchange = datumIVremeProdajeCallback;
	datumIVremeProdaje.onblur = datumIVremeProdajeCallback;
	datumIVremeProdaje.onkeyup = datumIVremeProdajeCallback;

	let kupacCallback = function(event) {
		let error = event.target.nextElementSibling;
		error.textContent = validacijaKupac()? "": "Kupac ne sme biti prazan!";

		button.disabled = !validacija();
	};
	kupac.onchange = kupacCallback;
	kupac.onblur = kupacCallback;
	kupac.onkeyup = kupacCallback;

	let razredCallback = function(event) {
		let error = event.target.nextElementSibling;
		error.textContent = validacijaRazred()? "": "Razred mora biti vrednost od 1 do 2!";

		button.disabled = !validacija();
	};
	razred.onchange = razredCallback;
	razred.onblur = razredCallback;
	razred.onkeyup = razredCallback;

	document.querySelector("form").onsubmit = function(event) {
		if (!validacija()) {
			event.preventDefault();
		}
	}

	button.disabled = !validacija();
}

