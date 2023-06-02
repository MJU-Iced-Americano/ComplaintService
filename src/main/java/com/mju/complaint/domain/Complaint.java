package com.mju.complaint.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "complaint")
public class Complaint {


    public enum ComplaintType {
        HATE_SPEECH, PROFANITY, SPAM, ILLEGAL_CONTENT, ETC;
    }

    @Builder
    public Complaint(String complaintContent, ComplaintType type , Long questionIndex, Long commendIndex, Long reviewIndex, String userId, String reportedUserId){
        this.complaintContent= complaintContent;
        this.type = type;
        this.questionIndex = questionIndex;
        this.commendIndex = commendIndex;
        this.reviewIndex = reviewIndex;
        this.userId = userId;
        this.reportedUserId = reportedUserId;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complaintIndex;

    @Column(name = "complaint_content")
    private String complaintContent;

    @Column(name = "question_complaint_index")
    private Long questionIndex;

    @Column(name = "commend_complaint_index")
    private Long commendIndex;

    @Column(name = "review_complaint_index")
    private Long reviewIndex;

    @Column(name = "reported_user_index")
    private String reportedUserId;

    @Column(name = "user_index")
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "complaint_type")
    private ComplaintType type;
    @Transient
    private String reportedUserName;
    public void addUserName(String reportedUserName) {
        this.reportedUserName = reportedUserName;
    }

}
