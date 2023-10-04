package co.kr.jurumarble.report.service;

import co.kr.jurumarble.exception.report.DuplicateReportException;
import co.kr.jurumarble.exception.report.SelfReportNotAllowedException;
import co.kr.jurumarble.report.repository.ReportRepository;
import co.kr.jurumarble.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportValidator {
    private final ReportRepository reportRepository;

    public void validateSelfReport(User reportUser, User reportedUser) {
        if (reportUser.equals(reportedUser)) {
            throw new SelfReportNotAllowedException();
        }
    }

    public void validateAlreadyReported(User reportUser, User reportedUser) {
        boolean hasAlreadyReported = reportRepository.existsByReportUserIdAndReportedUserId(reportUser.getId(), reportedUser.getId());
        if (hasAlreadyReported) {
            throw new DuplicateReportException();
        }
    }

}

