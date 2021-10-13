			function logout(){
				var result = confirm("로그아웃 하시겠습니까?");
				if(result){
					location.href = "logout.do";
				}else{
					location.href = "#";
				}
			}

			function viewComm() {
				var view = document.getElementsByClassName("view")
				var v = view.innertext;
			    return v.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
			}
			
			function likeComm(num) {
				var like = document.getElementsByClassName("like")
				var l = like.innertext;
			    return l.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
			}
			
			function selectOrder(order){ 
		    	//obj.submit(); //obj자체가 form이다.
		    	//var page = parseInt(val);
		    	var order = order.value;
		    	location.href="/ojt/home.do?flag=${flag}&order="+order;	
		    	//location.href="/ojt/home.do?flag=${flag}&order="+order+"&pageShow=${pageC}";	
			}