document.addEventListener("DOMContentLoaded", function() {
  const loginLink = document.getElementById("login-link");
  loginLink.addEventListener("click", function() {
    // Generate the /login link and navigate to it
    const loginUrl = "/login";
    window.location.href = loginUrl;
  });
});