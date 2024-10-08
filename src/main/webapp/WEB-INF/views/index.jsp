<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="utf-8">

            <c:import url="/WEB-INF/views/templete/header.jsp"></c:import>
        </head>

        <body>
            <c:import url="/WEB-INF/views/templete/nav.jsp"></c:import>
            <!-- Hero Start -->
            <div class="container-fluid py-5 mb-5 hero-header">
                <div class="container py-5">
                    <div class="row g-5 align-items-center">
                        <div class="col-md-12 col-lg-7">
                            <h4 class="mb-3 text-secondary">RECIPES FOR YOU</h4>
                            <h1 class="mb-5 display-3 text-primary">최고의 레시피와 신선한 재료를 한 곳에서!</h1>
                        </div>
                        <div class="col-md-12 col-lg-5">
                            <div id="carouselId" class="carousel slide position-relative" data-bs-ride="carousel">
                                <div class="carousel-inner" role="listbox">
                                    <div class="carousel-item active rounded">
                                        <img src="/resources/img/hero-img-1.png"
                                            class="img-fluid w-100 h-100 bg-secondary rounded" alt="First slide">
                                        <a href="/recipe/list" class="btn px-4 py-2 text-white rounded">레시피 보러가기</a>
                                    </div>
                                    <div class="carousel-item rounded">
                                        <img src="/resources/img/hero-img-2.jpg" class="img-fluid w-100 h-100 rounded"
                                            alt="Second slide">
                                        <a href="/food/list" class="btn px-4 py-2 text-white rounded">스토어 보러가기</a>
                                    </div>
                                </div>
                                <button class="carousel-control-prev" type="button" data-bs-target="#carouselId"
                                    data-bs-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Previous</span>
                                </button>
                                <button class="carousel-control-next" type="button" data-bs-target="#carouselId"
                                    data-bs-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Next</span>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Hero End -->
            <c:import url="/WEB-INF/views/templete/footer.jsp"></c:import>
        </body>

        </html>