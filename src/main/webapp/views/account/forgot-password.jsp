<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Quen mat khau</title></head>
<body>
<h2>Quen mat khau</h2>
<c:if test="${not empty error}"><p style="color:red">${error}</p></c:if>
<form action="<c:url value='/forgot-password'/>" method="post">
    <label>Nhap email da dang ky:</label><br>
    <input type="email" name="email" required><br><br>
    <input type="submit" value="Gui ma OTP">
</form>
</body>
</html>
