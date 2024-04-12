// package com.example.jpa.repository;

// import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;

// import com.example.jpa.entity.Board;
// import java.util.List;

// public interface BoardRepository extends JpaRepository<Board, Long> {

// @Query(value = "SELECT * FROM Board", nativeQuery = true)
// List<Board> findList();
// // 기본적인 제공은 id 로 찾기 또는 전체 찾기를 지원 한다

// // title 로 찾기
// @Query("SELECT b FROM Board b WHERE b.title=?1 LIKE ?1%")
// List<Board> findByTitle(String title);

// // 작성자로 찾기
// @Query("SELECT b FROM Board b WHERE b.writer=?1 LIKE %:writer%")
// List<Board> findByWriter(String writer);

// // like 포함된 것들을 가져온다면
// List<Board> findByTitleLike(String title);

// // 시작하는
// List<Board> findByTitleStartingWith(String title);

// // 끝나는
// List<Board> findByTitleEndingWith(String title);

// // 포함된
// List<Board> findByTitleContaining(String title);

// // wirter 가 user 로 시작하는 작성자 찾기
// List<Board> findByWriterStartingWith(String writer);

// // title 이 Title 이라는 문자열이 포함되어 있거나
// // content 가 Content 문자열이 포함되어 있는 데이터 조회

// // and or 중간에 끼면 됌

// @Query("SELECT b FROM Board b WHERE b.title LIKE %:title% or b.content
// =:content")
// List<Board> findByTitleContainingOrContent(String title, String content);

// List<Board> findByTitleContainingOrContentContaining(String title, String
// content);

// // title 이 Title 문자열이 포함되어 있고 id 가 50 보다 큰 게시물 조회
// @Query("SELECT b FROM Board b WHERE b.title LIKE %:?1% and b.id > ?2")
// List<Board> findByTitleContainingAndIdGreaterThan(String title, Long id);

// // id 가 50보다 큰 게시물 조회시 내림차순 정렬
// List<Board> findByIdGreaterThanOrderByIdDesc(Long id);

// // 페이지 나누기 개념 들어간 것
// List<Board> findByIdGreaterThanOrderByIdDesc(Long id, Pageable pageable);

// }
