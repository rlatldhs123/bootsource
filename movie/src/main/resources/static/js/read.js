// 작은 포스터 클릭하면 큰 포스터 보여주기
const imgModal = document.getElementById("imgModal");

if (imgModal) {
  imgModal.addEventListener("show.bs.modal", (e) => {
    // 모달을 뜨게 만든 li 가져오기
    const posterLi = e.relatedTarget;

    // data- : dataset
    const file = posterLi.getAttribute("data-file");
    console.log("file", file);

    // 타이틀 영역 영화명 삽입
    imgModal.querySelector(".modal-title").textContent = `${title}`;

    // 이미지 경로 변경
    const modalBody = imgModal.querySelector(".modal-body");
    modalBody.innerHTML = `<img src="/upload/display?fileName=${file}" style="width:100%">`;
  });
}
