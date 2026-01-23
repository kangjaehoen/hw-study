package com.example.hw.board.service.impl;

import com.example.hw.board.dto.BoardDTO;
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

    @Override
    public BoardDTO selectBoardDetail(int id) {
        BoardDTO dto = boardMapper.selectBoardDetail(id);
        System.out.println("boardDetail : dto : " +dto);
        return dto;
    }

    @Override
    public int insertBoard(BoardDTO dto) {
        int num = boardMapper.insertBoard(dto);
        return num;
    }

    @Override
    public int updateBoard(BoardDTO dto) {
        int num = boardMapper.updateBoard(dto);
        return num;
    }

    @Override
    public int deleteBoard(Long boardId) {
        Long num = boardMapper.deleteBoard(boardId);
        return Math.toIntExact(num);
    }
}
