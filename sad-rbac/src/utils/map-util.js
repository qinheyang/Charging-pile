export function listToMap (main_atr, list) {
	var maps = {};
	for (let d of list) {
		maps[d[main_atr]] = d;
	}

	return maps;
}