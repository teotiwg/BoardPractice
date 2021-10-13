<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Market_Pro</title>
        <!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <!-- Bootstrap icons-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="css/styles.css" rel="stylesheet" />
        <script>
        function checkValidate(f){
    		if(f.username.value==""){
    			alert("사용자 혹은 업체명를 입력하세요.");
    			f.username.focus();
    			return false;
    		}
    		if(f.title.value==""){
    			alert("서비스명을 입력하세요.");
    			f.title.focus();
    			return false;
    		}
    		if(f.summary.value==""){
    			alert("업체 및 서비스의 요약 소개를 입력하세요.");
    			f.summary.focus();
    			return false;
    		}
    		if(f.contents.value==""){
    			alert("업체 및 서비스 소개를 입력하세요.");
    			f.contents.focus();
    			return false;
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
    	
        </script>
        
    </head>
    <body>
        <!-- Navigation-->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container px-4 px-lg-5">
                <a class="navbar-brand" href="/ojt/home.do">Market_Pro</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                        <li class="nav-item"><a class="nav-link active" aria-current="page" href="/ojt/home.do">Home</a></li>
                        <li class="nav-item"><a class="nav-link" href="#!">About</a></li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Shop</a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="#!">All Products</a></li>
                                <li><hr class="dropdown-divider" /></li>
                                <li><a class="dropdown-item" href="#!">Popular Items</a></li>
                                <li><a class="dropdown-item" href="#!">New Arrivals</a></li>
                            </ul>
                        </li>
                    </ul>
                    <form class="d-flex">
                    	<button class="btn btn-outline-dark" type="submit">로그아웃</button>
                    </form>
                </div>
            </div>
        </nav>
        
        <form name="insertFrm" method="POST" enctype="multipart/form-data" action="/ojt/insertAction.do" >
	        <section class="py-5">
	            <div class="container px-4 px-lg-5 my-5">
		                <div class="row gx-4 gx-lg-5 align-items-center">
		                    <div class="col-md-6" id="divImg"><img class="card-img-top mb-5 mb-md-0" src="https://dummyimage.com/500x350/fcfcfc/fcfcfc.png" id="pImg" name="img"/></div>
		                    <div class="col-md-6">
		                        <div class="small mb-1">
		                        	업체명<br>
									<input type="text" style="width:80%" name="userid" value="seller1" ><br>
									서비스명<br>
									<input type="text" style="width:80%" name="title" /><br>
		                       		업체 및 서비스 소개 요약<br>
		                        	<textarea style="width:80%;height:100px;" name="summary"></textarea><br>
		                        	게시글 비밀번호&nbsp;&nbsp;<input type="password" name="postpw" >
		                        </div>
		                        <div class="d-flex">
		                        	<button class="btn btn-outline-dark flex-shrink-0" type="button" onclick="onclick=document.all.file.click()">썸네일 첨부</button>
		                            <input id="file" name="img" class="btn btn-outline-dark flex-shrink-0" type="file" accept="image/jpeg, image/jpg, image/png" style="display:none;" onchange="setPreview(event);"> &nbsp;
		                        </div>
	
		                    </div>
		                </div>
	            </div>
	        </section>
	        <section class="py-5 bg-light" >
	            <div class="container px-4 px-lg-5 mt-5" >
		        	<textarea style="width:100%;height:500px;" name="contents"></textarea>	
	            </div>
	            <div style="padding:1% 40% 0 45%;">
	            <!--  
	            	<button class="btn btn-outline-dark flex-shrink-0" type="button" >사진 첨부</button>
	            onclick="checkValidate(form)"
	            -->
		            <button class="btn btn-outline-dark" type="submit">등록</button>
	            </div>
	        </section>
	    </form>
        
        
        <!-- Footer-->
        <footer class="py-5 bg-dark">
            <div class="container"><p class="m-0 text-center text-white">Copyright &copy; Market_Pro 2021</p></div>
        </footer>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS
        <script src="js/scripts.js"></script>
        -->
    </body>
</html>
