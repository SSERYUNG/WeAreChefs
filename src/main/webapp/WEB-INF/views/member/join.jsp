<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<c:import url="/WEB-INF/views/templete/header.jsp"></c:import>
	<style>
		body {
			background-image: url(/resources/img/1.jpg);
			background-size: contain;
		}

		.form-container {
			display: flex;
			justify-content: center;
			align-items: center;
			flex-direction: column;
		}
		
		.form-wrapper {
			width: 100%;
			max-width: 600px;
			margin: 0 auto;
		}
		
		.form-item {
			margin-bottom: 15px;
		}
		
		.form-control {
				width: 100%;
		}

		#join_span {
			color: red;
		}

		#clear_span {
			color: #1e00ff
		}

		.email_span {
			margin-left: 10px;
		}

		.welcome {
			font-size: 50px;
			color: #81C408;
			font-style: italic;
		}

		.welcomediv {
			margin-left: 187px;
		}

		.headfrom {
			margin-top: -22%;
		}

		.labelName {
			color: black;
		}

		.col-12 input[type="text"] {
			color: rgb(41, 39, 39);
		}

		.btn {
			background-color: #fff;
		}

		.btn:disabled {
			background-color: #e9ecef;
			cursor: not-allowed;
			opacity: 0.65; 
		}
		
		.pwdCheck_span {
			margin-left: 7px;
			color: #1e00ff;
		}

		.unpwdCheck_span {
			margin-left: 7px;
			color: red;
		}

	</style>
</head>
<body>
	<c:import url="/WEB-INF/views/templete/nav.jsp"></c:import>
	<div class="container-fluid page-header py-5">
		<h1 class="text-center text-white display-6">회원가입</h1>
		<ol class="breadcrumb justify-content-center mb-0">
			<li class="breadcrumb-item"><a href="/">Home</a></li>
		</ol>
	</div>

	
<div class="container jointop">	
	<!-- <img src="/resources/img/vegetable-item-6.jpg"> -->

	<div class="container-fluid py-5" id="joinTopTag">
		<div class="container py-5">
			<div class="form-container">
				<div class="form-wrapper">
					<form action="join" method="post" id="joinForm">
						<div class="row g-5 headfrom">
							<div class="col-12">
								<div class="form-item welcomediv">
									<label class="form-label my-3 welcome">Welcome!!</label>	
								</div>

								<div class="form-item" id="name_span">
									<label class="form-label my-3 labelName">이름</label>
									<input type="text" class="form-control member_join labelName" id="member_name" name="member_name" size="5">
								</div>
								
								<div class="form-item" id="nick_name_span">
									<label class="form-label my-3 labelName">닉네임</label>
									<input type="text" class="form-control member_join labelName" id="member_nickname" name="member_nickname" maxlength="10">
									<button id="nickNameCheck" type="button" class="btn border-secondary py-2 px-4 text-uppercase w-80 text-primary mt-3">중복확인</button>
								</div>

								<div class="form-item">
									<label class="form-label my-3 labelName">아이디</label> 
									<input type="text" class="form-control member_join labelName" id="member_id" name="member_id" maxlength="12">
									<button id="idCheck" type="button" class="btn border-secondary py-2 px-4 text-uppercase w-80 text-primary mt-3">중복확인</button>
								</div>
								<div class="form-item">
									<label class="form-label my-3 labelName">비밀번호</label>
									<input type="password" class="form-control member_join labelName" id="member_pwd" name="member_pwd" maxlength="12">
								</div>
								<div class="form-item">
									<label class="form-label my-3 labelName">비밀번호확인</label>
									<input type="password" class="form-control member_join labelName" id="pwdCheck" maxlength="12">
									<span  id="pwdCheck_span" class="pwdCheck_span" style="display: none;">비밀번호가 일치합니다.</span>
									<span  id="unpwdCheck_span" class="unpwdCheck_span" style="display: none;">비밀번호가 불일치합니다.</span>
								</div>
								<div class="form-item">
									<label class="form-label my-3 labelName">이메일</label>
									<input type="text" class="form-control member_join labelName" id="membere_mail" name="membere_mail">
								</div>
								<button id="emailbtn" type="button" class="btn border-secondary py-2 px-4 text-uppercase w-70 text-primary">이메일 인증하기</button>

								<div class="form-item">
									<label class="form-label my-3 labelName">전화번호</label>
									<input type="text" class="form-control member_join labelName" id="member_phone" name="member_phone" maxlength="13">
									<button id="phoneCheck" type="button" class="btn border-secondary py-2 px-4 text-uppercase w-80 text-primary mt-3">중복확인</button>
								</div>

								<input type="hidden" class="form-control member_join" id="member_lev" name="member_lev" value="0">

								<div class="row g-4 text-center align-items-center justify-content-center pt-4">
									<button id="join_btn" type="button" class="btn border-secondary py-3 px-4 text-uppercase w-100 text-primary">회원가입</button>
								</div>
							</div>
						</div> 
					</form>
				</div>
			</div>
		</div>
	</div> 
</div>
<c:import url="/WEB-INF/views/templete/footer.jsp"></c:import>
<script src="/resources/member/js/join.js"></script>
<script src="/resources/member/js/email.js"></script>
</body>
</html>
<!-- placeholder="House Number Street Name" -->