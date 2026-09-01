<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Them san pham</title></head>
<body>

<h2>Them san pham</h2>
<form action="<c:url value='/admin/product/insert'/>" method="post" enctype="multipart/form-data">

    <label>Ten san pham:</label><br>
    <input type="text" name="productName" required><br><br>

    <label>Mo ta:</label><br>
    <textarea name="description" rows="4" cols="40"></textarea><br><br>

    <label>Gia mua (gia von, chi Admin xem):</label><br>
    <input type="number" step="0.01" name="importPrice" value="0"><br><br>

    <label>Gia ban:</label><br>
    <input type="number" step="0.01" name="price" required><br><br>

    <label>So luong:</label><br>
    <input type="number" name="quantity" required><br><br>

    <label>Danh muc:</label><br>
    <select name="categoryId" required>
        <c:forEach items="${listcate}" var="c">
            <option value="${c.categoryid}">${c.categoryname}</option>
        </c:forEach>
    </select><br><br>

    <label>Anh san pham:</label><br>
    <input type="file" name="image"><br><br>

    <label>Trang thai:</label><br>
    <input type="radio" id="ston" name="status" value="1" checked>
    <label for="ston">Hoat dong</label>
    <input type="radio" id="stoff" name="status" value="0">
    <label for="stoff">Khoa</label>

    <br><br>
    <input type="submit" value="Them">
    <a href="<c:url value='/admin/products'/>">Quay lai</a>
</form>

</body>
</html>
