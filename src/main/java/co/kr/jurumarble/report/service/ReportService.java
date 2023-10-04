package co.kr.jurumarble.report.service;

import co.kr.jurumarble.comment.domain.Comment;
import co.kr.jurumarble.comment.repository.CommentRepository;
import co.kr.jurumarble.exception.comment.CommentNotFoundException;
import co.kr.jurumarble.exception.user.UserNotFoundException;
import co.kr.jurumarble.exception.vote.VoteNotFoundException;
import co.kr.jurumarble.report.domain.Report;
import co.kr.jurumarble.report.enums.ReportType;
import co.kr.jurumarble.report.repository.ReportRepository;
import co.kr.jurumarble.user.domain.User;
import co.kr.jurumarble.user.repository.UserRepository;
import co.kr.jurumarble.vote.domain.Vote;
import co.kr.jurumarble.vote.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;
    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;
    private final ReportValidator reportValidator;

    @Transactional
    public void reportComment(ReportServiceRequest request, Long userId, ReportType reportType) {
        User reportUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Comment reportedComment = commentRepository.findById(request.getReportedObjectId()).orElseThrow(CommentNotFoundException::new);
        User reportedUser = reportedComment.getUser();
        reportValidator.validateSelfReport(reportUser, reportedUser);
        reportValidator.validateAlreadyReported(reportUser, reportedUser);
        Report report = request.toReport(userId, reportedUser.getId(), reportType);
        reportRepository.save(report);
    }


    @Transactional
    public void reportVote(ReportServiceRequest request, Long userId, ReportType reportType) {
        User reportUser = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Vote reportedVote = voteRepository.findById(request.getReportedObjectId()).orElseThrow(VoteNotFoundException::new);
        User reportedUser = userRepository.findById(reportedVote.getPostedUserId()).orElseThrow(UserNotFoundException::new);
        reportValidator.validateSelfReport(reportUser, reportedUser);
        reportValidator.validateAlreadyReported(reportUser, reportedUser);
        Report report = request.toReport(userId, reportedUser.getId(), reportType);
        reportRepository.save(report);
    }


}
