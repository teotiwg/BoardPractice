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
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
        <link href="css/mypage.css" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js" crossorigin="anonymous"></script>
		<style>
			#star{ color:red; }
		</style>
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
                                	회원정보 조회 / 변경
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
                                <i class="fas fa-book-open" style="font-size:15px;"></i>&nbsp;&nbsp;
                                	작성 게시물
                            </div>
                           	<div class="row">
		                        	<c:forEach items="${myPosts}" var="post" varStatus="status">
						            	<div class="col-xl-2 col-md-6" style="margin:20px auto;">
							            	<div class="card bg text-black mb-3">
							                	<div>
								                	<a class="as" href="/ojt/detail.do?b_idx=${post.b_idx }"><img class="img" src="./images/${post.img }" style="width:100%;"></a>
							                    </div>
							                    <div class="card-footer d-flex align-items-center justify-content-between">
							                        <a class="small text-black stretched-link" href="/ojt/detail.do?b_idx=${post.b_idx }" style="text-decoration:none;margin:auto;">
							                        	${post.title }
							                       	</a>
							                    </div>
							                    <div class="card-footer d-flex align-items-center justify-content-between">
							                       	[작성] ${post.date_c }
							                    </div>
							                    <div style="margin:0 auto;font-size:10pt;">
					                            	<span class="view">
						                            	<i class="material-icons" style="font-size:12pt;">&#xe417;</i>
					                            		<fmt:formatNumber type="number" maxFractionDigits="3" value="${post.viewcount }" />
					                            	</span>
					                            	<%-- c:choose>
						                            	<c:when test="${sessionScope.sessionID!=null && likes.contain.b_idx)==true }">
							                            	<i class="material-icons" style="color:red;font-size:12pt;">&#xe87d;</i>
						                            	</c:when>
						                            	<c:otherwise>
						                            		<i class="material-icons" style="font-size:12pt;">&#xe87e;</i>
						                            	</c:otherwise>
						                            </c:choose--%>
						                            <span class="likes">	
						                            	<i class="material-icons" style="font-size:12pt;">&#xe87e;</i>
						                            	<fmt:formatNumber type="number" maxFractionDigits="3" value="${post.likescount }" />	                            
						                            </span>
					                            </div>
							                </div>
							            </div>		
						         	</c:forEach>
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
