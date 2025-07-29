const baseURL = 'http://localhost:8080/api/v1';

document.getElementById('signupForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    // 📌 form 입력값 가져오기
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const nickname = document.getElementById('nickname').value;

    // 📡 fetch 요청 보내기
    const res = await fetch(`${baseURL}/user/signup`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, password, nickname })
    });

    if (!res.ok) {
        const err = await res.text();
        return alert(`회원가입 실패: ${err}`);
    }

    alert('회원가입이 완료되었습니다!');
    window.location.href = 'login.html';
});