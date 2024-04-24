package org.zerock.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.springboot.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
