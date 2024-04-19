// 페이지 로드시 무조건 실행
// 즉시실행 함수
// fetch() - 함수 작성후 호출

// 함수 작성

// 1.function method1(){}
// 2. const (or let)method1 = ()=>{}

// 함수실행 => 호출
// method1();

// 호이스팅(선언 안하고 먼저 호출 후 선언)

// 함수작성 방법 1번은 호이스팅이 되지만 2번은 호이스팅 불가

// var 로 선언된 변수는 호이스팅이 됨

// const,let 은 호이스팅 안됨

// 날짜 포멧 변경 함수

const formatDate = (data) => {
  const date = new Date(data);

  return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
};

// 댓글 목록 가져오기
const replyList = document.querySelector(".replyList");
const replyLoaded = () => {
  // 댓글목룍 보여줄 영역 가져오기

  //

  //
  fetch(`/replies/board/${bno}`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      // 댓글 총 수 확인

      document.querySelector("#replyCount").innerHTML = data.length;
      let result = "";
      data.forEach((reply) => {
        result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno="${reply.rno}" id="Rerno">`;
        result += `<div class="p-3"><img src="/img/default.png" alt="" class="rounded-circle mx-auto d-block" style="width: 60px; height: 60px" /></div>`;
        result += `<div class="flex-grow-1 align-self-center"><div>${reply.replyer}</div>`;
        result += `<div><span class="fs-5">${reply.text}</span></div>`;
        result += `<div class="text-muted"><span class="small">${formatDate(reply.createdDate)}</span></div></div>`;
        result += `<div class="d-flex flex-column align-self-center">`;
        result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
        result += `</div></div>`;
      });
      // 영역에 result 보여주기
      replyList.innerHTML = result;
    });
};

replyLoaded();

// 새 댓글 등록

// 새 댓글 등록 폼 submit 시

// 기능 중지 작성자/ 댓글 가져 오기

const replyForm = document.querySelector("#replyForm");

replyForm.addEventListener("submit", (e) => {
  e.preventDefault();

  const replyer = replyForm.querySelector("#replyer");
  const text = replyForm.querySelector("#reply");

  const reply = {
    replyer: replyer.value,
    text: text.value,
    bno: bno,
  };

  fetch(`/replies/new`, {
    method: "post",
    headers: {
      // 내가 보내는 데이터를 서버쪽에 알려줘야 함
      "content-type": "application/json",
    },
    body: JSON.stringify(reply), // JSON.stringify() : java 스크립트 객체를  => json  타입으로 변환
  })
    .then((resopnse) => resopnse.text())
    .then((data) => {
      console.log(data);
      if (data) {
        alert(data + "번 댓글 등록");

        // 댓글 다달고 나서 폼 인풋부분 초기화
        replyer.value = "";
        text.value = "";

        replyLoaded();
      }
    });
});

// 댓글에서 삭제 버튼을 클릭시

// 댓글에 RNO 가져오기
// 댓글 삭제 및 수정 버튼 클릭시 이벤트 전파로 찾아오기

// 이벤트 전파 : 자식 요소에 일어난 이벤트는 상위 요소로 전달 됨
replyList.addEventListener("click", (e) => {
  // 실제 이벤트가 일어난 대상은 누구인가?
  // e.target 을 쓰면 이벤트가 일어난 대상을 알 수 있다

  const btn = e.target;

  //closest("요소") : 위에 요소중에서 괄호안 일치하는 젤이 가까운 상위요소를 찾아준다
  // data-rno 값을 가져오는건 dataset.rno; 이런식으로

  const rno = btn.closest(".reply-row").dataset.rno;
  console.log("rno : ", rno);

  fetch(`/replies/${rno}`, {
    method: "delete",
  })
    .then((resopnse) => resopnse.text())
    .then((data) => {
      if (data == "success") {
        alert("댓글 삭제 성공");
        replyLoaded();
      }
    });
});

// document.querySelector("#delete").addEventListener("click", () => {
//   const rno = document.querySelector("#Rerno").getAttribute("data-rno");

//   console.log(rno);
// });
