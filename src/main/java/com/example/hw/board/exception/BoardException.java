package com.example.hw.board.exception;

public class BoardException extends RuntimeException {
    public BoardException(String message) {
        super(message);
    }
    
    public BoardException(String message, Throwable cause) {
        super(message, cause);
    }
}
