
// JavaScript code to display email and timestamp
const urlParams = new URLSearchParams(window.location.search);
const email = urlParams.get('email');
const timestamp = urlParams.get('timestamp');
document.getElementById('email').textContent = email;
document.getElementById('timestamp').textContent = timestamp;
