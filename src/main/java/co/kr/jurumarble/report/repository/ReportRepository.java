package co.kr.jurumarble.report.repository;

import co.kr.jurumarble.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    boolean existsByReportUserIdAndReportedUserId(Long reporterUserId, Long reportedUserId);
}
