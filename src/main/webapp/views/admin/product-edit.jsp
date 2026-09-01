<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Sua san pham</title></head>
<body>

<h2>Sua san pham</h2>
<form action="<c:url value='/admin/product/update'/>" method="post" enctype="multipart/form-data">
    <input type="hidden" name="productId" value="${product.productId}">

    <label>Ten san pham:</label><br>
    <input type="text" name="productName" value="${product.productName}" required><br><br>

    <label>Mo ta:</label><br>
    <textarea name="description" rows="4" cols="40">${product.description}</textarea><br><br>

    <label>Gia mua (gia von, chi Admin xem):</label><br>
    <input type="number" step="0.01" name="importPrice" value="<fmt:formatNumber value='${product.importPrice}' pattern='0.##' groupingUsed='false'/>"><br><br>

    <label>Gia ban:</label><br>
    <input type="number" step="0.01" name="price" value="<fmt:formatNumber value='${product.price}' pattern='0.##' groupingUsed='false'/>" required><br><br>

    <label>So luong:</label><br>
    <input type="number" name="quantity" value="${product.quantity}" required><br><br>

    <label>Danh muc:</label><br>
    <select name="categoryId" required>
        <c:forEach items="${listcate}" var="c">
            <option value="${c.categoryid}" ${c.categoryid == product.category.categoryid ? 'selected' : ''}>${c.categoryname}</option>
        </c:forEach>
    </select><br><br>

    <c:choose>
        <c:when test="${not empty product.image and fn:startsWith(product.image, 'https')}">
            <c:url value="${product.image}" var="imgUrl"/>
        </c:when>
        <c:otherwise>
            <c:url value="/image?type=product&fname=${product.image}" var="imgUrl"/>
        </c:otherwise>
    </c:choose>
    <img height="100" width="140" src="${imgUrl}"/><br><br>

    <label>Doi anh san pham:</label><br>
    <input type="file" name="image"><br><br>

    <label>Trang thai:</label><br>
    <input type="radio" id="ston" name="status" value="1" ${product.status == 1 ? 'checked' : ''}>
    <label for="ston">Hoat dong</label>
    <input type="radio" id="stoff" name="status" value="0" ${product.status != 1 ? 'checked' : ''}>
    <label for="stoff">Khoa</label>

    <br><br>
    <input type="submit" value="Cap nhat">
    <a href="<c:url value='/admin/products'/>">Quay lai</a>
</form>

</body>
</html>
