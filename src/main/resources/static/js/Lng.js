window.onload = function() {
    // "US" 옵션을 선택하도록 설정
    const selectElement = document.querySelector('select.LNG');
    const selectElement2 = document.querySelector('select.LNG2');
    selectElement.value = "/US"; // 값을 "/US"로 설정
    selectElement2.value = "/US"; // 값을 "/US"로 설정
    // 즉시 onchange 이벤트를 트리거하여 페이지 이동
};