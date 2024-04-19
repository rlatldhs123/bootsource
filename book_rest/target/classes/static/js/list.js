// 제목을 클릭 시 a 태그 기능 중지
// data - id 에 있는 갑 ㅅ가져오기

document.querySelector("tbody").addEventListener("click", (e) => {
  e.preventDefault();
  const target = e.target;

  console.log(target.dataset.id);

  fetch(`http://localhost:8080/read/${target.dataset.id}`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      // 디자인 영역 가져오기

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

// 삭제 클릭시 아이디 가져오기

// 삭제

document.querySelector(".btn-primary").addEventListener("click", (e) => {
  e.preventDefault();

  console.log("삭제 클릭시 아이디 출력" + document.querySelector("#book_id").value);

  const id = document.querySelector("#book_id").value;

  // /delete/{id} + Delete

  // get 방식이 아닌 이상 무조건 메소드가 패치 함수에 들어가야 한다

  fetch(`/delete/${id}`, {
    method: "delete",
  })
    .then((response) => response.text())
    .then((data) => {
      if ((data = "success")) {
        alert("삭제 성공");
        location.href = "/book/list?page=1&type=&keyword=";
      }
    });
});

// 수정 버튼 클릭시

document.querySelector(".btn-secondary").addEventListener("click", (e) => {
  e.preventDefault();

  // const myForm = document.querySelector("myForm");
  // myForm 안에 들어있는 요소 찾기
  // myForm.querySelector("#book.id")

  const book_id = document.querySelector("#book_id").value;
  const data = {
    id: book_id,
    price: document.querySelector("#price").value,
    salePrice: document.querySelector("#salePrice").value,
  };

  // method 지정 안하면 get 으로 전송됨

  fetch(`/modify/${book_id}`, {
    method: "put",
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
        alert("수정 성공");
        location.href = "/book/list?page=1&type=&keyword=";
      }
    });
});
