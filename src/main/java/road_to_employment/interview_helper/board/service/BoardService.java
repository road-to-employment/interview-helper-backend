package road_to_employment.interview_helper.board.service;

import road_to_employment.interview_helper.board.service.request.BoardCreateRequest;
import road_to_employment.interview_helper.board.service.response.BoardCreateResponse;

public interface BoardService {
    BoardCreateResponse create(BoardCreateRequest boardCreateRequest);
}
