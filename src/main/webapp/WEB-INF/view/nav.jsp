<nav class="navbar navbar-expand-lg bg-body-tertiary">
  <div class="container-fluid">
    <a class="navbar-brand" href="${rootDirectory}/home">Home</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
      data-bs-target="#navbarSupportedContent"
      aria-controls="navbarSupportedContent" aria-expanded="false"
      aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav">
        <% if (request.getSession().getAttribute("user") == null) { %>
          <li class="nav-item"><a class="nav-link" href="${rootDirectory}/signin">Sign In</a></li>
        <% } else { %>
          <li><img src="${rootDirectory}/profile_picture?id=${user.uuid}" style="border-radius: 50%; width: 40px; height: 40px;"></li>
          <li class="nav-item"><a class="nav-link" href="${rootDirectory}/profile">Profile</a></li>
          <li class="nav-item"><a class="nav-link" href="${rootDirectory}/signout">Sign Out</a></li>
        <% } %>
      </ul>
    </div>
  </div>
</nav>