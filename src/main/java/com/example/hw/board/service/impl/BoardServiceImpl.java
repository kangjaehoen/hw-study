package com.example.hw.board.service.impl;

import com.example.hw.board.dto.BoardDTO;
import com.example.hw.board.exception.BoardException;
import com.example.hw.board.exception.BoardNotFoundException;
import com.example.hw.board.mapper.BoardMapper;
import com.example.hw.board.service.BoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private static final Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
    private final BoardMapper boardMapper;

    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    public List<Map<String,Object>> selectBoardList() {
        logger.debug("Fetching board list");
        try {
            return boardMapper.selectBoardList();
        } catch (Exception e) {
            logger.error("Error fetching board list", e);
            throw new BoardException("게시글 목록을 조회하는 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public BoardDTO selectBoardDetail(int id) {
        logger.debug("Fetching board detail for id: {}", id);
        try {
            BoardDTO dto = boardMapper.selectBoardDetail(id);
            if (dto == null) {
                logger.warn("Board not found with id: {}", id);
                throw new BoardNotFoundException("게시글을 찾을 수 없습니다. ID: " + id);
            }
            logger.debug("Board detail retrieved: {}", dto);
            return dto;
        } catch (BoardNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error fetching board detail for id: {}", id, e);
            throw new BoardException("게시글 상세 정보를 조회하는 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    @Transactional
    public int insertBoard(BoardDTO dto) {
        logger.info("Inserting new board: title={}, writer={}", dto.getTitle(), dto.getWriter());
        try {
            int result = boardMapper.insertBoard(dto);
            if (result <= 0) {
                logger.error("Failed to insert board");
                throw new BoardException("게시글 등록에 실패했습니다.");
            }
            logger.info("Board inserted successfully");
            return result;
        } catch (BoardException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error inserting board", e);
            throw new BoardException("게시글을 등록하는 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    @Transactional
    public int updateBoard(BoardDTO dto) {
        logger.info("Updating board: id={}, title={}", dto.getBoardId(), dto.getTitle());
        try {
            // 먼저 게시글이 존재하는지 확인
            BoardDTO existingBoard = boardMapper.selectBoardDetail(dto.getBoardId().intValue());
            if (existingBoard == null) {
                logger.warn("Board not found for update: id={}", dto.getBoardId());
                throw new BoardNotFoundException("수정할 게시글을 찾을 수 없습니다. ID: " + dto.getBoardId());
            }
            
            int result = boardMapper.updateBoard(dto);
            if (result <= 0) {
                logger.error("Failed to update board: id={}", dto.getBoardId());
                throw new BoardException("게시글 수정에 실패했습니다.");
            }
            logger.info("Board updated successfully: id={}", dto.getBoardId());
            return result;
        } catch (BoardNotFoundException | BoardException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error updating board: id={}", dto.getBoardId(), e);
            throw new BoardException("게시글을 수정하는 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    @Transactional
    public int deleteBoard(Long boardId) {
        logger.info("Deleting board: id={}", boardId);
        try {
            // 먼저 게시글이 존재하는지 확인
            BoardDTO existingBoard = boardMapper.selectBoardDetail(boardId.intValue());
            if (existingBoard == null) {
                logger.warn("Board not found for delete: id={}", boardId);
                throw new BoardNotFoundException("삭제할 게시글을 찾을 수 없습니다. ID: " + boardId);
            }
            
            int result = boardMapper.deleteBoard(boardId);
            if (result <= 0) {
                logger.error("Failed to delete board: id={}", boardId);
                throw new BoardException("게시글 삭제에 실패했습니다.");
            }
            logger.info("Board deleted successfully: id={}", boardId);
            return result;
        } catch (BoardNotFoundException | BoardException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error deleting board: id={}", boardId, e);
            throw new BoardException("게시글을 삭제하는 중 오류가 발생했습니다.", e);
        }
    }
}
