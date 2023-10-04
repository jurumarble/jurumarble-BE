package co.kr.jurumarble.report.domain;

import co.kr.jurumarble.common.domain.BaseTimeEntity;
import co.kr.jurumarble.report.enums.ReportType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "report")
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_user_id")
    private Long reportUserId;

    @Column(name = "reported_user_id")
    private Long reportedUserId;


    @Column(name = "reported_object_id")
    private Long reportedObjectId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "report_type")
    private ReportType reportType;

    @Builder
    public Report(Long reportUserId, Long reportedUserId, Long reportedObjectId, ReportType reportType) {
        this.reportUserId = reportUserId;
        this.reportedUserId = reportedUserId;
        this.reportedObjectId = reportedObjectId;
        this.reportType = reportType;
    }
}
