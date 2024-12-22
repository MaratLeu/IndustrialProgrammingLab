document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const xhr = new XMLHttpRequest();
    xhr.open('POST', '/add');
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 300) {
            window.location.href = 'authorization.html';
        } else {
            const errorData = JSON.parse(xhr.responseText);
            document.getElementById('loginError').textContent = `Login failed: ${errorData.message || 'Please check your credentials.'}`;
        }
    };

    xhr.onerror = function() {
        document.getElementById('loginError').textContent = 'Login failed: Network error.';
    };

    const payload = JSON.stringify({ first: username, last: password });
    xhr.send(payload);
});

function togglePasswordVisibility() {
    const passwordInput = document.getElementById('password');
    const body = document.body;
    const eye = document.querySelector('.eye');

    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        body.classList.add('dark-mode');
        eye.classList.add('active');
        passwordInput.classList.add('highlighted');
    } else {
        passwordInput.type = 'password';
        body.classList.remove('dark-mode');
        eye.classList.remove('active');
        passwordInput.classList.remove('highlighted');
    }
}