export function handleTree (list) {
	var tree = [];
	var childListMap = {};
	var nodes = {};

	for (let d of list) {

		if (childListMap[d.menuId] == null) {
			childListMap[d.menuId] = [];
		}
		if (d.parentId == null) {
			tree.push(d);
		}
		if (d.parentId != null) {

			if (childListMap[d.parentId] == undefined) {
				childListMap[d.parentId] = [];
			}
			childListMap[d.parentId].push(d);
		}
		nodes[d.menuId] = d;

	}
	appendChild(tree);
	function appendChild (data) {
		for (let t of data) {
			if (childListMap[t.menuId] != null) {
				t.children = childListMap[t.menuId];
				appendChild(t.children);
			}
		}

	}
	return tree;

}
