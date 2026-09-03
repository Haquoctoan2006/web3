<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Quan tri san pham</title></head>
<body>

<h2>Quan tri san pham</h2>
<a href="<c:url value='/admin/product/add'/>" class="btn btn-primary btn-sm mb-3">Them san pham</a>
<hr>
<table border="1" width="100%" cellpadding="5">
<tr>
    <th>STT</th><th>Anh</th><th>Ten san pham</th><th>Gia mua</th><th>Gia ban</th><th>Loi nhuan/sp</th><th>SL</th><th>Danh muc</th><th>Trang thai</th><th>Hanh dong</th>
</tr>
<c:forEach items="${listproduct}" var="p" varStatus="STT">
<tr>
    <td>${STT.index + 1}</td>
    <c:choose>
        <c:when test="${not empty p.image and fn:startsWith(p.image, 'https')}">
            <c:url value="${p.image}" var="imgUrl"/>
        </c:when>
        <c:otherwise>
            <c:url value="/image?type=product&fname=${p.image}" var="imgUrl"/>
        </c:otherwise>
    </c:choose>
    <td><img height="80" width="100" src="${imgUrl}"/></td>
    <td>${p.productName}</td>
    <td><fmt:formatNumber value="${p.importPrice}" pattern="#,##0"/> d</td>
    <td><fmt:formatNumber value="${p.price}" pattern="#,##0"/> d</td>
    <td><fmt:formatNumber value="${p.price - p.importPrice}" pattern="#,##0"/> d</td>
    <td>${p.quantity}</td>
    <td>${p.category.categoryname}</td>
    <td><c:choose><c:when test="${p.status == 1}">Hoat dong</c:when><c:otherwise>Khoa</c:otherwise></c:choose></td>
    <td>
        <a href="<c:url value='/admin/product/edit?id=${p.productId}'/>">Sua</a>
        | <a href="<c:url value='/admin/product/delete?id=${p.productId}'/>" onclick="return confirm('Xoa san pham nay?');">Xoa</a>
    </td>
</tr>
</c:forEach>
</table>

</body>
</html>
