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
      "content-type": "application/json",
    },
    body: JSON.stringify(data), // JSON.stringify() : java 스크립트 객체를  => json  타입으로 변환
  })
    .then((response) => response.text())
    .then((data) => {
      if (data == "success") alert("입력성공");
      location.href = "/book/list?page=1&type=&keyword=";
    });
  //   fetch(`http://localhost:8080/create`)
  //     .then((response) => response.json())
  //     .then((data) => {
  //       console.log(data);
  //     });
});
