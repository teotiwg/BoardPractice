/**
 * webapp/js/header.js
 */
 // 검색기능
 
 var srchInput = document.getElementById("srch");
 srchInput.addEventListener("keyup", function(e){
	  var keyword = srchInput.value;
	  if(e.keyCode === 13){
		  location.href = "srchAll.do?key="+keyword;
	  }
  });