const searchForm = document.querySelector("#searchForm");

searchForm.addEventListener("submit", (e) => {
  e.preventDefault();

  const type = document.querySelector("#type");

  const keyword = document.querySelector("#keyword");

  if (type.value == "") {
    alert("타입을 입력해주세요");
    type.focus();
    return;
  }

  if (keyword.value == "") {
    alert("keyword를 입력해주세요");
    keyword.focus();
    return;
  }
  e.target.submit();
});
