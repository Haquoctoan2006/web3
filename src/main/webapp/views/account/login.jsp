<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Dang nhap</title></head>
<body>
<h2>Dang nhap</h2>
<c:if test="${not empty message}"><p style="color:green">${message}</p></c:if>
<c:if test="${not empty error}"><p style="color:red">${error}</p></c:if>
<form action="<c:url value='/login'/>" method="post">
    <label>Email:</label><br>
    <input type="email" name="email" required><br><br>
    <label>Mat khau:</label><br>
    <input type="password" name="password" required><br><br>
    <input type="submit" value="Dang nhap">
</form>
<p><a href="<c:url value='/forgot-password'/>">Quen mat khau?</a></p>
<p>Chua co tai khoan? <a href="<c:url value='/register'/>">Dang ky</a></p>
</body>
</html>
