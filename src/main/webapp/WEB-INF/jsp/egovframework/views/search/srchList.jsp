<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Header-->
        <header class="bg-dark py-5">
            <div class="container px-4 px-lg-5 my-5">
                <div class="text-center text-white">
                    <h1 class="display-4 fw-bolder">Market_Pro</h1>                    
                    <p class="lead fw-normal text-white-50 mb-0">
                    	<span>"${keyword}"</span>&nbsp;&nbsp;
                    	<span>검색결과 : </span>&nbsp;&nbsp;
                    	<span>${total} 건</span>
                    </p>            
                </div>
            </div>
        </header>

        <!-- Section-->
        <section class="py-5">
            <div class="container px-4 px-lg-5 mt-5">
                <div class="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
                
	                <c:forEach var="result" items="${blist}" varStatus="status">	
	                	<div class="col mb-5">
	                        <div class="card h-100">
	                            <!-- Product image-->
	                            <img class="card-img-top" src="./images/${result.img }" alt="..." />
	                            <!-- Product details-->
	                            <div class="card-body p-4">
	                                <div class="text-center">
	                                    <!-- Product name-->
	                                    <!-- Product price-->
	                                    <h5 class="fw-bolder">${result.title}</h5>
	                                    ${result.summary}
	                                </div>
	                            </div>
	                            <!-- Product actions-->
	                            <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
	                                <div class="text-center"><a class="btn btn-outline-dark mt-auto" href="/ojt/detail.do?b_idx=${result.b_idx }">자세히 보기</a></div>
	                            </div>
	                        </div>
	                    </div>
	                </c:forEach>	
                </div>
            </div>
            
        </section>