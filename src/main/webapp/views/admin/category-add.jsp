<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Category</title>
</head>
<body>

<h2>Add Category</h2>
<form action="<c:url value="/admin/category/insert"/>" method="post" enctype="multipart/form-data" novalidate>

    <label for="categoryname">Category name:</label><br>
    <input type="text" id="categoryname" name="categoryname" class="form-control" style="max-width:400px"
           required minlength="2" maxlength="50"><br><br>

    <label for="images">Link images:</label><br>
    <input type="text" id="images" name="images"><br><br>

    <label for="images1">Upload images:</label><br>
    <input type="file" id="images1" name="images1"><br><br>

    <label>Status</label><br>
    <input type="radio" id="ston" name="status" value="1" checked>
    <label for="ston">Hoat dong</label><br>
    <input type="radio" id="stoff" name="status" value="0">
    <label for="stoff">Khoa</label>

    <br><br>
    <input type="submit" value="Insert">
    <a href="<c:url value="/admin/categories"/>">Back</a>
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
