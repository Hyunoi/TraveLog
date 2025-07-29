// token.js
export function getToken() {
    return localStorage.getItem('accessToken');
}

export function logoutIfInvalid() {
    alert('로그인이 만료되었거나 유효하지 않습니다. 다시 로그인해주세요.');
    localStorage.clear();
    window.location.href = 'login.html';
}