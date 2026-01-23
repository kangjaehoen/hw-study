package com.example.hw.board.controller;

import com.example.hw.board.dto.BoardDTO;
import com.example.hw.board.service.BoardService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
public class BoardController {

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/main")
    public String boardMain() {
        logger.debug("Accessing board main page");
        return "board/main";
    }

    @GetMapping("/detail")
    public String boardDetail(@RequestParam int id, Model model) {
        logger.debug("Accessing board detail page for id: {}", id);
        model.addAttribute("detail", boardService.selectBoardDetail(id));
        return "board/detail";
    }

    @GetMapping("/write")
    public String boardWritePage() {
        logger.debug("Accessing board write page");
        return "board/write";
    }

    @PostMapping("/insert")
    public String boardInsert(@Valid @ModelAttribute BoardDTO dto, 
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        logger.info("Attempting to insert board: title={}, writer={}", dto.getTitle(), dto.getWriter());
        
        if (bindingResult.hasErrors()) {
            logger.warn("Validation errors in board insert: {}", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("error", "입력 정보를 확인해주세요.");
            return "redirect:/board/write";
        }
        
        try {
            boardService.insertBoard(dto);
            redirectAttributes.addFlashAttribute("success", "게시글이 등록되었습니다.");
            logger.info("Board inserted successfully");
            return "redirect:/board/main";
        } catch (Exception e) {
            logger.error("Error inserting board", e);
            redirectAttributes.addFlashAttribute("error", "게시글 등록 중 오류가 발생했습니다.");
            return "redirect:/board/write";
        }
    }

    @GetMapping("/edit")
    public String boardEditPage(@RequestParam int id, Model model) {
        logger.debug("Accessing board edit page for id: {}", id);
        model.addAttribute("detail", boardService.selectBoardDetail(id));
        return "board/edit";
    }

    @PostMapping("/update")
    public String boardUpdate(@Valid @ModelAttribute BoardDTO dto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        logger.info("Attempting to update board: id={}, title={}", dto.getBoardId(), dto.getTitle());
        
        if (bindingResult.hasErrors()) {
            logger.warn("Validation errors in board update: {}", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("error", "입력 정보를 확인해주세요.");
            return "redirect:/board/edit?id=" + dto.getBoardId();
        }
        
        try {
            boardService.updateBoard(dto);
            redirectAttributes.addFlashAttribute("success", "게시글이 수정되었습니다.");
            logger.info("Board updated successfully: id={}", dto.getBoardId());
            return "redirect:/board/detail?id=" + dto.getBoardId();
        } catch (Exception e) {
            logger.error("Error updating board: id={}", dto.getBoardId(), e);
            redirectAttributes.addFlashAttribute("error", "게시글 수정 중 오류가 발생했습니다.");
            return "redirect:/board/edit?id=" + dto.getBoardId();
        }
    }
}
