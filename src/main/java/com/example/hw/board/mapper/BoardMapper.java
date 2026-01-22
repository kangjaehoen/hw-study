package com.example.hw.board.mapper;

import com.example.hw.board.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {
    public List<Map<String,Object>> selectBoardList();
    public BoardDTO selectBoardDetail(int id);

}
