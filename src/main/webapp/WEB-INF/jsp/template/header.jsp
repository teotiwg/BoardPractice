<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style>
/* 추가적인 CSS 영역 */
.label-userName {
	font-size: 0.8rem;
	color:#fd7e14;
}
</style>

<!-- Navigation-->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container px-4 px-lg-5">
                <a class="navbar-brand" href="/ojt/home.do">Market_Pro</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">                      
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Shop</a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="home.do">전체상품보기</a></li>
                                <li><hr class="dropdown-divider" /></li>
                                <li><a class="dropdown-item" href="#!">카테고리1</a></li>
                                <li><a class="dropdown-item" href="#!">카테고리2</a></li>
                            </ul>
                        </li>
                    </ul>
                    <div class="d-flex">                    	
                    	<input id="srch" class="form-control me-2 srch" type="search" placeholder="Search" aria-label="Search"> 
                    </div>
                    <c:choose>
                    	<c:when test="${sessionScope.sessionID == null}">
                    		<button class="btn btn-outline-dark" type="button" onclick="location.href='login.do'">로그인</button>
                    	</c:when>
                    	<c:otherwise>
                    		<button class="btn btn-outline-dark" type="button" onclick="javascript:location.href='/ojt/insert.do'">등록하기</button>&nbsp;&nbsp;
                    		<button class="btn btn-outline-dark" type="button" onclick="location.href='logout.do'">로그아웃</button>&nbsp;&nbsp;
                    		<label class="label-userName">${sessionScope.sessionName}</label>
                    	</c:otherwise>
                    </c:choose>
                    
                </div>
            </div>
        </nav>
  <script>
  /*java script 영역*/
  </script>
  <script type="text/javascript" src="resources/js/header.js"></script>