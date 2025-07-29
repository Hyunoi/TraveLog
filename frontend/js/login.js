const baseURL = 'http://localhost:8080/api/v1';

document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const res = await fetch(`${baseURL}/user/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password })
    });

    if (!res.ok) {
        alert('로그인 실패! 이메일 또는 비밀번호를 확인하세요.');
        return;
    }

    const data = await res.json();
    console.log('로그인 응답:', data);
    localStorage.setItem('accessToken', data.accessToken);
    localStorage.setItem('refreshToken', data.refreshToken);

    alert('로그인 성공!');
    console.log('페이지 이동 시도');
    window.location.href = 'travelList.html'; // ✅ 상대경로 사용 가능
});