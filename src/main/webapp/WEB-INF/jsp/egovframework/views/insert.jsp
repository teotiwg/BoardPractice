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
		<script type="text/javascript" src="resources/js/product/insert.js"></script>
		<style>
			#star{ color:red; }
		</style>
		<script>
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
				if(confirm("게시글을 등록하시겠습니까?")){
					return true;
				}
				else
					history.back();
				//return true;
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
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Category</a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="home.do">전체 보기</a></li>
                                <li><hr class="dropdown-divider" /></li>
	                                <c:forEach items="${flags }" var="flag">
	                               		<li><a class="dropdown-item" href="/ojt/home.do?flag=${flag }" name="flag">${flag }</a></li>
	                                </c:forEach>
                            </ul>
                        </li>
                    </ul>
                    	<button class="btn btn-outline-dark" type="button" onclick="logout();">로그아웃</button>
                </div>
            </div>
        </nav>
        
        <form name="insertFrm" method="POST" enctype="multipart/form-data" action="/ojt/insertAction.do" onsubmit="return checkValidate(this);">
	        <section class="py-5">
	            <div class="container px-4 px-lg-5 my-5">
		                <div class="row gx-4 gx-lg-5 align-items-center">
		                    <div class="col-md-6" id="divImg"><img class="card-img-top mb-5 mb-md-0" src="https://dummyimage.com/500x350/fcfcfc/fcfcfc.png" id="pImg" name="img"/></div>
		                    <div class="col-md-6">
		                        <div class="small mb-1">
		                        	업체명<span id="star">*</span><br>
									<input type="text" style="width:80%;margin-bottom:10px;" name="userid" value="${sessionScope.sessionID}" ><br>
									서비스명<span id="star">*</span><br>
									<input type="text" style="width:80%;margin-bottom:10px;" name="title" /><br>
		                       		업체 및 서비스 요약 소개<span id="star">*</span><br>
		                        	<textarea style="width:80%;height:100px;margin-bottom:10px;" name="summary"></textarea><br>
		                        	<select name="p_flag" id="p_flag" style="border: solid lightgray 1px; border-radius:2px; padding:3px;width:80%;margin-bottom:10px;">
				                        <option value=""diabled select hidden>카테고리 분류</option>
				                           <c:forEach items="${flags }" var="flag" >
				                              <option value="${flag}">${flag}
				                                 <!-- varStatus="status"  <input type="hidden" value="${stocks[status.index] }"/>    -->
				                              </option>
				                           </c:forEach>
				                    </select>
		                        	<br>
		                        	게시글 비밀번호&nbsp;&nbsp;<input type="password" name="postpw" style="margin-bottom:10px;width:210px;" >
		                        </div>
		                        <div class="d-flex">
		                        	<button class="btn btn-outline-dark flex-shrink-0" type="button" onclick="onclick=document.all.file.click()"style="width:110px;">썸네일첨부</button>
		                            <input id="file" name="img" class="btn btn-outline-dark flex-shrink-0" type="file" accept="image/jpeg, image/jpg, image/png" style="display:none;" onchange="setPreview(event);"> &nbsp;
		                        </div>
	
		                    </div>
		                </div>
	            </div>
	        </section>
	        <section class="py-5 bg-light" >
	            <div class="container px-4 px-lg-5 mt-5" >
	            	서비스 소개<span id="star">*</span>
		        	<textarea style="width:100%;height:500px;" name="contents"></textarea>	
	            </div>
	            <div style="padding:1% 32% 0 48%;">
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
		
    </body>
</html>
