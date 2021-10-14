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
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <meta http-equiv="X-UA-Compatible" content="IE=Edge">
        <title>Market_Pro</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <!-- Bootstrap icons-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="css/styles.css" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <script type="text/javascript" src="resources/js/product/detail.js"></script>
        <script>
	    	let res = "${boardVO.likescount}";
	
	    	$(function(){
	    		likeList();
	    	});
	    	
	    	// Ajax 좋아요 기능 위해 좋아요 리스트 불러오기
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
			        },
			        error : function(request, status, error) {
			        	alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
			        }
			    });
			}
		
			// 좋아요
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
						likeList();
						
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
						alert("code : " + request.status + "\n" + "message : " + request.responseText + "\n" + "error : " + error);
					    }
					});
		 		}
			}
		
			// 좋아요 취소
			function likeRemove(){
				
				var likeVO = {};
				likeVO.userid = "<c:out value='${sessionScope.sessionID}' />";
				likeVO.b_idx = "<c:out value='${boardVO.b_idx}' />";
				
				$.ajax({
					url: '/ojt/removelike.do',
					type: 'POST',
					data: likeVO,
					success: function(result){
						likeList();
						
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
						alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error : " + error);
					}
				});	
			}
	       
			// 비밀번호 체크
			function pwCheck() {
				var b_idx = "<c:out value='${boardVO.b_idx}' />";
				var params = "b_idx="+b_idx+"&postpw="+$("#pw").val();
				
				$.ajax({
					type:"POST",
					url: "/ojt/checkpw.do",
					data : params,
					datatype: 'text',
					success:function(result){
						if(result == "correct"){		
							 $("#detailFrm").attr("action", "/ojt/update.do").submit();
						}else{				
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
			
			// 로그아웃 확인
			function logout(){
				var result = confirm("로그아웃 하시겠습니까?");
				if(result){
					location.href = "logout.do";
				}else{
					location.href = "#";
				}
			}
			
			// 비밀번호 입력 시 엔터 키 잠금
			document.addEventListener('keydown', function(event) {
				  if (event.keyCode === 13) {
				    event.preventDefault();
				  };
				}, true);
			
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
                     <c:choose>
                    	<c:when test="${sessionScope.sessionID == null}">
                    		<button class="btn btn-outline-dark" type="button" onclick="location.href='login.do'">로그인</button>
                    	</c:when>
                    	<c:otherwise>
                    		<button class="btn btn-outline-dark" type="button" onclick="javascript:location.href='/ojt/insert.do'">등록하기</button>&nbsp;&nbsp;
                    		<button class="btn btn-outline-dark" type="button" onclick="logout();">로그아웃</button>
                    	</c:otherwise>
                    </c:choose>
                </div>
            </div>
        </nav>
        
        <!-- Product section-->
        <section class="py-5">
            <div class="container px-4 px-lg-5 my-5">
            	<form name="detailFrm" id="detailFrm" method="POST" enctype="multipart/form-data" action="/ojt/update.do" >
	                <div class="row gx-4 gx-lg-5 align-items-center">
	                    <div class="col-md-6"><img class="card-img-top mb-5 mb-md-0" src="./images/${boardVO.img }" alt="..." /></div>
	                    <div class="col-md-6">
	                    	<div style="text-align:right;font-size:12pt;">
	                    		<span>
	                    			<i class="material-icons" style="font-size:12pt;">&#xe417;</i>
	                    				<fmt:formatNumber type="number" maxFractionDigits="3" value="${boardVO.viewcount }" />
	                            	<span id="likeSpan">
			                            <c:choose>
			                            	<c:when test="${sessionScope.sessionID!=null && sessionScope.sessionID==likeVO.userid}">
				                            	<i class="material-icons" style="color:red;font-size:12pt;">&#xe87d;</i>
			                            	</c:when>
			                            	<c:otherwise>
			                            		<i class="material-icons" style="font-size:12pt;">&#xe87e;</i>
			                            	</c:otherwise>
			                            </c:choose>
			                            	<fmt:formatNumber type="number" maxFractionDigits="3" value="${boardVO.likescount }" />
		                            </span>	 
	                    		</span>
	                    	</div>
	                    	
	                    	<input type="hidden" name="i_idx" value="${imageVO.i_idx }" />
	                    	<input type="hidden" name="b_idx" id="b_idx" value="${boardVO.b_idx }" />
	                    	<input type="hidden" name="postpw" value="${boardVO.postpw }" />
	                    	<input type="hidden" name="userid" value="${sessionScope.sessionID }" />
	                        <div class="small mb-1">${boardVO.p_flag}</div>
	                        <div class="small mb-1">${boardVO.userid }</div>
	                        <h1 class="display-5 fw-bolder">${boardVO.title }</h1>

	                        <p class="lead">
	                        	${fn:replace(boardVO.summary, newLineChar,"<br/>") }
	                        </p>
	                        
	                        <div class="d-flex">
	                            <button class="btn btn-outline-dark flex-shrink-0" type="button">
	                             	신청하기
	                            </button>&nbsp;&nbsp;
	                            
	                            <span id="likeBtnSpan">
		                            <c:choose>
		                            	<c:when test="${sessionScope.sessionID != null && sessionScope.sessionID==likeVO.userid }">
					                            <button class="btn btn-danger" type="button" onclick="likeRemove();" >
				                            		좋아요
				                        		</button>&nbsp;&nbsp;
		                        		</c:when>
		                        		<c:otherwise>
					                            <button class="btn btn-outline-dark" type="button" onclick="likeCreate();">
					                            		좋아요
				                        		</button>&nbsp;&nbsp;
		                        		</c:otherwise>
	                        		</c:choose>
	                            </span>
	                            
                        		<c:choose>
		                            <c:when test="${sessionScope.sessionID != null && boardVO.postpw == null}">
		                            	<input type="submit" class="btn btn-outline-dark flex-shrink-0" value="수정">
		                            </c:when>    
		                            <c:when test="${sessionScope.sessionID != null && boardVO.postpw != null }">
	                        			<button type="button" class="btn btn-outline-dark flex-shrink-0" data-toggle="modal" data-target="#pwModal" onclick="">
											수정
										</button>
		                            </c:when>
		                            <c:otherwise>
		                            </c:otherwise>
	                            </c:choose>
	                            
	                        </div>
	                        
	                    </div>
	                </div>
	        	</form>
            </div>
        </section>

        <section class="py-5 bg-light" style="margin:0 0 50px 0;">
            <div class="container px-4 px-lg-5 mt-5" style="padding-bottom:30px;">
            	<hr>
                <h4 class="fw-bolder mb-4">서비스 소개</h4>
	        		${fn:replace(boardVO.contents, newLineChar,"<br/>") }
	        		
	        	<hr>
            </div>
             <div style="padding:1% 33% 0 47%;">
			    	<button class="btn btn-outline-dark" type="button" onclick="javascript:history.back();">돌아가기</button>
	         </div>
        </section>
        
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
					        	<input type="hidden" name="b_idx" value="${boardVO.b_idx }" />
					        	
					        	<span id="mmsg" class="modal-title" style="font-size:10pt;">
					        		게시글의 비밀번호를 입력하세요.
					        	</span><br>
					        	
					        	<input type="password" id="pw" name="postpw" style="width:100%;margin:20px 0 15px 0;">
					        </div>
					        
					        <div class="modal-footer" style="padding:0 43% 0 25%;border:none;margin-bottom:10px;">
					          <input type="button" class="btn btn-outline-dark flex-shrink-0" onclick="pwCheck();" value="확인">
					        </div>
						</form>
				        
				      </div>
				    </div>
			  </div>
        
        
        <!-- Footer-->
        <footer class="py-5 bg-dark">
            <div class="container"><p class="m-0 text-center text-white">Copyright &copy; Market_Pro 2021</p></div>
        </footer>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
