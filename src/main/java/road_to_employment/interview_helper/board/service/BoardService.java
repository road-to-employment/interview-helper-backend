package road_to_employment.interview_helper.board.service;

import road_to_employment.interview_helper.board.entity.Board;
import road_to_employment.interview_helper.board.service.request.BoardCreateRequest;
import road_to_employment.interview_helper.board.service.response.BoardCreateResponse;
import road_to_employment.interview_helper.board.service.response.BoardListResponse;

import java.util.List;

public interface BoardService {
    BoardCreateResponse create(BoardCreateRequest boardCreateRequest);
    List<BoardListResponse> list();
}
