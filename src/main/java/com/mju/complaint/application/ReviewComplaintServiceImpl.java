package com.mju.complaint.application;

import com.mju.complaint.client.ReviewServiceClient;
import com.mju.complaint.domain.Complaint;
import com.mju.complaint.domain.model.Exception.ExceptionList;
import com.mju.complaint.domain.model.Exception.NonExceptionReportedReview;
import com.mju.complaint.domain.model.Exception.ServerRequestFailed;
import com.mju.complaint.domain.model.Result.SingleResult;
import com.mju.complaint.domain.repository.ComplaintRepository;
import com.mju.complaint.presentation.dto.ComplaintRegisterDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewComplaintServiceImpl implements ReviewComplaintService{
    private final ComplaintRepository complaintRepository;
    private final ReviewServiceClient reviewServiceClient;
    @Override
    @Transactional
    public void registerReview(String userId, Long reviewIndex, ComplaintRegisterDto complaintRegisterDto) {
        SingleResult<LinkedHashMap<String, Object>> requestResult = reviewServiceClient.reviewFindById(reviewIndex);
        LinkedHashMap<String, Object> data = requestResult.getData();
        if (data!=null){
            Integer reviewIndexInt = (Integer) data.get("reviewIndex");
            Long reviewIndexValue = reviewIndexInt.longValue();
            String reportedUserId = (String) data.get("userId");//리뷰서비스에서 이거 넣어놔야 해 참고로 조회를 위해 Name도 같이

            Complaint complaint = Complaint.builder()
                    .reviewIndex(reviewIndexValue)
                    .complaintContent(complaintRegisterDto.getComplaintContent())
                    .type(complaintRegisterDto.getType())
                    .userId(userId)
                    .reportedUserId(reportedUserId)
                    .build();
            complaintRepository.save(complaint);
        } else {
            throw new ServerRequestFailed(ExceptionList.SERVER_REQUEST_FAILED);
        }
    }

    @Override
    @Transactional
    public List<Object> getReviewList() {
        List<Complaint> comlaintReviewList = complaintRepository.findAllByReviewIndexNotNull();
        List<Object> reportedReviewList = new ArrayList<>();
        Set<Long> visitedReviewIndexes = new HashSet<>();

        for(Complaint complaint : comlaintReviewList) {
            Long reviewIndex = complaint.getReviewIndex();

            if(visitedReviewIndexes.contains(reviewIndex)){
                continue;
            }
            SingleResult<LinkedHashMap<String, Object>> requestResult = reviewServiceClient.reviewFindById(reviewIndex);
            LinkedHashMap<String, Object> data = requestResult.getData();
            if(data!=null) {
                reportedReviewList.add(data);
                visitedReviewIndexes.add(reviewIndex);
            }
        }

        if(!reportedReviewList.isEmpty()){
            return reportedReviewList;
        }else{
            throw new NonExceptionReportedReview(ExceptionList.NON_EXCEPTION_REPORTED_REVIEW);
        }
    }

    @Override
    @Transactional
    public LinkedHashMap<String, Object> getReviewContent(long reportedReviewIndex) {
        List<Complaint> complaintReviewList = complaintRepository.findAllByReviewIndexNotNull();
        Complaint complaint = complaintReviewList.stream()
                .filter(complaintComponent -> complaintComponent.getReviewIndex() == reportedReviewIndex)
                .findFirst()
                .orElseThrow(() -> new NonExceptionReportedReview(ExceptionList.NOT_REPORTED_REVIEW));

        SingleResult<LinkedHashMap<String, Object>> requestResult = reviewServiceClient.reviewFindById(complaint.getReviewIndex());
        LinkedHashMap<String, Object> data = requestResult.getData();
        if (data != null) {
            return data;
        }else{
            throw new ServerRequestFailed(ExceptionList.SERVER_REQUEST_FAILED);
        }
    }

    @Override
    @Transactional
    public List<Complaint> getReportedReview(long reportedReviewIndex) {
        List<Complaint> reviewComplaintList = complaintRepository.findAllByReviewIndexNotNullAndReviewIndex(reportedReviewIndex);
        if(!reviewComplaintList.isEmpty()){
            return reviewComplaintList;
        }else {
            throw new NonExceptionReportedReview(ExceptionList.NOT_REPORTED_REVIEW);
        }
    }

    @Override
    @Transactional
    public void deleteComplaint(long complaintIndex) {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(complaintIndex);
        if (optionalComplaint.isPresent()) {
            complaintRepository.delete(optionalComplaint.get());
        } else {
            throw new NonExceptionReportedReview(ExceptionList.NON_EXCEPTION_REPORTED_REVIEW);
        }
    }
}
