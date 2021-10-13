			//let res;
        	let res = "${boardVO.likescount}";

        	$(function(){
        		likeList();
        	});
        	
			function likeList() {
				
				var likeVO = {};
			 	likeVO.userid = "<c:out value='${sessionScope.sessionID}' />";
				likeVO.b_idx = "<c:out value='${boardVO.b_idx}' />";
			
				$.ajax({
				
			        url:'/ojt/likeList.do',
			        type : 'POST',
			        data :  likeVO,
			        dataType:'html', //'json'
			        success :  function(result){
						console.log("likeList 성공");
			        },
			        error : function(request, status, error) {
			        	console.log("likeList 실패");
			        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
			        }
			    });
			}
		
			
			function likeCreate() {
		
	 		if(${sessionScope.sessionID == null}) {
	            alert("로그인 후 이용해주세요.");
	            location.href="login.do";
	        }
	 		else {
				var likeVO = {};
			
				likeVO.userid = "<c:out value='${sessionScope.sessionID}' />";
				likeVO.b_idx = "<c:out value='${boardVO.b_idx}' />";
				
				$.ajax({
					url : '/ojt/addlike.do',
					type : 'POST',
					data : likeVO,
					dataType:'html', //'json'
					success : function(result) {
						console.log("likeCreate Ajax 성공");
						likeList();
						
						//let res= "${boardVO.likescount + 1}";
						res = Number(res) + 1;
						if(res < 0)
							res = 0;
						let res2 = "<i class='material-icons'style='color:red;font-size:12pt;''>&#xe87d;</i>&nbsp;"; 
						let res3 = "<button class='btn btn-danger' type='button' onclick='likeRemove();' >좋아요</button>&nbsp;&nbsp;";
						
						$('#likeSpan').empty();
						$('#likeSpan').append(res2 + res);
						
						$('#likeBtnSpan').empty();
						$('#likeBtnSpan').append(res3);

					},
					error : function(request, status, error) {
						console.log("like추가 실패");
						alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
					    }
					});
		 		}
			}
		
			function likeRemove(){
				
				var likeVO = {};
				likeVO.userid = "<c:out value='${sessionScope.sessionID}' />";
				likeVO.b_idx = "<c:out value='${boardVO.b_idx}' />";
				
				$.ajax({
					url: '/ojt/removelike.do',
					type: 'POST',
					data: likeVO,
					success: function(result){
						console.log("likeRemove Ajax 성공");
						likeList();
						
						//let res = "${boardVO.likescount - 1}";
						res = Number(res) - 1;
						if(res < 0)
							res = 0;
						let res2 = "<i class='material-icons' style='font-size:12pt;'>&#xe87e;</i>&nbsp;"; 
						let res3 = "<button class='btn btn-outline-dark' type='button' onclick='likeCreate();'>좋아요</button>&nbsp;&nbsp;";
						
						$('#likeSpan').empty();
						$('#likeSpan').append(res2 + res);
						
						$('#likeBtnSpan').empty();
						$('#likeBtnSpan').append(res3);
					},
					error:function(request, status, error){
						console.log("like취소 실패");
						alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error : " + error);
					}
				});	
			}
	       
			function pwCheck(pw) {

				var b_idx = "<c:out value='${boardVO.b_idx}' />";
				var postpw = pw.val;
				var resultParam = "<c:out value='${result}' />";
				
				$.ajax({
					type:"POST",
					url: "check.do",
					data : b_idx, postpw,
					datatype: 'html',
					success:function(result){
						if(result == "update"){		
							window.location.href="update.do";				
						}else{				
							$('#mmsg').empty();
							$('#mmsg').text("비밀번호를 다시 입력하세요.");
						}
					},
			    	error:function(request,status){
			    		alert("실패");
			    		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			    	}
				});	
			}
			
			function logout(){
				var result = confirm("로그아웃 하시겠습니까?");
				if(result){
					location.href = "logout.do";
				}else{
					location.href = "#";
				}
				
			}