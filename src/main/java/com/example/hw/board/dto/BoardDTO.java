package com.example.hw.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDTO {
    private Long boardId;
    
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200, message = "제목은 200자 이하여야 합니다.")
    private String title;
    
    @Size(max = 5000, message = "내용은 5000자 이하여야 합니다.")
    private String content;
    
    @NotBlank(message = "작성자는 필수입니다.")
    @Size(max = 50, message = "작성자는 50자 이하여야 합니다.")
    private String writer;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
