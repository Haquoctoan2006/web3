<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Chi tiet san pham</title>
<style>
.detail { display:flex; gap:24px; }
.detail img { width:320px; height:320px; object-fit:cover; }
</style>
</head>
<body>

<c:if test="${not empty error}"><p style="color:red">${error}</p></c:if>

<c:choose>
<c:when test="${empty product}">
    <p>Khong tim thay san pham.</p>
</c:when>
<c:otherwise>
    <div class="detail">
        <c:choose>
            <c:when test="${not empty product.image and fn:startsWith(product.image, 'https')}">
                <c:url value="${product.image}" var="imgUrl"/>
            </c:when>
            <c:otherwise>
                <c:url value="/image?type=product&fname=${product.image}" var="imgUrl"/>
            </c:otherwise>
        </c:choose>
        <img src="${imgUrl}" alt="${product.productName}">
        <div>
            <h2>${product.productName}</h2>
            <p><b>Gia:</b> <fmt:formatNumber value="${product.price}" pattern="#,##0"/> VND</p>
            <p><b>So luong con lai:</b> ${product.quantity}</p>
            <p><b>Danh muc:</b> ${product.category.categoryname}</p>
            <p><b>Mo ta:</b><br>${product.description}</p>

            <c:choose>
                <c:when test="${empty sessionScope.SESSION_USER}">
                    <p><a href="<c:url value='/login'/>">Dang nhap</a> de mua san pham nay.</p>
                </c:when>
                <c:when test="${product.quantity <= 0}">
                    <p style="color:red">San pham da het hang.</p>
                </c:when>
                <c:otherwise>
                    <form action="<c:url value='/order/buy'/>" method="post">
                        <input type="hidden" name="productId" value="${product.productId}">
                        <label>So luong:</label>
                        <input type="number" name="quantity" value="1" min="1" max="${product.quantity}" required>
                        <button type="submit">Mua ngay</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</c:otherwise>
</c:choose>

</body>
</html>
