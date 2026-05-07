package com.example.revise.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CursorPageResponse<T> {
    private List<T> content;
    private int size;
    private boolean hasNext;
    private String nextCursor;
    private long totalElements;

    public static <T> CursorPageResponse<T> of(
            List<T> content,
            int size,
            String nextCursor,
            long totalElements){
        return CursorPageResponse.<T>builder()
                .content(content)
                .size(size)
                .hasNext(nextCursor!=null)
                .nextCursor(nextCursor)
                .totalElements(totalElements)
                .build();
    }
}
