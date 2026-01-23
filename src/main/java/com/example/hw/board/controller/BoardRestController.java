package com.example.hw.board.controller;

import com.example.hw.board.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/board")
public class BoardRestController {

    private static final Logger logger = LoggerFactory.getLogger(BoardRestController.class);
    private final BoardService boardService;

    public BoardRestController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/getBoardList")
    public ResponseEntity<Map<String, Object>> getBoardList() {
        logger.debug("REST API: Fetching board list");
        try {
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("list", boardService.selectBoardList());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Error in getBoardList API", e);
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("success", false);
            errorResult.put("message", "게시글 목록을 조회하는 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
        }
    }

    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<Map<String, Object>> deleteBoard(@PathVariable Long boardId) {
        logger.info("REST API: Deleting board: id={}", boardId);
        try {
            int result = boardService.deleteBoard(boardId);
            Map<String, Object> response = new HashMap<>();
            
            if (result > 0) {
                response.put("success", true);
                response.put("message", "게시글이 삭제되었습니다.");
                logger.info("Board deleted successfully: id={}", boardId);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "게시글 삭제에 실패했습니다.");
                logger.warn("Failed to delete board: id={}", boardId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            logger.error("Error deleting board: id={}", boardId, e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "게시글 삭제 중 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
