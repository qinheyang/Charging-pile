export function isEmpty (o) {
	if (o === null || o === undefined || o === '') {
		return true;
	}
	return false;
}

export function parseStrEmpty (str) {
	if (!str || str == "undefined" || str == "null") {
		return "";
	}
	return str;
}
