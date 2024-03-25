function openMenu() {
    let modal = document.getElementById('mobile-menu');
    modal.style.display = 'block';
}

function closeMenu() {
    let modal = document.getElementById('mobile-menu');
    modal.style.display = 'none';
}

// 모달 닫기 함수
window.onclick = function(event) {
    let menu = document.getElementById('mobile-menu');
    let modal = document.getElementById('Modal');
    if (event.target == modal && event.target == menu) {
        modal.style.display = 'none';
        menu.style.display = 'none';
    }
}