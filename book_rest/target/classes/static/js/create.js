// form submit 시  submit 기능 중지

// form 내용 가져오기 => javascriprt 객체 생성

document.querySelector("#createForm").addEventListener("submit", (e) => {
  e.preventDefault();

  const data = {
    categoryName: document.querySelector("#categoryName").value,
    title: document.querySelector("#title").value,
    publisherName: document.querySelector("#publisherName").value,
    writer: document.querySelector("#writer").value,
    price: document.querySelector("#price").value,
    salePrice: document.querySelector("#salePrice").value,
  };

  console.log(data);

  // method 지정 안하면 get 으로 전송됨

  fetch(`http://localhost:8080/book/new`, {
    method: "post",
    headers: {
      // 내가 보내는 데이터를 서버쪽에 알려 주는 것
      "content-type": "application/json",
    },

    // 자바스크립트 객체를 data 형식으로 바꿔줌
    body: JSON.stringify(data), // JSON.stringify() : java 스크립트 객체를  => json  타입으로 변환
  })
    .then((response) => response.text())
    .then((data) => {
      console.log(data);

      if (data === "success") {
        alert("입력성공");
        location.href = "/book/list?page=1&type=&keyword=";
      }
    });
});

//////////////////////////////////////////////////////////////////

// 클릭 이벤트 리스너 등록
// (1) tbody 요소의 클릭 이벤트를 감지하여 실행
document.querySelector("tbody").addEventListener("click", (e) => {
  // 이벤트 기본 동작 중지
  // (2) 기본 동작인 링크 이동 등을 중지
  e.preventDefault();
  const target = e.target;

  // 클릭된 요소의 데이터 ID 가져오기
  // (3) 클릭된 요소의 데이터 ID를 가져옴
  console.log(target.dataset.id);

  // 서버로 GET 요청하여 데이터 가져오기
  // (4) 클라이언트가 서버로 데이터를 요청함
  fetch(`http://localhost:8080/read/${target.dataset.id}`)
    .then((response) => response.json())
    .then((data) => {
      // 가져온 데이터 출력
      // (5) 서버가 전송한 데이터를 출력
      console.log(data);

      // 가져온 데이터로 디자인 영역 업데이트
      // (6) 가져온 데이터를 사용하여 디자인 영역 업데이트
      document.querySelector("#category").value = data.categoryName;
      document.querySelector("#title").value = data.title;
      document.querySelector("#publisher").value = data.publisherName;
      document.querySelector("#writer").value = data.writer;
      document.querySelector("#price").value = data.price;
      document.querySelector("#salePrice").value = data.salePrice;
      document.querySelector("#salePrice").value = data.salePrice;
      document.querySelector("#book_id").value = data.id;
    });
});
