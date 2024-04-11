// 삭제버튼 클릭시 액션 폼 보내기
const form = document.querySelector("#actionForm");

document.querySelector("#delete").addEventListener("click", (e) => {
  if (!confirm("정말로 삭제하시겠습니까?")) return;

  form.action = "/book/delete";
  form.submit();
});
