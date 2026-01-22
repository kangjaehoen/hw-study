package com.example.hw.board.controller;


import com.example.hw.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/main")
    public String boardMain(){
        return "board/main";
    }

    @GetMapping("/detail")
    public String boardDetail(@RequestParam int id, Model model){
        model.addAttribute("detail", boardService.selectBoardDetail(id));
        return "board/detail";
    }

}
