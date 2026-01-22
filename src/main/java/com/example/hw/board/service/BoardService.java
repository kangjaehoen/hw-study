package com.example.hw.board.service;

import com.example.hw.board.dto.BoardDTO;

import java.util.List;
import java.util.Map;


public interface BoardService {
    public List<Map<String, Object>> selectBoardList();
    public BoardDTO selectBoardDetail(int id);

}
