// 화면의 중복 요소에 이벤트 작성
// document.querySelectorAll("[name='completed']").addEventListener("click"(e)=>{

// })

// 2번째 방법 이벤트 전파 = 부모 요소가 감지

document.querySelector(".list-group").addEventListener("click", (e) => {
  console.log("이벤트가 발생한 대상" + e.target);
  console.log("이벤트가 발생한 대상 value" + e.target.value);
  console.log("이벤트를 감지한 대상" + e.currentTarget);
});
