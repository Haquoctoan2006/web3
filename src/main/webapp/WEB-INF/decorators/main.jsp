<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib uri="jakarta.tags.core" prefix="c" %>
    <!DOCTYPE html>
    <html lang="vi">

    <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>
        <sitemesh:write property='title' /> - Convenient Shop
      </title>

      <!-- Bootstrap 5 (CDN) -->
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

      <sitemesh:write property='head' />

      <style>
        body {
          background-color: #f8f9fa;
        }

        .navbar-brand {
          font-weight: 600;
        }

        main.container {
          background: #fff;
          padding: 24px;
          border-radius: 8px;
          margin-top: 20px;
          margin-bottom: 40px;
          box-shadow: 0 1px 3px rgba(0, 0, 0, .08);
        }

        footer {
          text-align: center;
          padding: 16px;
          color: #6c757d;
          font-size: .9em;
        }
      </style>
    </head>

    <body>

      <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
          <a class="navbar-brand" href="<c:url value='/home'/>">Convenient Shop</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="mainNav">
            <ul class="navbar-nav me-auto">
              <li class="nav-item"><a class="nav-link" href="<c:url value='/home'/>">Trang chu</a></li>
              <li class="nav-item"><a class="nav-link" href="<c:url value='/product'/>">San pham</a></li>
              <c:if test="${not empty sessionScope.SESSION_USER}">
                <li class="nav-item"><a class="nav-link" href="<c:url value='/orders'/>">Don hang cua toi</a></li>
              </c:if>
            </ul>
            <ul class="navbar-nav">
              <c:choose>
                <c:when test="${not empty sessionScope.SESSION_USER}">
                  <c:if test="${sessionScope.SESSION_USER.role == 1}">
                    <li class="nav-item"><a class="nav-link" href="<c:url value='/admin/products'/>">Quan tri</a></li>
                  </c:if>
                  <li class="nav-item"><a class="nav-link" href="<c:url value='/profile'/>">Xin chao,
                      ${sessionScope.SESSION_USER.fullname}</a></li>
                  <li class="nav-item"><a class="nav-link" href="<c:url value='/logout'/>">Dang xuat</a></li>
                </c:when>
                <c:otherwise>
                  <li class="nav-item"><a class="nav-link" href="<c:url value='/login'/>">Dang nhap</a></li>
                  <li class="nav-item"><a class="nav-link" href="<c:url value='/register'/>">Dang ky</a></li>
                </c:otherwise>
              </c:choose>
            </ul>
          </div>
        </div>
      </nav>

      <main class="container">
        <sitemesh:write property='body' />
      </main>



      <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>

    </html>