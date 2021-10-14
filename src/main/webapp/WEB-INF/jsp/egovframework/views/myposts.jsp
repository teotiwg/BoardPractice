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
                            <div class="sb-sidenav-menu-heading">마이페이지</div>
                            <a class="nav-link" href="/ojt/mypage.do">
                                <div class="sb-nav-link-icon"><i class="material-icons" style="font-size:20px;">&#xe853;</i></div>
                                	회원정보 조회 / 변경
                            </a>
                            <a class="nav-link collapsed" href="#">
                                <div class="sb-nav-link-icon"><i class="material-icons" style="font-size:20px;">&#xe87d;</i></div>
                                	나의 좋아요
                            </a>
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapsePages" aria-expanded="false" aria-controls="collapsePages">
                                <div class="sb-nav-link-icon"><i class="fas fa-book-open" style="font-size:15px;"></i></div>
                                	나의 게시글
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
                                	최신 작성글 
                            </div>
                            <div class="card-body">
                                <c:forEach items="${boardVO}" var="result" varStatus="status">	
				                	<div class="col mb-5">
				                        <div class="card h-100">
				                            <!-- Product image-->
				                            <img class="card-img-top" src="./images/${result.img }" alt="..." />
				                            <!-- Product details-->
				                            <div class="card-body p-4">
				                                <div class="text-center">
				                                    <h5 class="fw-bolder">${result.title}</h5>
				                                    <div class="small mb-1" style="font-size:9pt;">${result.p_flag}</div>
				                                    	${fn:replace(result.summary, newLineChar,"<br/>") }
				                                </div>
				                            </div>
				                            <!-- Product actions-->
				                            <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
				                                <div class="text-center"><a class="btn btn-outline-dark mt-auto" href="/ojt/detail.do?b_idx=${result.b_idx }">자세히 보기</a></div>
				                            </div>
				                            <div style="text-align:right;font-size:12pt;">
				                            	<i class="material-icons" style="font-size:12pt;">&#xe417;</i>
				                            	<span class="view">
				                            		<fmt:formatNumber type="number" maxFractionDigits="3" value="${result.viewcount }" />
				                            	</span>
				                            	<c:choose>
					                            	<c:when test="${sessionScope.sessionID!=null && likes.contains(result.b_idx)==true }">
						                            	<i class="material-icons" style="color:red;font-size:12pt;">&#xe87d;</i>
					                            	</c:when>
					                            	<c:otherwise>
					                            		<i class="material-icons" style="font-size:12pt;">&#xe87e;</i>
					                            	</c:otherwise>
					                            </c:choose>
					                            <span class="likes">	
					                            	<fmt:formatNumber type="number" maxFractionDigits="3" value="${result.likescount }" />	                            
					                            </span>
				                            </div>
				                        </div>
				                    </div>
				                </c:forEach>	
				                
                            </div>
                        </div>
                        
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="material-icons" style="font-size:20px;">&#xe87d;</i>&nbsp;
                                	최신 좋아요
                            </div>
                            <div class="card-body">
                                <table id="datatablesSimple">
                                    <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Position</th>
                                            <th>Office</th>
                                            <th>Age</th>
                                            <th>Start date</th>
                                            <th>Salary</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td>Tiger Nixon</td>
                                            <td>System Architect</td>
                                            <td>Edinburgh</td>
                                            <td>61</td>
                                            <td>2011/04/25</td>
                                            <td>$320,800</td>
                                        </tr>
                                    </tbody>
                                </table>
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
