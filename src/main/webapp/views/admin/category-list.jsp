<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Category List</title>
</head>
<body>

<h2>Category Management</h2>

<div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:10px;">
    <a href="<c:url value="/admin/category/add"/>">Add Category</a>

    <form action="<c:url value="/admin/categories"/>" method="get" style="display:flex; gap:6px;">
        <input type="text" name="keyword" value="${keyword}" placeholder="Tim theo ten danh muc...">
        <button type="submit">Tim kiem</button>
        <c:if test="${not empty keyword}">
            <a href="<c:url value="/admin/categories"/>">Xoa loc</a>
        </c:if>
    </form>
</div>
<hr>
<table border="1" width="100%" cellpadding="5">
<tr>
    <th>STT</th>
    <th>Images</th>
    <th>Category name</th>
    <th>Status</th>
    <th>Action</th>
</tr>
<c:forEach items="${listcate}" var="cate" varStatus="STT">
<tr>
    <td>${STT.index + 1}</td>
    <c:choose>
        <c:when test="${not empty cate.images and fn:startsWith(cate.images, 'https')}">
            <c:url value="${cate.images}" var="imgUrl"/>
        </c:when>
        <c:otherwise>
            <c:url value="/image?fname=${cate.images}" var="imgUrl"/>
        </c:otherwise>
    </c:choose>
    <td><img height="100" width="140" src="${imgUrl}" /></td>
    <td>${cate.categoryname}</td>
    <td>
        <c:choose>
            <c:when test="${cate.status == 1}">Hoat dong</c:when>
            <c:otherwise>Khoa</c:otherwise>
        </c:choose>
    </td>
    <td>
        <a href="<c:url value='/admin/category/edit?id=${cate.categoryid}'/>">Sua</a>
        |
        <a href="<c:url value='/admin/category/delete?id=${cate.categoryid}'/>"
           onclick="return confirm('Ban co chac muon xoa?');">Xoa</a>
    </td>
</tr>
</c:forEach>
</table>

</body>
</html>
