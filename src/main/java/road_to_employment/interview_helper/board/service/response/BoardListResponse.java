package road_to_employment.interview_helper.board.service.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import road_to_employment.interview_helper.board.entity.Board;

import java.time.ZonedDateTime;

@Getter
@RequiredArgsConstructor
public class BoardListResponse {
    private final Long id;
    private final String title;
    private final String writer;
    private final ZonedDateTime updateDate;

    public static BoardListResponse from(Board board) {
        return new BoardListResponse(
                board.getId(),
                board.getTitle(),
                board.getWriter(),
                board.getUpdateDate());
    }

}
