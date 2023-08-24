
function confirmLogout(event) {
    var confirmMessage = event.target.getAttribute('data-confirm');
    if (!confirm(confirmMessage)) {
        event.preventDefault();
    }
}

// Attach the confirmLogout function to the "click" event of the logout link
var logoutLink = document.querySelector('a[data-confirm]');
if (logoutLink) {
    logoutLink.addEventListener('click', confirmLogout);
}


const container = document.querySelector(".container"),
      pwFields = document.querySelectorAll(".password"),
      signUp = document.querySelector(".signup-link"),
      login = document.querySelector(".login-link");

    // js code to appear signup and login form
    signUp.addEventListener("click", ( )=>{
        container.classList.add("active");
    });
    login.addEventListener("click", ( )=>{
        container.classList.remove("active");
    });

