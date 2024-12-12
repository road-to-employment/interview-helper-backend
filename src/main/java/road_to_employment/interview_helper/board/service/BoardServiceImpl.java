package road_to_employment.interview_helper.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import road_to_employment.interview_helper.board.entity.Board;
import road_to_employment.interview_helper.board.repository.BoardRepository;
import road_to_employment.interview_helper.board.service.request.BoardCreateRequest;
import road_to_employment.interview_helper.board.service.response.BoardCreateResponse;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    public BoardCreateResponse create(BoardCreateRequest boardCreateRequest) {
        log.info("board service -> create() called!");
        Board board = boardCreateRequest.toBoard();
        Board createdBoard = boardRepository.save(board);

        return BoardCreateResponse.from(createdBoard);
    }
}
