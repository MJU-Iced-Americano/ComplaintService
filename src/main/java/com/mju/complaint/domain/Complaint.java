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
    public Complaint(String complaintContent, ComplaintType type , Long questionIndex, Long commendIndex, Long reviewIndex /* ,QuestionCommend questionCommend*/){
        this.complaintContent= complaintContent;
        this.type = type;
        this.questionIndex = questionIndex;
        this.commendIndex = commendIndex;
        this.reviewIndex = reviewIndex;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long complaintIndex;

    @Column(name = "complaint_content")
    private String complaintContent;
//    @JsonIgnore
//    @OneToMany(mappedBy = "questionBoard", cascade = CascadeType.ALL)
//    private List<QuestionComplaint> questionComplaintList = new ArrayList<>(); //아직은 신고 테이블에서만 처리해서 사용을 안함.
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "question_index")
//    private QuestionBoard questionBoard;

    @Column(name = "question_index")
    private Long questionIndex;

    @Column(name = "commend_index")
    private Long commendIndex;

    @Column(name = "review_index")
    private Long reviewIndex;

//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "commend_index")
//    private QuestionCommend questionCommend;

    @Enumerated(EnumType.STRING)
    @Column(name = "complaint_type")
    private ComplaintType type;


//    @JoinColumn(name = "complainer_index")
//    private User user;

}
