package road_to_employment.interview_helper.board.controller.response_form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import road_to_employment.interview_helper.board.service.response.BoardListResponse;

import java.time.ZonedDateTime;

@Getter
@RequiredArgsConstructor
public class BoardListResponseForm {
    private final int id;
    private final String title;
    private final String writer;
    private final ZonedDateTime updateDate;

    public static BoardListResponseForm from(BoardListResponse boardListResponse) {
        return new BoardListResponseForm(
                boardListResponse.getId(),
                boardListResponse.getTitle(),
                boardListResponse.getWriter(),
                boardListResponse.getUpdateDate()
        );
    }

}
