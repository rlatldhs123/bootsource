const actionForm = document.querySelector("#actionForm");

document.querySelector("#delete").addEventListener("click", () => {
  if (!confirm("삭제 하시겠습니까")) {
    return;
  }
  actionForm.submit();
});
