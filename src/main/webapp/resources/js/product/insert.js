	function logout(){
			var result = confirm("로그아웃 하시겠습니까?");
			if(result){
				location.href = "logout.do";
			}else{
				location.href = "#";
			}
		}
		
		// 썸네일 
    	function setPreview(event){
    		var reader = new FileReader();
    		
    		reader.onload = function(event){
    			var before = document.getElementById("pImg");
    			before.remove();
    			
    			var img = document.createElement("img");
    			
    			img.style.width="100%";
    			img.style.height="100%";
    			
    			img.setAttribute("src", event.target.result);
    			document.querySelector("#divImg").appendChild(img);
    		};
    		reader.readAsDataURL(event.target.files[0]);
    	}
		
		 function checkValidate(fn){
				var l1 = fn.title;
				var l2 = fn.summary;
			
				if(fn.userid.value=""){
					alert("업체명을 입력하세요.");
					fn.userid.focus();
					return false;
				}
				if(fn.title.value==""){
					alert("서비스명을 입력하세요.");
					fn.title.focus();
					return false;
				}
				if(l1.value.length>=50){
					alert("서비스명은 50자 내로 입력하세요.");
					l1.focus();
					return false;
				}
				if(fn.summary.value==""){
					alert("업체 및 서비스의 요약설명을 입력하세요.");
					fn.summary.focus();
					return false;
				}
				if(l2.value.length>=100){
					alert("업체 및 서비스의 요약설명은 100자 내로 입력하세요.");
					l2.focus();
					return false;
				}
				if(fn.p_flag.value==""){
					alert("카테고리를 설정하세요.");
					fn.p_flag.focus();
					return false;
				}
				if(fn.img.value==""){
					alert("썸네일 이미지를 첨부하세요.");
					fn.img.focus();
					return false;
				}
				if(fn.contents.value==""){
					alert("서비스 소개를 입력하세요.");
					fn.contents.focus();
					return false;
				}
				return true;
			}