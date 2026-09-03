<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib uri="jakarta.tags.core" prefix="c" %>
    <!DOCTYPE html>
    <html lang="vi">

    <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>
        <sitemesh:write property='title' /> - Quan tri
      </title>

      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

      <sitemesh:write property='head' />

      <style>
        body {
          background-color: #f1f3f5;
        }

        .sidebar {
          min-height: calc(100vh - 56px);
          background: #212529;
          padding-top: 20px;
        }

        .sidebar a {
          color: #ced4da;
          display: block;
          padding: 10px 20px;
          text-decoration: none;
        }

        .sidebar a:hover,
        .sidebar a.active {
          background: #343a40;
          color: #fff;
        }

        .admin-content {
          padding: 24px;
          background: #fff;
          border-radius: 8px;
          margin: 20px;
          box-shadow: 0 1px 3px rgba(0, 0, 0, .08);
        }
      </style>
    </head>

    <body>

      <nav class="navbar navbar-dark bg-dark">
        <div class="container-fluid">
          <a class="navbar-brand" href="<c:url value='/admin/products'/>">CategoryCRUD - Trang quan tri</a>
          <span class="navbar-text text-white">
            ${sessionScope.SESSION_USER.fullname} |
            <a href="<c:url value='/home'/>" class="text-white">Ve trang chu</a> |
            <a href="<c:url value='/logout'/>" class="text-white">Dang xuat</a>
          </span>
        </div>
      </nav>

      <div class="d-flex">
        <div class="sidebar" style="width:220px;">
          <a href="<c:url value='/admin/categories'/>">Quan ly Danh muc</a>
          <a href="<c:url value='/admin/products'/>">Quan ly San pham</a>
        </div>
        <div class="flex-grow-1">
          <div class="admin-content">
            <sitemesh:write property='body' />
          </div>
        </div>
      </div>

      <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>

    </html>