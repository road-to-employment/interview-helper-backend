package road_to_employment.interview_helper.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import road_to_employment.interview_helper.board.entity.Board;
import road_to_employment.interview_helper.board.repository.BoardRepository;
import road_to_employment.interview_helper.board.service.request.BoardCreateRequest;
import road_to_employment.interview_helper.board.service.request.BoardUpdateRequest;
import road_to_employment.interview_helper.board.service.response.BoardCreateResponse;
import road_to_employment.interview_helper.board.service.response.BoardListResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<BoardListResponse> list() {
        log.info("board service -> list() called!");
        List<Board> boardList = boardRepository.findAll();

        return boardList.stream().map(BoardListResponse::from).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public BoardReadResponse update(Long id, BoardUpdateRequest boardUpdateRequest) {
        System.out.println(boardUpdateRequest.getTitle());
        log.info("board service -> update() called!");
        Optional<Board> maybeBoard = boardRepository.findById(id);

        Board board = maybeBoard.orElse(null);

        if (board != null) {
            board.updatedBoard(
                    boardUpdateRequest.getTitle(),
                    boardUpdateRequest.getContent()
            );
        }

        return BoardReadResponse.from(board);
    }
}
