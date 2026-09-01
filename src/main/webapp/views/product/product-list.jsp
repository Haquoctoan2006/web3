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
.grid { display:flex; flex-wrap:wrap; gap:16px; }
.card { border:1px solid #ccc; width:200px; padding:10px; text-decoration:none; color:#000; }
.card img { width:100%; height:140px; object-fit:cover; }
.pagination a, .pagination span { margin-right:6px; }
</style>
</head>
<body>

<nav>
    <a href="<c:url value='/home'/>">Trang chu</a> |
    <a href="<c:url value='/product'/>">San pham</a>
</nav>
<hr>

<h2>Tat ca san pham</h2>
<div class="grid">
<c:forEach items="${listproduct}" var="p">
    <a class="card" href="<c:url value='/product/detail?id=${p.productId}'/>">
        <c:choose>
            <c:when test="${not empty p.image and fn:startsWith(p.image, 'https')}">
                <c:url value="${p.image}" var="imgUrl"/>
            </c:when>
            <c:otherwise>
                <c:url value="/image?type=product&fname=${p.image}" var="imgUrl"/>
            </c:otherwise>
        </c:choose>
        <img src="${imgUrl}" alt="${p.productName}">
        <div>${p.productName}</div>
        <div><b><fmt:formatNumber value="${p.price}" pattern="#,##0"/></b> VND</div>
    </a>
</c:forEach>
</div>

<hr>
<c:if test="${totalPages > 0}">
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
<c:if test="${totalPages == 0}">
    <p>Chua co san pham nao. Vui long vao trang quan tri de them san pham.</p>
</c:if>

</body>
</html>
