<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Market_Pro ::: Login</title>

    <!-- Custom fonts for this template-->  
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body class="bg-gradient-primary">

    <div class="container">

        <!-- Outer Row -->
        <div class="row justify-content-center">

            <div class="col-xl-10 col-lg-12 col-md-9">
				
                <div class="card o-hidden border-0 shadow-lg my-5">
                    <div class="card-body p-0">
                        <!-- Nested Row within Card Body -->
                        <div class="row">
                            <div class="col-lg-6 d-none d-lg-block login_backImg"></div>
                            <div class="col-lg-6">
                                <div class="p-5">
                                    <div class="text-center">
                                    	<a href='/ojt/'>
                                        	<label class="login_title">Market_Pro</label>
                                        </a>
                                    </div>
                                    
                                    <div class="user">
                                        <div class="form-group">
                                            <input type="text" class="form-control form-control-user"
                                                id="userid" name="userid"
                                                placeholder="아이디를 입력해주세요" required="required">
                                        </div>
                                        <div class="form-group">
                                            <input type="password" class="form-control form-control-user"
                                                id="userpw" name="userpw" placeholder="비밀번호" required="required" autocomplete="off">
                                        </div>
                                        <div class="form-group">
                                            <div class="custom-control custom-checkbox small" >
                                                <input type="checkbox" class="custom-control-input" id="customCheck">
                                                <label class="custom-control-label" for="customCheck">로그인 정보 기억</label>
                                            </div>
                                        </div>
                                        <button class="btn btn-primary btn-user btn-block" id="loginBtn" name="loginBtn" onclick="login()">
                                            	로그인
                                        </button>
                                    </div>
                                    
                                    <hr>                                   
                                    <div class="text-center">
                                    <!-- 
                                        <a class="small" href="/ojt/">회원가입</a>
                                     -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

        </div>

    </div>
    
<script type="text/javascript" src="resources/js/member/login.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

</body>

</html>