<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Dang nhap</title></head>
<body>
<h2>Dang nhap</h2>
<c:if test="${not empty message}"><div class="alert alert-success">${message}</div></c:if>
<c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
<form action="<c:url value='/login'/>" method="post" class="col-md-5" novalidate>
    <div class="mb-3">
        <label class="form-label">Email:</label>
        <input type="email" name="email" class="form-control" required>
        <div class="invalid-feedback">Vui long nhap dung dinh dang email.</div>
    </div>
    <div class="mb-3">
        <label class="form-label">Mat khau:</label>
        <input type="password" name="password" class="form-control" required minlength="6">
        <div class="invalid-feedback">Mat khau phai tu 6 ky tu tro len.</div>
    </div>
    <button type="submit" class="btn btn-primary">Dang nhap</button>
</form>
<p class="mt-3"><a href="<c:url value='/forgot-password'/>">Quen mat khau?</a></p>
<p>Chua co tai khoan? <a href="<c:url value='/register'/>">Dang ky</a></p>

<script>
(function () {
  'use strict';
  Array.prototype.slice.call(document.querySelectorAll('form')).forEach(function (form) {
    form.addEventListener('submit', function (event) {
      if (!form.checkValidity()) { event.preventDefault(); event.stopPropagation(); }
      form.classList.add('was-validated');
    }, false);
  });
})();
</script>
</body>
</html>
