/**
 * webapp/js/member/login.js
 */
 /* 아이디 저장기능 관련 */
 var checkBox = document.getElementById("customCheck");
 var inputId = document.getElementById("userid");
 window.onload=function(){	
 	/* localStorage에  저장된 id가 있는 경우, 표시 */
 	if(window.localStorage){
 		if(localStorage.getItem('uid')){
 			inputId.value=localStorage.getItem('uid');
 			checkBox.checked=true;
 		}
 	}
 }
 // 체크박스 클릭 시 브라우저 localstorage에 저장 또는 삭제
 checkBox.addEventListener("click", function(e){
 	if(window.localStorage){
		if(inputId.value != ''){
			if(checkBox.checked){
				localStorage.setItem("uid", inputId.value);
			}else{
				localStorage.removeItem("uid");
			}
			
		}else{
			alert("아이디를 입력해 주세요.")
 			checkBox.checked=false;
 			return false;
		}	
 	}else{
 		alert("브라우저가 해당 기능을 지원하지 않습니다.")
		return false;
 	}
 }, false);
 
 /* 비밀번호 란에 엔터 입력 시 이벤트 */
 var pwd = document.getElementById("userpw");
 pwd.addEventListener("keyup", function(e){
 	e.preventDefault();
 	if(e.keyCode === 13){
 		login();
 	} 
 });
 
/* 브라우저에 따른 XMLHTTPRequest 생성  */ 
var xhr = null;
function xhrTypeCheck(){
	if(window.ActiveXObject){
		try{
			xhr = ActiveXObject("Msxml2.XMLHTTP");
		}catch(e){
			xhr = ActiveXObject("Microsoft.XMLHTTP");
		}
		console.log("ActiveXObject =>"+xhr);
	}else if(window.XMLHttpRequest){
		xhr = new window.XMLHttpRequest();
		console.log("window.XMLHttpRequest =>"+xhr);
	}else{
		xhr = new XMLHttpRequest();
		console.log("XMLHttpRequest =>"+xhr);
	}
}

/* 로그인버튼 동작 */
function login() {
	xhrTypeCheck();
	var idv = document.getElementById("userid").value;
	var pwdv = document.getElementById("userpw").value;
	var param = "userid="+idv;
		param += "&userpw="+pwdv;
	if(idv == ""){
		alert("아이디를 입력해 주세요.");
		return false;
	}
	if(pwdv == ""){
		alert("비밀번호를 입력해 주세요.");
		return false;
	}	
	xhr.onreadystatechange= res;
	xhr.open("post","loginprocess.do", true);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send(param);
}

/* 콜백 함수 */
function res() {
	if(xhr.readyState ===4){
		if(xhr.status ===200){
			var txt =xhr.responseText;
			if(txt=="false") {
				alert("아이디 혹은 비밀번호가 잘못 되었습니다.");
				document.getElementById("userid").focus();
			} else {
				location="/ojt/";
			}
		} else {
			console.log("실패");
		}
	}
}


