// /reviews/3/all 요청 처리
// 날짜 포맷 변경 함수
const formatDate = (data) => {
  const date = new Date(data);

  return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
};

const reviewsLoaded = () => {
  fetch(`/reviews/${mno}/all`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      // 리뷰 총 개수 변경
      document.querySelector(".review-cnt").innerHTML = data.length;
      if (data.length > 0) reviewList.classList.remove("hidden");

      let result = "";
      data.forEach((review) => {
        result += `<div class="d-flex justify-content-between my-2 border-bottom py-2 review-row" data-rno="${review.reviewNo}"> 
          <div class="flex-grow-1 align-self-center">
            <div><span class="font-semibold">${review.text}</span></div>
            <div class="small text-muted">
              <span class="d-inline-block mr-3">${review.nickname}</span>
              평점 : <span class="grade">${review.grade}</span>
            </div>
            <div class="text-muted"><span class="small">${formatDate(review.createdDate)}</span></div>
          </div>
          <div class="d-flex flex-column align-self-center">
            <div class="mb-2">
              <button class="btn btn-outline-danger btn-sm">삭제</button>
            </div>
            <div>
              <button class="btn btn-outline-success btn-sm">수정</button>
            </div>
          </div>
        </div>`;
      });
      reviewList.innerHTML = result;
    });
};

reviewsLoaded();

// 리뷰 등록 or 수정
// 리뷰 폼 submit 중지
const reviewForm = document.querySelector(".review-form");
reviewForm.addEventListener("submit", (e) => {
  e.preventDefault();
  // text, grade, mid, mno
  const text = reviewForm.querySelector("#text");
  const mid = reviewForm.querySelector("#mid");
  const nickname = reviewForm.querySelector("#nickname");
  // 수정이라면 reviewNo 가 존재함
  const reviewNo = reviewForm.querySelector("#reviewNo");

  const body = {
    mno: mno,
    text: text.value,
    grade: grade || 0,
    mid: mid.value,
    reviewNo: reviewNo.value,
  };

  if (!reviewNo.value) {
    // 새 review 등록
    fetch(`/reviews/${mno}`, {
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify(body),
      method: "post",
    })
      .then((response) => response.text())
      .then((data) => {
        console.log(data);

        text.value = "";
        nickname.value = "";
        // grade = 0;
        reviewForm.querySelector(".starrr a:nth-child(" + grade + ")").click();

        if (data) alert(data + " 번 리뷰가 등록되었습니다.");
        reviewsLoaded(); // 댓글 리스트 다시 가져오기
      });
  } else {
    // 수정
    fetch(`/reviews/${mno}/${reviewNo.value}`, {
      headers: {
        "content-type": "application/json",
      },
      body: JSON.stringify(body),
      method: "put",
    })
      .then((response) => response.text())
      .then((data) => {
        console.log(data);

        text.value = "";
        nickname.value = "";
        reviewNo.value = "";
        reviewForm.querySelector(".starrr a:nth-child(" + grade + ")").click();

        if (data) alert(data + " 번 리뷰가 수정되었습니다.");
        reviewsLoaded(); // 댓글 리스트 다시 가져오기
      });
  }
});

// 삭제 클릭 시 reviewNo 가져오기
// fetch() 작성
reviewList.addEventListener("click", (e) => {
  // 부모요소가 이벤트를 감지하는 형태로 작성 => 실제 이벤트 대상 요소가 무엇인지 찾아야 함
  console.log("이벤트 대상 ", e.target);

  const target = e.target;
  // 리뷰 댓글 번호 가져오기
  const reviewNo = target.closest(".review-row").dataset.rno;

  if (target.classList.contains("btn-outline-danger")) {
    if (!confirm("리뷰를 정말로 삭제하시겠습니까?")) return;

    fetch(`/reviews/${mno}/${reviewNo}`, {
      method: "delete",
    })
      .then((response) => response.text())
      .then((data) => {
        alert(data + " 번 리뷰가 삭제되었습니다.");
        reviewsLoaded();
      });
  } else if (target.classList.contains("btn-outline-success")) {
    // 도착한 데이터 review폼에 보여주기
    fetch(`/reviews/${mno}/${reviewNo}`)
      .then((response) => response.json())
      .then((data) => {
        reviewForm.querySelector("#reviewNo").value = data.reviewNo;
        reviewForm.querySelector("#mid").value = data.mid;
        reviewForm.querySelector("#nickname").value = data.nickname;
        reviewForm.querySelector("#text").value = data.text;
        // 이벤트 click 을 직접 호출
        reviewForm.querySelector(".starrr a:nth-child(" + data.grade + ")").click();
        reviewForm.querySelector("button").innerHTML = "리뷰 수정";
      });
  }
});
