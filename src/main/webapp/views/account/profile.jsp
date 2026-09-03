<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Ho so ca nhan</title></head>
<body>

<h2>Ho so ca nhan</h2>

<c:if test="${not empty message}"><div class="alert alert-success">${message}</div></c:if>
<c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>

<div class="row">
  <div class="col-md-3 text-center mb-3">
    <c:choose>
        <c:when test="${not empty user.avatar}">
            <c:url value="/image?type=avatar&fname=${user.avatar}" var="avatarUrl"/>
        </c:when>
        <c:otherwise>
            <c:url value="https://via.placeholder.com/160x160.png?text=No+Avatar" var="avatarUrl"/>
        </c:otherwise>
    </c:choose>
    <img src="${avatarUrl}" alt="Avatar" class="rounded-circle border" width="160" height="160" style="object-fit:cover;">
  </div>

  <div class="col-md-7">
    <form action="<c:url value='/profile/update'/>" method="post" enctype="multipart/form-data" novalidate>

        <div class="mb-3">
            <label class="form-label">Email (khong the thay doi):</label>
            <input type="email" class="form-control" value="${user.email}" disabled>
        </div>

        <div class="mb-3">
            <label class="form-label">Ho ten:</label>
            <input type="text" name="fullname" class="form-control" value="${user.fullname}"
                   required minlength="2" maxlength="100" pattern="^[\p{L} ]+$"
                   title="Ho ten chi gom chu cai va khoang trang">
            <div class="invalid-feedback">Vui long nhap ho ten hop le.</div>
        </div>

        <div class="mb-3">
            <label class="form-label">So dien thoai:</label>
            <input type="tel" name="phone" class="form-control" value="${user.phone}"
                   pattern="^0[0-9]{9,10}$" title="So dien thoai VN, bat dau bang 0, 10-11 chu so">
            <div class="invalid-feedback">So dien thoai khong hop le (vd: 0912345678).</div>
        </div>

        <div class="mb-3">
            <label class="form-label">Anh dai dien moi (neu muon doi):</label>
            <input type="file" name="avatar" class="form-control" accept="image/*">
        </div>

        <button type="submit" class="btn btn-primary">Luu thay doi</button>
    </form>
  </div>
</div>

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
