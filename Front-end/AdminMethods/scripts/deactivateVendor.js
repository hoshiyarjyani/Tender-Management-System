function deactivate() {
	const vendorId = document.getElementById("vendorId").value;

	const url = `http://localhost:8080/deactivateVendor/${vendorId}`;

	fetch(url, {
		method: "PATCH",
	})
		.then((response) => response.text())
		.then((result) => {
			alert("Vendor deactivated.");
		})
		.catch((error) => {
			alert(error);
		});
}
