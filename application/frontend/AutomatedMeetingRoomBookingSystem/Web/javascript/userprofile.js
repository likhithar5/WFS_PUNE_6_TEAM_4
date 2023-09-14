const urlParams = new URLSearchParams(window.location.search);
const email = urlParams.get('email');
document.getElementById('email').textContent = email;
const atIndex = email.indexOf('@');