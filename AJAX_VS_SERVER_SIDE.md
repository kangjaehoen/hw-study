# AJAX vs 서버 사이드 렌더링 비교

## 현재 상황 분석

### 현재 구현 방식: AJAX (클라이언트 사이드 렌더링)

**동작 흐름:**
1. 사용자가 `/board/main` 접속
2. 서버가 빈 HTML 페이지 반환 (데이터 없음)
3. 브라우저에서 JavaScript 실행
4. AJAX로 `/api/board/getBoardList` 호출
5. JSON 데이터 받아서 클라이언트에서 HTML 생성

**코드:**
```java
// BoardController.java
@GetMapping("/main")
public String boardMain() {
    return "board/main";  // 데이터 없이 빈 페이지 반환
}
```

```javascript
// main.html
$(function(){
    $.ajax({
        url: "/api/board/getBoardList",
        type: "GET",
        success: function(data){
            // 클라이언트에서 HTML 생성
            let html = '';
            for(let i = 0; i < list.length; i++){
                html += `<tr>...</tr>`;
            }
            $("#board-list-body").html(html);
        }
    });
});
```

---

## 두 방식 비교

### 1. AJAX 방식 (현재 사용 중)

#### ✅ 장점
1. **부분 업데이트 가능**: 페이지 전체를 새로고침하지 않고 데이터만 갱신
2. **사용자 경험 향상**: 로딩 중에도 다른 작업 가능
3. **서버 부하 감소**: 필요한 데이터만 요청
4. **동적 인터랙션**: 실시간 업데이트, 무한 스크롤 등 구현 용이
5. **프론트엔드/백엔드 분리**: API 재사용 가능 (모바일 앱, 다른 웹사이트 등)

#### ❌ 단점
1. **초기 로딩 느림**: 페이지 로드 → AJAX 요청 → 렌더링 (2단계)
2. **SEO 불리**: 검색 엔진이 JavaScript 실행 전 내용을 볼 수 없음
3. **JavaScript 필수**: JS 비활성화 시 동작 안 함
4. **복잡도 증가**: 클라이언트 코드가 복잡해짐
5. **에러 처리 복잡**: 네트워크 오류, 타임아웃 등 처리 필요

---

### 2. 서버 사이드 렌더링 방식 (Thymeleaf)

#### ✅ 장점
1. **초기 로딩 빠름**: 서버에서 완성된 HTML 반환 (1단계)
2. **SEO 최적화**: 검색 엔진이 완성된 HTML을 바로 읽을 수 있음
3. **JavaScript 불필요**: 기본 기능은 JS 없이도 동작
4. **간단한 구현**: 서버에서 데이터 처리 후 템플릿에 전달
5. **보안**: 서버에서 데이터 처리로 클라이언트 노출 최소화

#### ❌ 단점
1. **페이지 전체 새로고침**: 작은 변경에도 전체 페이지 리로드
2. **서버 부하**: 매 요청마다 서버에서 렌더링
3. **사용자 경험**: 전체 페이지 깜빡임
4. **동적 기능 제한**: 실시간 업데이트 등 구현 어려움

---

## 구현 예시

### 서버 사이드 렌더링 방식 구현

**BoardController 수정:**
```java
@GetMapping("/main")
public String boardMain(Model model) {
    logger.debug("Accessing board main page");
    // 서버에서 데이터를 가져와서 모델에 추가
    model.addAttribute("boardList", boardService.selectBoardList());
    return "board/main";
}
```

**main.html 수정:**
```html
<tbody id="board-list-body">
    <!-- Thymeleaf로 서버에서 전달받은 데이터 렌더링 -->
    <tr th:each="board : ${boardList}">
        <td th:text="${board.board_id}">번호</td>
        <td th:text="${board.title}">제목</td>
        <td th:text="${board.writer}">글쓴이</td>
        <td th:text="${board.created_at}">작성일</td>
    </tr>
</tbody>

<script>
    // 클릭 이벤트만 처리 (데이터는 서버에서 이미 렌더링됨)
    $(function(){
        $("#board-list-body tr").on("click", function () {
            let boardId = $(this).children("td").eq(0).text();
            location.href = "/board/detail?id=" + boardId;
        });
    });
</script>
```

---

## 권장사항

### 현재 프로젝트에 적합한 방식: **하이브리드 접근**

게시판의 특성상 **서버 사이드 렌더링**이 더 적합합니다:

1. **게시판은 정적 콘텐츠**: 실시간 업데이트가 자주 필요하지 않음
2. **SEO 중요**: 검색 엔진 최적화 필요
3. **초기 로딩 속도**: 사용자 경험 향상
4. **간단한 구현**: 코드 복잡도 감소

### 권장 구조

```
서버 사이드 렌더링 (기본)
├── 게시판 목록 (/board/main) → Thymeleaf로 렌더링
├── 게시글 상세 (/board/detail) → Thymeleaf로 렌더링
└── 게시글 작성/수정 → 폼 제출

AJAX (필요한 경우만)
├── 게시글 삭제 → AJAX (페이지 새로고침 없이)
├── 댓글 추가/삭제 → AJAX (실시간 업데이트)
└── 무한 스크롤 → AJAX (필요시)
```

---

## 결론

**현재 AJAX 방식의 문제점:**
- 게시판 목록은 정적 데이터인데 AJAX로 가져올 필요 없음
- 초기 로딩이 느림 (페이지 로드 → AJAX 요청 → 렌더링)
- SEO에 불리함

**권장 사항:**
- 게시판 목록: **서버 사이드 렌더링**으로 변경
- 게시글 삭제: **AJAX 유지** (사용자 경험 향상)

이렇게 하면 **장점을 모두 활용**할 수 있습니다!
