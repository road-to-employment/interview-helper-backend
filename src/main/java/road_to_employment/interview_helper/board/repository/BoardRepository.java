package road_to_employment.interview_helper.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import road_to_employment.interview_helper.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
