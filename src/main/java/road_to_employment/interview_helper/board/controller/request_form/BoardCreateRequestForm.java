package road_to_employment.interview_helper.board.controller.request_form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import road_to_employment.interview_helper.board.service.request.BoardCreateRequest;

import java.util.Date;

@RequiredArgsConstructor
@Getter
public class BoardCreateRequestForm {
    private final String title;
    private final String writer;
    private final String content;

    public BoardCreateRequest toBoardCreateRequest() {
        return new BoardCreateRequest(
                this.title,
                this.writer,
                this.content);
    }
}
