package road_to_employment.interview_helper.board.service.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import road_to_employment.interview_helper.board.entity.Board;

import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class BoardReadResponse {
    private final String title;
    private final String writer;
    private final String content;

    public static BoardReadResponse from(Board board) {
        if (board == null) {
            return new BoardReadResponse("", "", "");
        }

        return new BoardReadResponse(
                board.getTitle(),
                board.getWriter(),
                board.getContent());
    }
}
