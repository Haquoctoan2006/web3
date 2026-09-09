<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User List</title>
</head>
<body>

<h2>Quan ly Nguoi dung</h2>

<div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:10px;">
    <a href="<c:url value="/admin/user/add"/>" class="btn btn-primary btn-sm">+ Them nguoi dung</a>

    <form action="<c:url value="/admin/users"/>" method="get" style="display:flex; gap:6px;">
        <input type="text" name="keyword" value="${keyword}" placeholder="Tim theo ten hoac email..."
               class="form-control form-control-sm" style="width:250px;">
        <button type="submit" class="btn btn-secondary btn-sm">Tim kiem</button>
        <c:if test="${not empty keyword}">
            <a href="<c:url value="/admin/users"/>" class="btn btn-outline-secondary btn-sm">Xoa loc</a>
        </c:if>
    </form>
</div>

<table border="1" width="100%" cellpadding="5" class="table table-bordered">
<tr>
    <th>STT</th>
    <th>Avatar</th>
    <th>Ho ten</th>
    <th>Email</th>
    <th>SDT</th>
    <th>Vai tro</th>
    <th>Trang thai</th>
    <th>Hanh dong</th>
</tr>
<c:forEach items="${listuser}" var="us" varStatus="STT">
<tr>
    <td>${STT.index + 1}</td>
    <td>
        <c:url value="/image?fname=${us.avatar}&type=avatar" var="imgUrl"/>
        <img height="60" width="60" src="${imgUrl}" onerror="this.src='<c:url value="/image?fname=avatar.png&type=avatar"/>'">
    </td>
    <td>${us.fullname}</td>
    <td>${us.email}</td>
    <td>${us.phone}</td>
    <td>
        <c:choose>
            <c:when test="${us.role == 1}">Admin</c:when>
            <c:otherwise>Khach hang</c:otherwise>
        </c:choose>
    </td>
    <td>
        <c:choose>
            <c:when test="${us.active == 1}">Da kich hoat</c:when>
            <c:otherwise>Chua/Khoa</c:otherwise>
        </c:choose>
    </td>
    <td>
        <a href="<c:url value='/admin/user/edit?id=${us.userId}'/>">Sua</a>
        |
        <a href="<c:url value='/admin/user/delete?id=${us.userId}'/>"
           onclick="return confirm('Ban co chac muon xoa nguoi dung nay?');">Xoa</a>
    </td>
</tr>
</c:forEach>
<c:if test="${empty listuser}">
<tr><td colspan="8" style="text-align:center;">Khong tim thay nguoi dung nao.</td></tr>
</c:if>
</table>

</body>
</html>
