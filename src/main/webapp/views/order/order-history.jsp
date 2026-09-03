<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Don hang cua toi</title>
<style>
table { border-collapse: collapse; width: 100%; margin-bottom: 20px; }
td, th { border: 1px solid #ccc; padding: 6px 10px; }
</style>
</head>
<body>

<h2>Don hang cua toi</h2>

<c:if test="${empty orders}">
    <p>Ban chua co don hang nao. <a href="<c:url value='/product'/>">Mua sam ngay</a></p>
</c:if>

<c:forEach items="${orders}" var="o">
    <h3>Don hang #${o.orderId} - <fmt:formatDate value="${o.orderDate}" pattern="dd/MM/yyyy HH:mm"/></h3>
    <table>
        <tr>
            <th>San pham</th>
            <th>Gia (tai thoi diem mua)</th>
            <th>So luong</th>
            <th>Thanh tien</th>
        </tr>
        <c:forEach items="${o.orderDetails}" var="d">
        <tr>
            <td>${d.product.productName}</td>
            <td><fmt:formatNumber value="${d.priceAtPurchase}" pattern="#,##0"/> VND</td>
            <td>${d.quantity}</td>
            <td><fmt:formatNumber value="${d.subTotal}" pattern="#,##0"/> VND</td>
        </tr>
        </c:forEach>
    </table>
    <p><b>Tong tien don hang:</b> <fmt:formatNumber value="${o.totalAmount}" pattern="#,##0"/> VND</p>
    <hr>
</c:forEach>

</body>
</html>
