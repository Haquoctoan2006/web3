<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Category</title>
</head>
<body>

<h2>Edit Category</h2>
<form action="<c:url value="/admin/category/update"/>" method="post" enctype="multipart/form-data" novalidate>
    <input type="hidden" name="categoryid" value="${cate.categoryid}">

    <label for="categoryname">Category name:</label><br>
    <input type="text" id="categoryname" name="categoryname" value="${cate.categoryname}" class="form-control"
           style="max-width:400px" required minlength="2" maxlength="50"><br><br>

    <label for="images">Link images:</label><br>
    <input type="text" id="images" name="images" value="${cate.images}"><br><br>

    <c:choose>
        <c:when test="${not empty cate.images and fn:startsWith(cate.images, 'https')}">
            <c:url value="${cate.images}" var="imgUrl"/>
        </c:when>
        <c:otherwise>
            <c:url value="/image?fname=${cate.images}" var="imgUrl"/>
        </c:otherwise>
    </c:choose>
    <img height="100" width="140" src="${imgUrl}" /><br><br>

    <label for="images1">Upload images:</label><br>
    <input type="file" id="images1" name="images1"><br><br>

    <label>Status</label><br>
    <input type="radio" id="ston" name="status" value="1" ${cate.status == 1 ? 'checked' : ''}>
    <label for="ston">Hoat dong</label><br>
    <input type="radio" id="stoff" name="status" value="0" ${cate.status != 1 ? 'checked' : ''}>
    <label for="stoff">Khoa</label>

    <br><br>
    <input type="submit" value="Update">
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
