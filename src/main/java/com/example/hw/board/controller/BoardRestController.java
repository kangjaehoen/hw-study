package com.example.hw.board.controller;

import com.example.hw.board.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long boardId){
        String result ="";
        int num = boardService.deleteBoard(boardId);
            if(num >0){
                result = "success";
            }else{
                result = "fail";
            }
        return ResponseEntity.ok(result);
    }
}
