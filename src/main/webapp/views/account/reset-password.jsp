<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Dat lai mat khau</title></head>
<body>
<h2>Dat lai mat khau</h2>
<p>Ma OTP da duoc gui toi email: <b>${email}</b></p>
<c:if test="${not empty message}"><p style="color:green">${message}</p></c:if>
<c:if test="${not empty error}"><p style="color:red">${error}</p></c:if>
<form action="<c:url value='/reset-password'/>" method="post">
    <input type="hidden" name="email" value="${email}">
    <label>Ma OTP:</label><br>
    <input type="text" name="otp" required><br><br>
    <label>Mat khau moi:</label><br>
    <input type="password" name="newPassword" required><br><br>
    <input type="submit" value="Doi mat khau">
</form>

<form action="<c:url value='/reset-password'/>" method="post" style="margin-top:10px;">
    <input type="hidden" name="email" value="${email}">
    <button type="submit">Gui lai ma OTP</button>
</form>
</body>
</html>
