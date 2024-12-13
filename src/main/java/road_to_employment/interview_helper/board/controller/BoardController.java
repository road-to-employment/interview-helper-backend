package road_to_employment.interview_helper.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import road_to_employment.interview_helper.board.controller.request_form.BoardCreateRequestForm;
import road_to_employment.interview_helper.board.controller.response_form.BoardCreateResponseForm;
import road_to_employment.interview_helper.board.controller.response_form.BoardListResponseForm;
import road_to_employment.interview_helper.board.controller.request_form.BoardUpdateRequestForm;
import road_to_employment.interview_helper.board.service.BoardService;
import road_to_employment.interview_helper.board.service.response.BoardCreateResponse;
import road_to_employment.interview_helper.board.service.response.BoardListResponse;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/create")
    public BoardCreateResponseForm create(@ModelAttribute BoardCreateRequestForm boardCreateRequestForm) {
        log.info("board controller -> create() called!");

        BoardCreateResponse response = boardService.create(boardCreateRequestForm.toBoardCreateRequest());
        return BoardCreateResponseForm.from(response);
    }

    @GetMapping("/list")
    public List<BoardListResponseForm> list() {
        log.info("board controller -> list() called!");

        List<BoardListResponse> responseList = boardService.list();
        return responseList.stream().map(BoardListResponseForm::from).collect(Collectors.toList());
    }

    @PostMapping("/modify/{id}")
    public BoardReadResponseForm update(@PathVariable Long id, @RequestBody BoardUpdateRequestForm boardUpdateRequestForm) {
        log.info("board controller -> update() called!");
        System.out.println(boardUpdateRequestForm.getTitle());

        BoardReadResponse response = boardService.update(id, boardUpdateRequestForm.toBoardUpdateRequest());
        return BoardReadResponseForm.from(response);
    }
}
