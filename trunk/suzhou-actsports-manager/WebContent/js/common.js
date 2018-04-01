function hasClass(obj, cls) {
	return obj.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));
}

function addClass(obj, cls) {
	if(!this.hasClass(obj, cls)) obj.className += " " + cls;
}

function removeClass(obj, cls) {
	if(hasClass(obj, cls)) {
		var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');
		obj.className = obj.className.replace(reg, ' ');
	}
}

function creatNode(org, classes) {
	mui.each(org, function(index, items) {
		var errorNode = document.createElement('p');
		errorNode.className = classes;
		items.appendChild(errorNode);
	});
}

function message(org, tip) {
	var org = org.parentNode.lastChild;
	org.parentNode.lastChild.innerText = tip;
}

function isAccount(org){
	if(org.value==''){
		message(org,'户号不能为空');
		return false;
	}else{
		message(org,'');
	}
	
}


function onlyNum(org){
	var value = org.value;
	if(isNaN(value))
	document.execCommand('undo')
}


