<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	pageContext.setAttribute("newLineChar", "\n");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=Edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Market_Pro</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
        <link href="css/mypage.css" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <script>
        // 비밀번호 확인
        function passValidate(fn){
        	var p1 = fn.userpw;
        	var p2 = fn.userpw2;
        	
        	if(fn.pw.value==""){
				alert("기존 비밀번호를 입력하세요.");
				fn.pw.focus();
				return false;
			}
        	if(p1.value==""){
				alert("새 비밀번호를 입력하세요.");
				p1.focus();
				return false;
			}
        	if(p2.value==""){
        		alert("확인 비밀번호를 입력하세요.");
        		p2.focus();
        		return false;
        	}
        	
        }
        
     	// 회원정보 입력 값 확인
		function infoValidate(fn){
		
			if(fn.name.value==""){
				alert("이름을 입력하세요.");
				fn.name.focus();
				return false;
			}
			if(fn.phone.value==""){
				alert("연락처를 입력하세요.");
				fn.summary.focus();
				return false;
			}
			if(fn.email.value==""){
				alert("이메일 주소를 입력하세요.");
				fn.email.focus();
				return false;
			}
				
		}
     	/*
     	1. 구 비번 받아서 확인
     	2. 틀렸으면 알러트
     	3. 맞았으면 새 비밀번호 입력창 뜸
     	*/
		function oldCheck() {
			var userid = "<c:out value='${myInfo.userid}' />";
			var params = "userid="+userid+"&userpw="+$("#oldpw").val();
			
			$.ajax({
				type:"POST",
				url: "/ojt/check.do",
				data : params,
				datatype: 'text',
				success:
					function(result){
					if(result == "correct"){
						alert("성공");
						
					}else{
						alert("비밀번호가 틀렸습니다.\n다시 입력하세요,");
					}
				},	
		    	error:function(request,status){
		    		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		    	}
			});	
		}
     	
     	
		     // 비밀번호 체크
				function pwCheck() {
					var userid = "<c:out value='${myInfo.userid}' />";
					var params = "userid="+userid+"&userpw="+$("#pw").val();
					
					$.ajax({
						type:"POST",
						url: "/ojt/check.do",
						data : params,
						datatype: 'text',
						success:
							function(result){
							if(result == "correct"){
								console.log("성공");
								console.log(result.value);
								 $("#infoFrm").attr("action", "/ojt/updateinfo.do").submit();
								 alert("회원정보가 수정되었습니다.");
							}else{
								console.log("실패");
								console.log(result.value);
					    		$('#pw').empty();
								$('#mmsg').empty();
								$('#mmsg').text("비밀번호를 다시 입력하세요.");
							}
						},	
				    	error:function(request,status){
				    		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
				    	}
					});	
				}
		     
				// 비밀번호 입력 시 엔터 키 잠금
				document.addEventListener('keydown', function(event) {
					  if (event.keyCode === 13) {
					    event.preventDefault();
					  };
					}, true);
				
				// 로그아웃 확인
				function logout(){
					var result = confirm("로그아웃 하시겠습니까?");
					if(result){
						location.href = "logout.do";
					}else{
						location.href = "#";
					}
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
                    <button class="btn btn-outline-dark" type="button" onclick="location.href='/ojt/insert.do'">등록하기</button>&nbsp;&nbsp;
                    <button class="btn btn-outline-dark" type="button" onclick="logout();">로그아웃</button>
                    <a href="/ojt/mypage.do"><i class="material-icons" style="color:gray;font-size:35px;margin:10px 0 0 10px;">&#xe853;</i></a>
                </div>
            </div>
        </nav>
        
        
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <div class="sb-sidenav-menu-heading">
								<a class="nav-link" href="/ojt/mypage.do">
	                            	마이페이지
								</a>    
							</div>
                            <a class="nav-link" href="/ojt/myinfo.do">
                                <div class="sb-nav-link-icon"><i class="material-icons" style="font-size:20px;">&#xe853;</i></div>
                                	회원정보 조회/ 변경
                            </a>
                            <a class="nav-link" href="/ojt/myposts.do">
                                <div class="sb-nav-link-icon"><i class="fas fa-book-open" style="font-size:15px;"></i></div>
                                	작성 게시물
                            </a>
                            <a class="nav-link" href="/ojt/mylikes.do">
                                <div class="sb-nav-link-icon"><i class="material-icons" style="font-size:20px;">&#xe87d;</i></div>
                                	나의 좋아요
                            </a>

                        </div>
                    </div>
                </nav>
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h2 class="mt-4">${sessionScope.sessionID }님의 마이페이지</h2>
						<br>                        
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="material-icons" style="font-size:20px;">&#xe87d;</i>&nbsp;
                                	회원정보 조회 / 변경
                            </div>
                            <div>
                            	<form name="passFrm" id="passFrm" action="/ojt/updatepw.do" method="POST">
                            		<table class="table table-bordered">
                            			<colgroup>
                            				<col width="20%" />
                            				<col width="*" />
                            			</colgroup>
                            			<tbody>
                            				<tr>비밀번호 변경</tr>
                            				<tr>
												<td>기존 비밀번호 입력</td>
												<td>
													<input type="password" name="oldpw" id="oldpw" >
												</td>
                            				</tr>
                            				<tr id="new1" style="display:none;">
												<td>새 비밀번호 입력</td>
												<td>
													<input type="hidden" name="userid" id="userid" value="${myInfo.userid }">
													<input type="password" name="userpw" id="userpw">
												</td>
                            				</tr>
                            				<tr id="new2" style="display:none;">
												<td>새 비밀번호 비밀번호 확인</td>
												<td>
													<input type="password" name="userpw2" id="userpw2">
												</td>
                            				</tr>
                            				<tr>
                            					<input class="btn btn-outline-dark" type="button" onclick="oldCheck();" value="변경">
                            					<button class="btn btn-outline-dark" type="submit" id="submitBtn" style="display:none;">전송</button>
                            				</tr>
                            			</tbody>
                            		</table>
                            	</form>
                            </div>
                            <div>
                            	<form name="infoFrm" id="infoFrm" action="/ojt/updateinfo.do" method="POST">
                            		<table class="table table-bordered">
                            			<colgroup>
                            				<col width="20%" />
                            				<col width="*" />
                            			</colgroup>
                            			<tbody>
                            				<tr>회원정보 수정</tr>
                            				<tr>
												<td>아이디</td>
												<td>
													<input type="text" name="userid" value="${myInfo.userid }" readonly>
												</td>
                            				</tr>
                            				<tr>
												<td>이름</td>
												<td>
													<input type="text" name="name" value="${myInfo.name }">
												</td>
                            				</tr>
                            				<tr>
												<td>연락처</td>
												<td>
													<input type="text" name="phone" value="${myInfo.phone }">
												</td>
                            				</tr>
                            				<tr>
												<td>이메일 주소</td>
												<td>
													<input type="text" name="email" value="${myInfo.email }">
												</td>
                            				</tr>
                            				<tr>
                            					<button type="button" class="btn btn-outline-dark flex-shrink-0" data-toggle="modal" data-target="#pwModal" onclick="">
													수정
												</button>
												
                            				</tr>
                            			</tbody>
                            		</table>
                            	</form>
                            </div>	
                            	<!-- The Modal -->
							  <div class="modal" id="pwModal">
								    <div class="modal-dialog">
								      <div class="modal-content">
								        <!-- Modal Header -->
								        <div class="modal-header" style="border:none;">
								          <button type="button" class="close" data-dismiss="modal">&times;</button>
								        </div>
								        
							  			<form name="frm" method="POST" action="/ojt/check.do"> 
									        <!-- Modal body -->
									        <div class="modal-body" style="padding:0 30% 0 30%;">
									        	<input type="hidden" name="userid" value="${myInfo.userid }" />
									        	
									        	<span id="mmsg" class="modal-title" style="font-size:10pt;">
									        		회원정보를 수정하시겠습니까?<br/>
									        		비밀번호를 입력해주세요.
									        	</span><br>
									        	
									        	<input type="password" id="pw" name="updatepw" style="width:100%;margin:20px 0 15px 0;">
									        </div>
									        
									        <div class="modal-footer" style="padding:0 43% 0 25%;border:none;margin-bottom:10px;">
									        	<input type="button" class="btn btn-outline-dark flex-shrink-0" onclick="pwCheck();" value="확인">
									        </div>
										</form>
								        
								      	</div>
									</div>
							  </div>
                            	
                            </div>
                        </div>
                        
                    </div>
                </main>
                
            </div>
        </div>



        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="assets/demo/chart-area-demo.js"></script>
        <script src="assets/demo/chart-bar-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
        <script src="js/datatables-simple-demo.js"></script>
    </body>
</html>
