// 📁 js/photo.js
import { getToken, logoutIfInvalid } from './token.js';

const baseURL = 'http://localhost:8080/api/v1';
const travelId = new URLSearchParams(window.location.search).get('travelId');

// 🔐 토큰 또는 travelId 없으면 로그인 페이지로 이동
if (!travelId || !getToken()) {
    window.location.href = 'login.html';
}

// ✅ 사진 목록 불러오기
export async function loadPhotos() {
    const res = await fetch(`${baseURL}/photo/${travelId}`, {
        headers: { 'Authorization': `Bearer ${getToken()}` }
    });

    if (!res.ok) return logoutIfInvalid();
    const data = await res.json();

    const list = document.getElementById('photoList');
    list.innerHTML = '';
    data.forEach(photo => {
        const item = document.createElement('li');
        item.className = 'list-group-item';

        item.innerHTML = `
          <div class="d-flex align-items-center justify-content-between">
            <div class="d-flex align-items-center">
              <img src="${photo.photoUrl}" width="120" class="me-3" alt="여행 사진">
              <div>
                <strong>${photo.comment}</strong><br>
                <small>@ ${photo.location || '위치 정보 없음'}</small>
              </div>
            </div>
            <div>
              <button class="btn btn-sm btn-secondary me-2" onclick="editPhoto(${photo.photoId})">✏️ 수정</button>
              <button class="btn btn-sm btn-danger" onclick="deletePhoto(${photo.photoId})">🗑 삭제</button>
            </div>
          </div>
        `;

        list.appendChild(item);
    });
}

// ✅ 사진 업로드 핸들러
export async function handleUploadPhoto(e) {
    e.preventDefault();
    const form = e.target;

    const formData = new FormData();
    formData.append('image', form.image.files[0]);
    formData.append('comment', form.comment.value);
    formData.append('location', form.location.value);

    const res = await fetch(`${baseURL}/photo/${travelId}`, {
        method: 'POST',
        headers: { 'Authorization': `Bearer ${getToken()}` },
        body: formData,
    });

    if (!res.ok) return alert('업로드 실패');

    alert('사진이 업로드되었습니다');
    form.reset();
    loadPhotos();
}

// ✅ 삭제 핸들러
window.deletePhoto = async function(photoId) {
    if (!confirm('정말 삭제하시겠습니까?')) return;

    const res = await fetch(`${baseURL}/photo/${photoId}`, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${getToken()}`
        }
    });

    if (!res.ok) return alert('삭제 실패');
    alert('삭제되었습니다');
    loadPhotos();
};

// ✅ 수정 모달 열기
let editingPhotoId = null;

window.editPhoto = function (photoId) {
    editingPhotoId = photoId;
    document.getElementById('editModal').style.display = 'flex';
};

// ✅ 수정 모달 닫기
window.closeEditModal = function () {
    editingPhotoId = null;
    document.getElementById('editModal').style.display = 'none';
    document.getElementById('editPhotoForm').reset();
};

// ✅ 사진 수정 요청
document.getElementById('editPhotoForm').addEventListener('submit', async function (e) {
    e.preventDefault();
    if (!editingPhotoId) return;

    const form = e.target;
    const formData = new FormData();

    // image는 선택사항이므로 있을 때만 추가
    if (form.image.files.length > 0) {
        formData.append('image', form.image.files[0]);
    }

    // DTO 형태로 JSON 만들기
    const json = JSON.stringify({
        comment: form.comment.value,
        location: form.location.value
    });
    formData.append('request', new Blob([json], { type: 'application/json' }));

    const res = await fetch(`${baseURL}/photo/${editingPhotoId}`, {
        method: 'PATCH',
        headers: { 'Authorization': `Bearer ${getToken()}` },
        body: formData
    });

    if (!res.ok) return alert('수정 실패');

    alert('수정되었습니다');
    closeEditModal();
    loadPhotos();
});

// ✅ 돌아가기 버튼
window.goBack = function () {
    window.location.href = 'travelList.html';
};

// ✅ 초기 실행
document.getElementById('uploadPhotoForm').addEventListener('submit', handleUploadPhoto);
loadPhotos();