<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
            <%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
                <!DOCTYPE html>
                <html>

                <head>
                    <meta charset="UTF-8">
                    <title>Danh sach san pham</title>
                    <style>
                        .grid {
                            display: flex;
                            flex-wrap: wrap;
                            gap: 16px;
                        }

                        .card {
                            border: 1px solid #ccc;
                            width: 200px;
                            padding: 10px;
                            text-decoration: none;
                            color: #000;
                        }

                        .card img {
                            width: 100%;
                            height: 140px;
                            object-fit: cover;
                        }

                        .pagination a,
                        .pagination span {
                            margin-right: 6px;
                        }
                    </style>
                </head>

                <body>

                    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:10px;">
                        <h2 style="margin:0;">
                            <c:choose>
                                <c:when test="${not empty keyword}">Ket qua tim kiem cho "${keyword}"</c:when>
                                <c:otherwise>Tat ca san pham</c:otherwise>
                            </c:choose>
                        </h2>

                        <form action="<c:url value=" /product" />" method="get" style="display:flex; gap:6px;">
                        <input type="text" name="keyword" value="${keyword}" placeholder="Tim theo ten san pham...">
                        <button type="submit">Tim kiem</button>
                        <c:if test="${not empty keyword}">
                            <a href="<c:url value=" /product" />">Xoa loc</a>
                        </c:if>
                        </form>
                    </div>

                    <div class="grid">
                        <c:forEach items="${listproduct}" var="p">
                            <a class="card" href="<c:url value='/product/detail?id=${p.productId}'/>">
                                <c:choose>
                                    <c:when test="${not empty p.image and fn:startsWith(p.image, 'https')}">
                                        <c:url value="${p.image}" var="imgUrl" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/image?type=product&fname=${p.image}" var="imgUrl" />
                                    </c:otherwise>
                                </c:choose>
                                <img src="${imgUrl}" alt="${p.productName}">
                                <div>${p.productName}</div>
                                <div><b>
                                        <fmt:formatNumber value="${p.price}" pattern="#,##0" />
                                    </b> VND</div>
                            </a>
                        </c:forEach>
                    </div>

                    <c:if test="${empty listproduct}">
                        <p>
                            <c:choose>
                                <c:when test="${not empty keyword}">Khong tim thay san pham nao phu hop.</c:when>
                                <c:otherwise>Chua co san pham nao. Vui long vao trang quan tri de them san pham.
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </c:if>

                    <hr>
                    <c:if test="${empty keyword and totalPages > 0}">
                        <div class="pagination">
                            <c:if test="${currentPage > 0}">
                                <a href="<c:url value='/product?page=${currentPage - 1}'/>">&laquo; Truoc</a>
                            </c:if>
                            <c:forEach begin="0" end="${totalPages - 1}" var="i">
                                <c:choose>
                                    <c:when test="${i == currentPage}">
                                        <span><b>${i + 1}</b></span>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="<c:url value='/product?page=${i}'/>">${i + 1}</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:if test="${currentPage < totalPages - 1}">
                                <a href="<c:url value='/product?page=${currentPage + 1}'/>">Sau &raquo;</a>
                            </c:if>
                        </div>
                    </c:if>

                </body>

                </html>