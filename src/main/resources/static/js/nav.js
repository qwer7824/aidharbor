function openMenu() {
    let modal = document.getElementById('mobile-menu');
    modal.style.display = 'block';
    console.log("open");

    // 클릭 이벤트 리스너 비활성화
    window.removeEventListener('click', handleClick);

    // 모달이 열린 후 1초 후에 클릭 이벤트 리스너를 다시 활성화
    setTimeout(() => {
        window.addEventListener('click', handleClick);
    }, 1000);
}

function handleClick(event) {
    let modal = document.getElementById('mobile-menu');
    // 모달 배경을 클릭했을 때만 닫히도록 조건 추가
    if (!modal.contains(event.target)) {
        closeMenu(); // 모달 닫기
    }
}

function closeMenu() {
    let modal = document.getElementById('mobile-menu');
    modal.style.display = 'none';
    console.log("close");
}

// 이벤트 리스너 초기 설정
window.addEventListener('click', handleClick);

document.addEventListener('DOMContentLoaded', function() {
    const menuTriggers = document.querySelectorAll('.menu-top, .down-btn, .up-btn');

    menuTriggers.forEach(trigger => {
        trigger.addEventListener('click', function(event) {
            event.preventDefault(); // 링크 클릭 방지
            event.stopPropagation(); // 이벤트 전파 중단


            // 클릭한 메뉴의 부모 요소를 찾아 서브메뉴 선택
            const submenu = trigger.closest('.menu-tap').nextElementSibling;
            const downBtn = trigger.closest('.menu-tap').querySelector('.down-btn');
            const upBtn = trigger.closest('.menu-tap').querySelector('.up-btn');

            // 모든 서브메뉴 숨기기
            const allSubmenus = document.querySelectorAll('.submenu');
            allSubmenus.forEach(item => {
                if (item !== submenu) {
                    item.style.display = 'none';
                    const itemParent = item.previousElementSibling;
                    const itemDownBtn = itemParent.querySelector('.down-btn');
                    const itemUpBtn = itemParent.querySelector('.up-btn');
                    itemDownBtn.style.display = 'block';
                    itemUpBtn.style.display = 'none';
                }
            });

            // 클릭한 메뉴가 menu-top일 경우 서브메뉴 토글
            if (trigger.classList.contains('menu-top')) {
                const computedStyle = window.getComputedStyle(submenu);
                if (computedStyle.display === 'none') {
                    submenu.style.display = 'block';
                    downBtn.style.display = 'none';
                    upBtn.style.display = 'block';
                } else {
                    submenu.style.display = 'none';
                    downBtn.style.display = 'block';
                    upBtn.style.display = 'none';
                }
            } else {
                // down-btn 또는 up-btn 클릭 시 서브메뉴 열기
                submenu.style.display = 'block';
                downBtn.style.display = 'none';
                upBtn.style.display = 'block';
            }
        });
    });
});