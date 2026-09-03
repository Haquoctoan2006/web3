<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Dang ky</title></head>
<body>
<h2>Dang ky tai khoan</h2>
<c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
<form action="<c:url value='/register'/>" method="post" class="col-md-5" novalidate>
    <div class="mb-3">
        <label class="form-label">Ho ten:</label>
        <input type="text" name="fullname" class="form-control" required minlength="2" maxlength="100"
               pattern="^[\p{L} ]+$" title="Ho ten chi gom chu cai va khoang trang">
        <div class="invalid-feedback">Vui long nhap ho ten hop le (chi chu cai, toi thieu 2 ky tu).</div>
    </div>
    <div class="mb-3">
        <label class="form-label">Email:</label>
        <input type="email" name="email" class="form-control" required maxlength="150">
        <div class="invalid-feedback">Vui long nhap dung dinh dang email.</div>
    </div>
    <div class="mb-3">
        <label class="form-label">Mat khau:</label>
        <input type="password" name="password" class="form-control" required minlength="6" maxlength="50">
        <div class="invalid-feedback">Mat khau phai tu 6 ky tu tro len.</div>
    </div>
    <button type="submit" class="btn btn-primary">Dang ky</button>
</form>
<p class="mt-3">Da co tai khoan? <a href="<c:url value='/login'/>">Dang nhap</a></p>

<script>
(function () {
  'use strict';
  var forms = document.querySelectorAll('form');
  Array.prototype.slice.call(forms).forEach(function (form) {
    form.addEventListener('submit', function (event) {
      if (!form.checkValidity()) {
        event.preventDefault();
        event.stopPropagation();
      }
      form.classList.add('was-validated');
    }, false);
  });
})();
</script>
</body>
</html>
