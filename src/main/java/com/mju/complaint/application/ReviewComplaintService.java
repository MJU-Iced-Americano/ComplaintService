package com.mju.complaint.application;

import com.mju.complaint.domain.Complaint;
import com.mju.complaint.presentation.dto.ComplaintRegisterDto;

import java.util.LinkedHashMap;
import java.util.List;

public interface ReviewComplaintService {

    public void registerReview(String userId, Long reviewIndex, ComplaintRegisterDto complaintRegisterDto);

    public List<Object> getReviewList();

    public LinkedHashMap<String, Object> getReviewContent(long reportedReviewIndex);

    public List<Complaint> getReportedReview(long reportedReviewIndex);

    public void deleteComplaint(long complaintIndex);
}
