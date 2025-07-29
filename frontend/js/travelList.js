import { getToken, logoutIfInvalid } from './token.js';

const baseURL = 'http://localhost:8080/api/v1';

if (!getToken()) {
    window.location.href = 'login.html';
}

function formatDate(dateTimeString) {
    const date = new Date(dateTimeString);
    return date.toLocaleDateString('ko-KR', {
        year: 'numeric', month: '2-digit', day: '2-digit',
        hour: '2-digit', minute: '2-digit'
    });
}

let settingTravelId = null;

export async function loadTravels() {
    const res = await fetch(`${baseURL}/travel`, {
        headers: { Authorization: `Bearer ${getToken()}` }
    });

    if (!res.ok) return logoutIfInvalid();

    const travels = await res.json();
    const list = document.getElementById('travelList');
    list.innerHTML = '';

    travels.forEach(travel => {
        const item = document.createElement('li');
        item.className = 'list-group-item d-flex justify-content-between align-items-center';

        item.innerHTML = `
            <div>
                <h5 class="mb-1">${travel.title}</h5>
                <p class="mb-1">${travel.location} | ${travel.description || ''}</p>
                <small>${formatDate(travel.startDate)} ~ ${formatDate(travel.endDate)}</small>
            </div>
            <div>
                <button class="btn btn-sm btn-secondary me-2 setting-btn" data-id="${travel.id}">⚙️ 설정</button>
                <button class="btn btn-outline-primary btn-sm view-btn" data-id="${travel.id}">📷 보기</button>
            </div>
        `;

        item.querySelector('.setting-btn').addEventListener('click', (e) => {
            settingTravel(e.target.dataset.id);
        });

        item.querySelector('.view-btn').addEventListener('click', (e) => {
            window.location.href = `travel.html?travelId=${e.target.dataset.id}`;
        });

        list.appendChild(item);
    });
}

export async function handleCreateTravel(e) {
    e.preventDefault();
    const form = e.target;

    const body = {
        title: form.title.value,
        description: form.description.value,
        location: form.location.value,
        startDate: form.startDate.value,
        endDate: form.endDate.value,
        latitude: form.latitude.value,
        longitude: form.longitude.value
    };

    const res = await fetch(`${baseURL}/travel`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${getToken()}`
        },
        body: JSON.stringify(body)
    });

    if (!res.ok) return alert('여행 생성 실패');

    alert('여행이 추가되었습니다!');
    form.reset();
    loadTravels();
}

// 설정 모달 열기
window.settingTravel = function (travelId) {
    settingTravelId = travelId;
    document.getElementById('settingModal').style.display = 'flex';
};

// 설정 모달 닫기
window.closeTravelModal = function () {
    settingTravelId = null;
    document.getElementById('settingModal').style.display = 'none';
    document.getElementById('settingTravelForm')?.reset();
};

// 대표 사진 지정
window.selectThumbnail = async function () {
    if (!settingTravelId) return alert('여행이 선택되지 않았습니다.');

    const res = await fetch(`${baseURL}/travel/${settingTravelId}/thumbnail`, {
        method: 'PATCH',
        headers: { Authorization: `Bearer ${getToken()}` }
    });

    if (!res.ok) return alert('대표 사진 지정 실패');

    alert('대표 사진이 지정되었습니다!');
    closeTravelModal();
    loadTravels();
};

// 수정 모달 열기
window.openEditForm = function () {
    document.getElementById('editModal').style.display = 'flex';
};

// 수정 모달 닫기
window.closeEditModal = function () {
    document.getElementById('editModal').style.display = 'none';
};

// 삭제 확인 모달 열기
window.openDeleteConfirm = function () {
    document.getElementById('deleteConfirmModal').style.display = 'flex';
};

// 삭제 확인 모달 닫기
window.closeDeleteModal = function () {
    document.getElementById('deleteConfirmModal').style.display = 'none';
};

// 삭제 수행
window.confirmDeleteTravel = async function () {
    if (!settingTravelId) return alert('선택된 여행이 없습니다.');

    const res = await fetch(`${baseURL}/travel/${settingTravelId}`, {
        method: 'DELETE',
        headers: { Authorization: `Bearer ${getToken()}` }
    });

    if (!res.ok) return alert('삭제 실패');

    alert('삭제 성공');
    closeDeleteModal();
    closeTravelModal();
    loadTravels();
};

// 로그아웃
window.logout = function () {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    window.location.href = 'login.html';
};

// 초기 실행

document.getElementById('createTravelForm')?.addEventListener('submit', handleCreateTravel);
document.getElementById('selectThumbnailBtn')?.addEventListener('click', window.selectThumbnail);
document.getElementById('editTravelBtn')?.addEventListener('click', window.openEditForm);
document.getElementById('closeModalBtn')?.addEventListener('click', window.closeTravelModal);
document.getElementById('closeEditBtn')?.addEventListener('click', window.closeEditModal);
document.getElementById('deleteTravelBtn')?.addEventListener('click', window.openDeleteConfirm);
document.getElementById('cancelDeleteBtn')?.addEventListener('click', window.closeDeleteModal);
document.getElementById('confirmDeleteBtn')?.addEventListener('click', window.confirmDeleteTravel);

loadTravels();