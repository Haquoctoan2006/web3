<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Xac thuc OTP</title></head>
<body>
<h2>Xac thuc OTP kich hoat tai khoan</h2>
<p>Ma OTP da duoc gui toi email: <b>${email}</b></p>
<c:if test="${not empty message}"><div class="alert alert-success">${message}</div></c:if>
<c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>

<form action="<c:url value='/verify-otp'/>" method="post" class="col-md-5" novalidate>
    <input type="hidden" name="email" value="${email}">
    <div class="mb-3">
        <label class="form-label">Nhap ma OTP:</label>
        <input type="text" name="otp" class="form-control" required pattern="\d{6}" title="Ma OTP gom 6 chu so">
        <div class="invalid-feedback">Ma OTP gom dung 6 chu so.</div>
    </div>
    <button type="submit" class="btn btn-primary">Xac thuc</button>
</form>

<form action="<c:url value='/resend-otp'/>" method="post" class="mt-2">
    <input type="hidden" name="email" value="${email}">
    <button type="submit" class="btn btn-outline-secondary btn-sm">Gui lai ma OTP</button>
</form>

<script>
(function () {
  'use strict';
  Array.prototype.slice.call(document.querySelectorAll('form[novalidate]')).forEach(function (form) {
    form.addEventListener('submit', function (event) {
      if (!form.checkValidity()) { event.preventDefault(); event.stopPropagation(); }
      form.classList.add('was-validated');
    }, false);
  });
})();
</script>
</body>
</html>
