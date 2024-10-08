<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<!-- <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous"> -->
<c:import url="/WEB-INF/views/templete/header.jsp"></c:import>
<title>Insert title here</title>
<style>
    .pp {
        width: 200px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        height: 20px;
    }

    h4 {
        width: 200px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        height: 50px;
    }

    .about_me {

        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;

    }

    .modal-body input[type="file"] {
        color: black;
        /* 파일 입력 필드의 텍스트 색상 */
        background-color: white;
        /* 배경 색상 */
        border: 1px solid #ccc;
        /* 테두리 색상 */
        padding: 5px;
        /* 패딩 */
    }

    .about_me {
        text-align: center;
    }

    .listcategory {
        width: 78%;
    }

    .profile{
        border: 3px solid #b2e71e;
    }

</style>
</head>

<body>
<c:import url="/WEB-INF/views/templete/nav.jsp"></c:import>
<div class="container-fluid page-header py-5">
    <h1 class="text-center text-white display-6">마이페이지</h1>
    <ol class="breadcrumb justify-content-center mb-0">
        <li class="breadcrumb-item"><a href="/">Home</a></li>
        <li class="breadcrumb-item"><a href="buyList">주문목록</a></li>
    </ol>
</div>


    <!-- Fruits Shop Start-->
    <div class="container-fluid fruite py-5">
        <div class="container py-5 pb-3">
            <div class="row g-4">
                <div class="col-lg-12">
                    <div class="row g-4">
                        <div class="col-lg-3">
                            <div class="row g-4">
                                <div class="col-lg-12">
                                    <div class="mb-3">
                                        <div class="rounded position-relative fruite-item mt-5">
                                            <div class="box-1">
                                                <c:if test="${member.member_type eq '일반회원'}">
                                                    <c:choose>
                                                        <c:when test="${not empty member.profile_name}">
                                                            <img src="/resources/upload/member/${member.profile_name}"  class="profile" alt="">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="/resources/member/img/base.jpg" class="profile">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                                <c:if test="${member.member_type eq '카카오톡'}">
                                                    <c:choose>
                                                        <c:when test="${not empty member.kakao_profile_img}">
                                                            <img src="${member.kakao_profile_img}" class="profile" alt="">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="/resources/member/img/base.jpg" class="profile">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                            </div>
                                            <div class="text-white px-3 py-1 rounded position-absolute change" style="top: -10px; left: 170px;">

                                            <!-- 프로필 사진 modal -->
                                            <a href="#" data-bs-toggle="modal" data-bs-target="#pictureModal">
                                                <img class="profileChange" src="/resources/member/img/5.png" style="width: 30px; height: 30px;">
                                            </a>

                                            <div class="modal fade" id="pictureModal" tabindex="-1" aria-labelledby="pictureModalLabel" aria-hidden="true">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h1 class="modal-title fs-5" id="pictureModalLabel">프로필 사진 변경</h1>
                                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>

                                                        <div class="modal-body">
                                                            <form action="/member/profileChange" method="POST" enctype="multipart/form-data">

                                                                <div class="mb-3">
                                                                    <div class="form-group" id="test">
                                                                        <label class="form-label mt-3 mr-3" style="color: black;">프로필 사진 변경: </label>
                                                                        <input type="file" name="multipartFile">
                                                                    </div>
                                                                </div>

                                                                <div class="modal-footer">
                                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                                    <button type="submit" class="btn btn-primary" id="profile_picture_add" data-member-id="${member.member_id}">등록</button>
                                                                    <button type="button" class="btn btn-danger" id="profile_picture_delete" data-member-delete-id="${member.member_id}">삭제</button>
                                                                </div>
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            </div>
                                            <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                                            <!--자기소개 Modal -->
                                                <div>
                                                    <a href="#" class="" data-bs-toggle="modal" data-bs-target="#exampleModal">
                                                        <c:choose>
                                                            <c:when test="${not empty member.profile_about_me}">
                                                                <div id="change" class="about_me">
                                                                    ${member.profile_about_me}
                                                                    <br>
                                                                    <c:choose>
                                                                        <c:when test="${not empty member.profile_sns_url}">
                                                                            
                                                                            <a href="${member.profile_sns_url}" target="_blank"  class="btn border border-secondary rounded-pill px-3 text-primary mt-2">
                                                                                <label for="fruits">SNS</label>
                                                                            </a>
                                                                            <a id="modalpage" class="btn border border-secondary rounded-pill px-3 text-primary mt-2" data-bs-toggle="modal" data-bs-target="#SnsModal">
                                                                                <label for="fruits">SNS 변경</label>
                                                                            </a>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <a id="modalpage" class="btn border border-secondary rounded-pill px-3 text-primary mt-2" data-bs-toggle="modal" data-bs-target="#SnsModal">

                                                                                <label for="fruits">SNS 링크➕</label>
                                                                            </a>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                    <!-- SNS Modal -->
                                                                    <div class="modal fade" id="SnsModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                                        <div class="modal-dialog">
                                                                            <div class="modal-content">
                                                                                <div class="modal-header">
                                                                                    <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
                                                                                    <button type="button" class="btn-close" data-bs-dismiss="modal"aria-label="Close"></button>
                                                                                </div>
                                                                                <div class="modal-body">
                                                                                    <c:choose>
                                                                                        <c:when test="${not empty member.profile_sns_url}">
                                                                                            <input id="snsUrl" type="text" size="50" placeholder="${member.profile_sns_url}" class="ms-3">
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <input id="snsUrl" type="text" size="50" placeholder="https://www.youtube.com 형식으로 작성해주세요." class="ms-3">
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                </div>
                                                                                <div class="modal-footer">
                                                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                                                    <button type="button" class="btn btn-primary" id="profile_sns_add" data-sns-member_id="${member.member_id}">등록</button>
                                                                                    <button type="button" class="btn btn-danger" id="profile_sns_delete" data-sns-member_id="${member.member_id}">삭제</button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <div id="introduces_text" class="about_me">
                                                                    자기소개서를 등록할 수 있습니다
                                                                    <br>
                                                                    <c:choose>
                                                                        <c:when test="${not empty member.profile_sns_url}">
                                                                            <a href="${member.profile_sns_url}" target="_blank"  class="btn border border-secondary rounded-pill px-3 text-primary mt-2">
                                                                                <label for="fruits">SNS</label>
                                                                            </a>
                                                                            <a id="modalpage" class="btn border border-secondary rounded-pill px-3 text-primary mt-2" data-bs-toggle="modal" data-bs-target="#SnsModal">
                                                                                <label for="fruits">SNS 변경</label>
                                                                            </a>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <a id="modalpage" class="btn border border-secondary rounded-pill px-3 text-primary mt-2" data-bs-toggle="modal" data-bs-target="#SnsModal">
                                                                                <label for="fruits">SNS 링크➕</label>
                                                                            </a>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                    <!-- SNS Modal -->
                                                                    <div class="modal fade" id="SnsModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                                        <div class="modal-dialog">
                                                                            <div class="modal-content">
                                                                                <div class="modal-header">
                                                                                    <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
                                                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                                                </div>
                                                                                <div class="modal-body">
                                                                                    <c:choose>
                                                                                        <c:when test="${not empty member.profile_sns_url}">
                                                                                            <input id="snsUrl" type="text" size="50" placeholder="${member.profile_sns_url}" class="ms-3">
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <input id="snsUrl" type="text" size="50" placeholder="https://www.youtube.com 형식으로 작성해주세요." class="ms-3">
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                </div>
                                                                                <div class="modal-footer">
                                                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                                                    <button type="button" class="btn btn-primary" id="profile_sns_add" data-sns-member_id="${member.member_id}">등록</button>
                                                                                    <button type="button" class="btn btn-danger" id="profile_sns_delete" data-sns-member_id="${member.member_id}">삭제</button>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </a>

                                                    <div class="modal fade" id="exampleModal" tabindex="-1"
                                                        aria-labelledby="exampleModalLabel" aria-hidden="true">
                                                        <div class="modal-dialog">
                                                            <div class="modal-content">
                                                                <div class="modal-header">
                                                                    <h1 class="modal-title fs-5"
                                                                        id="exampleModalLabel">자기소개 등록 및 변경</h1>
                                                                    <button type="button" class="btn-close"
                                                                        data-bs-dismiss="modal"
                                                                        aria-label="Close"></button>
                                                                </div>

                                                                <div class="modal-body">
                                                                    <div class="mb-3">
                                                                        <textarea class="form-control"
                                                                            id="commentContents"
                                                                            rows="3"></textarea>
                                                                    </div>
                                                                </div>

                                                                <div class="modal-footer">
                                                                    <button type="button"
                                                                        class="btn btn-secondary"
                                                                        data-bs-dismiss="modal">Close</button>
                                                                    <button type="button"
                                                                        class="btn btn-primary"
                                                                        id="introduction_add"
                                                                        data_member_id="${member.member_id}">등록</button>
                                                                    <button type="button" class="btn btn-danger"
                                                                        id="introduction_delete"
                                                                        data-member-id="${member.member_id}">삭제</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>


                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                <a class="btn border border-secondary rounded-pill px-3 text-primary ms-5 mt-2 mb-2">레시피 조회수 &#128195;${hit}</a>
                                                <a href="buyList" class="btn border border-secondary rounded-pill px-3 text-primary ms-5 mt-2 mb-2">
                                                    <i class="fa fa-shopping-bag me-2 text-primary iii"></i>주문목록 보러가기</a>
                                            
                                            </div>
                                        </div>


                                    </div>
                                </div>


                                <div class="col-lg-12">
                                    <div id="carouselExampleCaptions" class="carousel slide"
                                        data-bs-ride="carousel">
                                        <div class="carousel-indicators">
                                            <button type="button" data-bs-target="#carouselExampleCaptions"
                                                data-bs-slide-to="0" class="active" aria-current="true"
                                                aria-label="Slide 1"></button>
                                            <button type="button" data-bs-target="#carouselExampleCaptions"
                                                data-bs-slide-to="1" aria-label="Slide 2"></button>
                                            <button type="button" data-bs-target="#carouselExampleCaptions"
                                                data-bs-slide-to="2" aria-label="Slide 3"></button>
                                        </div>



                                        <div class="carousel-inner">
                                            <div class="carousel-item active">
                                                <img src="/resources/member/img/test1.png" class="d-block w-100"
                                                    alt="...">
                                                <div class="carousel-caption d-none d-md-block">

                                                    <p class="pp">${recentyList[0].recipe_name}</p>
                                                </div>
                                            </div>
                                            <div class="carousel-item">
                                                <img src="/resources/member/img/test2.png"
                                                    class="d-block w-100" alt="...">
                                                <div class="carousel-caption d-none d-md-block">

                                                    <p class="pp">${recentyList[1].recipe_name}</p>
                                                </div>
                                            </div>
                                            <div class="carousel-item">
                                                <img src="/resources/member/img/test3.png"
                                                    class="d-block w-100" alt="...">
                                                <div class="carousel-caption d-none d-md-block">

                                                    <p class="pp">${recentyList[2].recipe_name}</p>
                                                </div>
                                            </div>
                                        </div>



                                        <button class="carousel-control-prev" type="button"
                                            data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
                                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                            <span class="visually-hidden">Previous</span>
                                        </button>
                                        <button class="carousel-control-next" type="button"
                                            data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
                                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                            <span class="visually-hidden">Next</span>
                                        </button>
                                    </div>
                                </div>
                                
                                <c:if test="${member.member_type eq '일반회원'}">
                                    <form action="/member/resign" method="post" id="resignForm">
                                        <input type="hidden" value="${member.member_id}" name="member_id">
                                        <button class="btn btn-danger" type="button" id="resignBtn">회원탈퇴</button>
                                    </form>
                                </c:if>


                                <div style="display: none;">
                                    <button class="btn btn-danger" type="button" id="resignBtn">회원탈퇴</button>
                                </div>

                            </div>
                        </div>


                        <div class="col-lg-9">
                            <div class="row g-4 justify-content-center">
                                <div class="tab-class text-center">
                                    <div class="col-lg-8 text-end listcategory">
                                        <ul class="nav nav-pills d-inline-flex text-center mb-5 mt-5">
                                            <li class="nav-item">
                                                <a class="d-flex m-2 py-2 bg-light rounded-pill ${tab eq '1' ? 'active' : ''}"
                                                    href="/member/mypage?tab=1">
                                                    <span class="text-dark" style="width: 130px;">레시피 리스트</span>
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="d-flex py-2 m-2 bg-light rounded-pill ${tab eq '2' ? 'active' : ''}"
                                                    href="/member/mypage?tab=2">
                                                    <span class="text-dark" style="width: 130px;">요리 후기
                                                        리스트</span>
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="d-flex m-2 py-2 bg-light rounded-pill ${tab eq '3' ? 'active' : ''}"
                                                    href="/member/mypage?tab=3">
                                                    <span class="text-dark" style="width: 130px;">댓글 리스트</span>
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="d-flex m-2 py-2 bg-light rounded-pill ${tab eq '4' ? 'active' : ''}"
                                                    href="/member/mypage?tab=4">
                                                    <span class="text-dark" style="width: 130px;">찜 목록</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>                     
                                    <div class="tab-content">
                                        <c:choose>
                                            <c:when test="${tab=='1'}">
                                                <div id="tab-1" class="tab-pane fade show active p-0">
                                                    <div class="row g-4">
                                                        <div class="col-lg-12">
                                                            <div class="row g-4"> 
                                            
                                                                <c:forEach items="${recipeMap.recipeAr}" var="list">
                                                                    <div class="col-md-6 col-lg-4 col-xl-3">
                                                                        <div class="rounded position-relative fruite-item">
                                                                            <div class="fruite-img">
                                                                                <img src="img/fruite-item-5.jpg" class="img-fluid w-100 rounded-top" alt="">
                                                                            </div>
                                                                            <div class="text-white bg-secondary px-3 py-1 rounded position-absolute" style="top: 10px; left: 10px;">
                                                                                ${list.recipe_category}
                                                                            </div>
                                                                            <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                                                <h4>${list.recipe_name}</h4>
                                                                                <!-- <p class="pp">${list.menu_recipe}</p> -->
                                                                                <div class="d-flex justify-content-between flex-lg-wrap">
                                                                                    <a href="/recipe/detail?recipe_num=${list.recipe_num}" style="margin-left:18%"
                                                                                        class="btn border border-secondary rounded-pill px-3 text-primary">🧑‍🍳레시피보러가기</a>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </c:forEach>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-12">
                                                        <div class="pagination d-flex justify-content-center mt-5">
                                                            <a href="/member/mypage?page=${recipeMap.recipePager.startNum-1}&order=${recipeMap.recipePager.order}&tab=1" class="btn rounded ${recipeMap.recipePager.pre ? '' : 'disabled'}">&laquo;</a>
                                                            <c:forEach begin="${recipeMap.recipePager.startNum}" end="${recipeMap.recipePager.lastNum}" step="1" var="i">
                                                                <a href="/member/mypage?page=${i}&order=${recipeMap.recipePager.order}&tab=1" class="rounded">${i}</a>
                                                            </c:forEach>
                                                            <a href="/member/mypage?page=${recipeMap.recipePager.lastNum+1}&order=${recipeMap.recipePager.order}&tab=1" class="btn rounded ${recipeMap.recipePager.next ? '' : 'disabled'}">&raquo;</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:when>


                                            <c:when test="${tab=='2'}">
                                                <div id="tab-2" class="tab-pane fade show active p-0">
                                                    <div class="row g-4">
                                                        <div class="col-lg-12">
                                                            <div class="row g-4">
                                                                <c:forEach items="${recipeReviewMap.recipereViewAr}" var="review">
                                                                    <div class="col-md-6 col-lg-4 col-xl-3">
                                                                        <div class="rounded position-relative fruite-item">
                                                                            <div class="fruite-img">
                                                                                <img src="img/fruite-item-5.jpg" class="img-fluid w-100 rounded-top" alt="">
                                                                            </div>
                                                                            <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                                                <!-- <h4>${review.board_title}</h4> -->
                                                                                <p class="pp">${review.board_content}</p>
                                                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                                                <a href="/recipe/detail?recipe_num=${review.recipe_num}" style="margin-left:18%" class="btn border border-secondary rounded-pill px-3 text-primary">작성한 요리 후기 보러가기</a>
                                                                                </div> 
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </c:forEach>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-12">
                                                        <div class="pagination d-flex justify-content-center mt-5">
                                                            <a href="/member/mypage?page=${recipeReviewMap.recipeReviewpager.startNum-1}&order=${recipeReviewMap.recipeReviewpager.order}&tab=2" class="btn rounded ${recipeReviewMap.recipeReviewpager.pre ? '' : 'disabled'}">&laquo;</a>
                                                            <c:forEach begin="${recipeReviewMap.recipeReviewpager.startNum}" end="${recipeReviewMap.recipeReviewpager.lastNum}" step="1" var="i">
                                                                <a href="/member/mypage?page=${i}&order=${recipeReviewMap.recipeReviewpager.order}&tab=2" class="rounded">${i}</a>
                                                            </c:forEach>
                                                            <a href="/member/mypage?page=${recipeReviewMap.recipeReviewpager.lastNum+1}&order=${recipeReviewMap.recipeReviewpager.order}&tab=2" class="btn rounded ${recipeReviewMap.recipeReviewpager.next ? '' : 'disabled'}">&raquo;</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:when>

                                            <c:when test="${tab=='4'}">
                                                
                                                <div id="tab-4" class="tab-pane fade show active p-0">
                                                    <div class="row g-4">
                                                        <div class="col-lg-12">
                                                            <div class="row g-4">
                                                                <c:forEach items="${wishList.wishListAr}" var="list">
                                                                    <div class="col-md-6 col-lg-4 col-xl-3">
                                                                        <div class="rounded position-relative fruite-item">
                                                                            <div class="fruite-img">
                                                                                <img src="img/fruite-item-5.jpg" class="img-fluid w-100 rounded-top" alt="">
                                                                            </div>
                                                                            <div class="text-white bg-secondary px-3 py-1 rounded position-absolute" style="top: 10px; left: 10px;">${list.recipe_category}</div>
                                                                            <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                                                <h4>${list.recipe_name}</h4>
                                                                                <!-- <p class="pp">${list.menu_recipe}</p> -->
                                                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                                                <a href="/recipe/detail?recipe_num=${list.recipe_num}" style="margin-left:18%" class="btn border border-secondary rounded-pill px-3 text-primary">찜 목록 보러가기</a>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </c:forEach>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-12">
                                                        <div class="pagination d-flex justify-content-center mt-5">
                                                            <a href="/member/mypage?page=${wishList.wishListPager.startNum-1}&order=${wishList.wishListPager.order}&tab=4" class="btn rounded ${wishList.wishListPager.pre ? '' : 'disabled'}">&laquo;</a>
                                                            <c:forEach begin="${wishList.wishListPager.startNum}" end="${wishList.wishListPager.lastNum}" step="1" var="i">
                                                                <a href="/member/mypage?page=${i}&order=${wishList.wishListPager.order}&tab=4" class="rounded">${i}</a>
                                                            </c:forEach>
                                                            <a href="/member/mypage?page=${wishList.wishListPager.lastNum+1}&order=${wishList.wishListPager.order}&tab=4" class="btn rounded ${wishList.wishListPager.next ? '' : 'disabled'}">&raquo;</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:when>


                                            <c:otherwise>
                                                <div id="tab-3" class="tab-pane fade show active p-0">
                                                    <div class="row g-4">
                                                        <div class="col-lg-12">
                                                            <div class="row g-4">
                                                                <c:forEach items="${recipeReplyMap.recipeReplyAr}" var="reply">
                                                                    <div class="col-md-6 col-lg-4 col-xl-3">
                                                                        <div class="rounded position-relative fruite-item">
                                                                            <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                                                <p class="pp">${reply.board_content}</p>
                                                                                <div class="d-flex justify-content-between flex-lg-wrap">
                                                                                    <a href="/recipe/detail?recipe_num=${reply.recipe_num}" style="margin-left:18%" class="btn border border-secondary rounded-pill px-3 text-primary">작성한 댓글 보러가기</a>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </c:forEach>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-12">
                                                        <div
                                                            class="pagination d-flex justify-content-center mt-5">
                                                            <a href="/member/mypage?page=${recipeReplyMap.recipeReplyPager.startNum-1}&order=${recipeReplyMap.recipeReplyPager.order}&tab=3"
                                                                class="btn rounded ${recipeReplyMap.recipeReplyPager.pre ? '' : 'disabled'}">&laquo;</a>
                                                            <c:forEach
                                                                begin="${recipeReplyMap.recipeReplyPager.startNum}"
                                                                end="${recipeReplyMap.recipeReplyPager.lastNum}"
                                                                step="1" var="i">
                                                                <a href="/member/mypage?page=${i}&order=${recipeReplyMap.recipeReplyPager.order}&tab=3"
                                                                    class="rounded">${i}</a>
                                                            </c:forEach>
                                                            <a href="/member/mypage?page=${recipeReplyMap.recipeReplyPager.lastNum+1}&order=${recipeReplyMap.recipeReplyPager.order}&tab=3"
                                                                class="btn rounded ${recipeReplyMap.recipeReplyPager.next ? '' : 'disabled'}">&raquo;</a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:otherwise>

                                            
                                        </c:choose>
                                    </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Fruits Shop End-->

<c:import url="/WEB-INF/views/templete/footer.jsp"></c:import>
<script src="/resources/member/js/mypage.js"></script>
</body>

</html>