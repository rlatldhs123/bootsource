const searchForm = document.querySelector("#searchForm");
searchForm.addEventListener("submit", (e) => {
  e.preventDefault();

  const type = document.querySelector("#type");
  const keyword = document.querySelector("#keyword");

  if (!type.value) {
    alert("타입 확인");
  } else if (!keyword.value) {
    alert("검색어를 입력해주세요");
  }
  searchForm.submit();
});
