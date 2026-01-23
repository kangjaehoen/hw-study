package com.example.hw.board.controller;


import com.example.hw.board.dto.BoardDTO;
import com.example.hw.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping("/write")
    public String boardWritePage(){
        return "board/write";
    }

    @PostMapping("/insert")
    public String boardInsert(BoardDTO dto){
        int num = boardService.insertBoard(dto);
        if(num>0){
            System.out.println("insert success");
        }
        return "redirect:/board/main";
    }

    @GetMapping("/edit")
    public String boardEditePage(@RequestParam int id, Model model){
        model.addAttribute("detail", boardService.selectBoardDetail(id));
        return "board/edit";
    }

    @PostMapping("/update")
    public String boardUpdate(BoardDTO dto) throws Exception {
        int num = boardService.updateBoard(dto);
            if(num <= 0){
                throw new Exception("update error");
            }
        return "redirect:/board/detail?id="+dto.getBoardId();
    }
}
