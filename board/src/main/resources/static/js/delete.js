document.querySelector("#delete").addEventListener("click", () => {
  const form = document.querySelector("#actionForm");
  if (!confirm("정말로 삭제하시겠습니까?")) return;

  form.action = "/board/delete";

  form.submit();
});
