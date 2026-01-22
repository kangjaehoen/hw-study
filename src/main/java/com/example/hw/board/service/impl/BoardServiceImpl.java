package com.example.hw.board.service.impl;

import com.example.hw.board.mapper.BoardMapper;
import com.example.hw.board.service.BoardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    public List<Map<String,Object>> selectBoardList() {
        return boardMapper.selectBoardList();
    }
}
