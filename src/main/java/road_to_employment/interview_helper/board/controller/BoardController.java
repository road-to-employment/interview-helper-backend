package road_to_employment.interview_helper.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import road_to_employment.interview_helper.board.controller.request_form.BoardCreateRequestForm;
import road_to_employment.interview_helper.board.controller.response_form.BoardCreateResponseForm;
import road_to_employment.interview_helper.board.service.BoardService;
import road_to_employment.interview_helper.board.service.response.BoardCreateResponse;

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
}
