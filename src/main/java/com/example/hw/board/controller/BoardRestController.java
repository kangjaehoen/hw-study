package com.example.hw.board.controller;

import com.example.hw.board.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/board")
public class BoardRestController {

    private final BoardService boardService;

    public BoardRestController(BoardService boardService) {
        this.boardService = boardService;
    }


    @GetMapping("/getBoardList")
    public ResponseEntity<Map<String, Object>> getBoardList(){
        Map<String,Object> result = new HashMap<>();
        result.put("list", boardService.selectBoardList());
        return ResponseEntity.ok(result);
    }
}
