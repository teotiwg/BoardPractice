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
        <title>Market_Pro</title>
        <!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <!-- Bootstrap icons-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="css/styles.css" rel="stylesheet" />
		<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
		<script type="text/javascript" src="resources/js/product/home.js"></script>
		<script>
			function selectOrder(order){ 
		    	//obj.submit(); //obj자체가 form이다.
		    	//var page = parseInt(val);
		    	var order = order.value;
		    	var flag = "${flag}"
		    	//location.href="/ojt/home.do?order="+order;	
		    	location.href="/ojt/home.do?flag="+flag+"&order="+order;	
		    	//location.href="/ojt/home.do?flag=${flag}&order="+order+"&pageShow=${pageC}";	
			}
		</script>
    </head>
    <body>
    	<form action="/ojt/home.do?flag=${flag }&order=${order }" method="get" >
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
	                               		<li><a class="dropdown-item" href="/ojt/home.do?flag=${flag }&order=${order }" name="flag">${flag }</a></li>
	                                </c:forEach>
                            </ul>
                        </li>
                    </ul>
                    <c:choose>
                    	<c:when test="${sessionScope.sessionID == null}">
                    		<button class="btn btn-outline-dark" type="button" onclick="location.href='login.do'">로그인</button>
                    	</c:when>
                    	<c:otherwise>
                    		<button class="btn btn-outline-dark" type="button" onclick="location.href='/ojt/insert.do'">등록하기</button>&nbsp;&nbsp;
                    		<button class="btn btn-outline-dark" type="button" onclick="logout();">로그아웃</button>
                    	</c:otherwise>
                    </c:choose>
                    
                </div>
            </div>
        </nav>
        <!-- Header-->
        <header class="bg-dark py-5">
            <div class="container px-4 px-lg-5 my-5">
                <div class="text-center text-white">
                    <h1 class="display-4 fw-bolder">Market_Pro</h1>
                    <p class="lead fw-normal text-white-50 mb-0">How Could We Help You?</p>
                </div>
            </div>
        </header>
       		<div style="text-align:right;padding-right:120px;margin-top:30px;">
       		<!-- 
                <form name="orderFrm" action="" method="post" >
       		 -->
		                    <span id="orderDropdown">
			                        <select name="order" id="order" onchange="selectOrder(this)">
			                        	<option value="" diabled select hidden>정렬</option>
			                            <option value="all" >날짜 순</option>
			                            <option value="view" >조회수 순</option>
			                            <option value="like">좋아요 순</option>
			                        </select>
		                    </span>
		                    <!-- 
		        </form>      
		                     -->
       		</div>
        
 	</form>
        <!-- Section-->
        <section class="py-5">
            <div class="container px-4 px-lg-5 mt-5">
                <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
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
	                            	<!-- 
	                            	<i class="material-icons" style="font-size:12pt;">&#xe87e;</i>
	                            	<i class="material-icons">&#xe87d;</i>
	                            	 -->
	                            </div>
	                        </div>
	                    </div>
	                </c:forEach>	

                </div>
            </div>
        </section>
        
        
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