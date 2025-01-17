package cha.friendly.repository;

import cha.friendly.domain.Memo;
import cha.friendly.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    List<Point> findByMemberId(Long id);
}
