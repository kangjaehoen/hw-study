package com.example.hw.board.service;

import com.example.hw.board.dto.BoardDTO;

import java.util.List;
import java.util.Map;

public interface BoardService {
    List<Map<String, Object>> selectBoardList();
    BoardDTO selectBoardDetail(int id);
    int insertBoard(BoardDTO dto);
    int updateBoard(BoardDTO dto);
    int deleteBoard(Long boardId);
}
