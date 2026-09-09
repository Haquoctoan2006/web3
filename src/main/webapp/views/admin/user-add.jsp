<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add User</title>
</head>
<body>

<h2>Them nguoi dung</h2>
<c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>

<form action="<c:url value="/admin/user/insert"/>" method="post" enctype="multipart/form-data" novalidate>

    <label for="fullname">Ho ten:</label><br>
    <input type="text" id="fullname" name="fullname" value="${us.fullname}" class="form-control" style="max-width:400px"
           required minlength="2" maxlength="100"><br><br>

    <label for="email">Email:</label><br>
    <input type="email" id="email" name="email" value="${us.email}" class="form-control" style="max-width:400px"
           required><br><br>

    <label for="password">Mat khau:</label><br>
    <input type="password" id="password" name="password" class="form-control" style="max-width:400px"
           required minlength="6"><br><br>

    <label for="phone">So dien thoai:</label><br>
    <input type="text" id="phone" name="phone" value="${us.phone}" class="form-control" style="max-width:400px"><br><br>

    <label for="avatar1">Anh dai dien:</label><br>
    <input type="file" id="avatar1" name="avatar1"><br><br>

    <label>Vai tro</label><br>
    <input type="radio" id="rolecus" name="role" value="0" checked>
    <label for="rolecus">Khach hang</label><br>
    <input type="radio" id="roleadmin" name="role" value="1">
    <label for="roleadmin">Admin</label>
    <br><br>

    <label>Trang thai</label><br>
    <input type="radio" id="acton" name="active" value="1" checked>
    <label for="acton">Kich hoat</label><br>
    <input type="radio" id="actoff" name="active" value="0">
    <label for="actoff">Khoa</label>

    <br><br>
    <input type="submit" value="Them moi" class="btn btn-primary">
    <a href="<c:url value="/admin/users"/>" class="btn btn-secondary">Quay lai</a>
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
