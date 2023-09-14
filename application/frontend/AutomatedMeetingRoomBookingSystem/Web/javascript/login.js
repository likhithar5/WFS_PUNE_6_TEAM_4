const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');
const userIDInput = document.getElementById('userID');
const userIDError = document.getElementById('userIDError');
const emailInput = document.getElementById('email');
const emailError = document.getElementById('emailError');

signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});

function redirectToPage(emailValue) {
    const domain = emailValue.split('@')[1];
    const timestamp = new Date().toLocaleString();
    const encodedEmail = encodeURIComponent(emailValue);
    const encodedTimestamp = encodeURIComponent(timestamp);

    switch (domain) {
        case 'member.com':
            window.location.href = `member.html?email=${encodedEmail}&timestamp=${encodedTimestamp}`;
            break;
        case 'admin.com':
            window.location.href = `admin.html?email=${encodedEmail}&timestamp=${encodedTimestamp}`;
            break;
        case 'manager.com':
            window.location.href = `manager.html?email=${encodedEmail}&timestamp=${encodedTimestamp}`;
            break;
        default:
            // Handle other cases or display an error message
            alert("Invalid email domain");
    }
}

function validateForm() {
    const userIDValue = userIDInput.value.trim();
    const emailValue = emailInput.value.trim();
    const userIDRegex = /^\d{8}$/;
    const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

    let isValid = true;

    if (!userIDRegex.test(userIDValue)) {
        userIDError.textContent = "Please enter a valid 8-digit User ID.";
        isValid = false;
    } else {
        userIDError.textContent = "";
    }

    if (!emailRegex.test(emailValue)) {
        emailError.textContent = "Please enter a valid email address.";
        isValid = false;
    } else {
        emailError.textContent = "";
    }

    if (isValid) {
        redirectToPage(emailValue);
    }

    return false;
}