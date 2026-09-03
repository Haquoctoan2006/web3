<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Quen mat khau</title></head>
<body>
<h2>Quen mat khau</h2>
<c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
<form action="<c:url value='/forgot-password'/>" method="post" class="col-md-5" novalidate>
    <div class="mb-3">
        <label class="form-label">Nhap email da dang ky:</label>
        <input type="email" name="email" class="form-control" required>
        <div class="invalid-feedback">Vui long nhap dung dinh dang email.</div>
    </div>
    <button type="submit" class="btn btn-primary">Gui ma OTP</button>
</form>

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
