<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang chu</title>
<style>
.grid { display:flex; flex-wrap:wrap; gap:16px; }
.card { border:1px solid #ccc; width:200px; padding:10px; text-decoration:none; color:#000; }
.card img { width:100%; height:140px; object-fit:cover; }
</style>
</head>
<body>

<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>

<h2>10 san pham moi nhat</h2>
<div class="grid">
<c:forEach items="${latestProducts}" var="p">
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

</body>
</html>
