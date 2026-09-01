<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Dang ky</title></head>
<body>
<h2>Dang ky tai khoan</h2>
<c:if test="${not empty error}"><p style="color:red">${error}</p></c:if>
<form action="<c:url value='/register'/>" method="post">
    <label>Ho ten:</label><br>
    <input type="text" name="fullname" required><br><br>
    <label>Email:</label><br>
    <input type="email" name="email" required><br><br>
    <label>Mat khau:</label><br>
    <input type="password" name="password" required><br><br>
    <input type="submit" value="Dang ky">
</form>
<p>Da co tai khoan? <a href="<c:url value='/login'/>">Dang nhap</a></p>
</body>
</html>
