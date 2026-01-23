package com.example.hw.board.mapper;

import com.example.hw.board.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {
    List<Map<String,Object>> selectBoardList();
    BoardDTO selectBoardDetail(int id);
    int insertBoard(BoardDTO dto);
    int updateBoard(BoardDTO dto);
    int deleteBoard(Long boardId);
}
