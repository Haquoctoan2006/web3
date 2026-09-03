<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>Them san pham</title></head>
<body>

<h2>Them san pham</h2>
<form action="<c:url value='/admin/product/insert'/>" method="post" enctype="multipart/form-data" class="col-md-6" novalidate>

    <div class="mb-3">
        <label class="form-label">Ten san pham:</label>
        <input type="text" name="productName" class="form-control" required minlength="2" maxlength="150">
        <div class="invalid-feedback">Vui long nhap ten san pham (toi thieu 2 ky tu).</div>
    </div>

    <div class="mb-3">
        <label class="form-label">Mo ta:</label>
        <textarea name="description" class="form-control" rows="4" maxlength="2000"></textarea>
    </div>

    <div class="mb-3">
        <label class="form-label">Gia mua (gia von, chi Admin xem):</label>
        <input type="number" step="0.01" min="0" name="importPrice" class="form-control" value="0">
    </div>

    <div class="mb-3">
        <label class="form-label">Gia ban:</label>
        <input type="number" step="0.01" min="0" name="price" class="form-control" required>
        <div class="invalid-feedback">Gia ban phai la so >= 0.</div>
    </div>

    <div class="mb-3">
        <label class="form-label">So luong:</label>
        <input type="number" min="0" name="quantity" class="form-control" required>
        <div class="invalid-feedback">So luong phai la so nguyen >= 0.</div>
    </div>

    <div class="mb-3">
        <label class="form-label">Danh muc:</label>
        <select name="categoryId" class="form-select" required>
            <option value="" selected disabled>-- Chon danh muc --</option>
            <c:forEach items="${listcate}" var="c">
                <option value="${c.categoryid}">${c.categoryname}</option>
            </c:forEach>
        </select>
        <div class="invalid-feedback">Vui long chon danh muc.</div>
    </div>

    <div class="mb-3">
        <label class="form-label">Anh san pham:</label>
        <input type="file" name="image" class="form-control" accept="image/*">
    </div>

    <div class="mb-3">
        <label class="form-label d-block">Trang thai:</label>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" id="ston" name="status" value="1" checked>
            <label class="form-check-label" for="ston">Hoat dong</label>
        </div>
        <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" id="stoff" name="status" value="0">
            <label class="form-check-label" for="stoff">Khoa</label>
        </div>
    </div>

    <button type="submit" class="btn btn-primary">Them</button>
    <a href="<c:url value='/admin/products'/>" class="btn btn-outline-secondary">Quay lai</a>
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
