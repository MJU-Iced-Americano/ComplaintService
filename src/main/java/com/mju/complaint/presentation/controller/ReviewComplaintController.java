package com.mju.complaint.presentation.controller;

import com.mju.complaint.application.ReviewComplaintService;
import com.mju.complaint.domain.Complaint;
import com.mju.complaint.domain.model.Result.CommonResult;
import com.mju.complaint.domain.service.ResponseService;
import com.mju.complaint.presentation.dto.ComplaintRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/complaint-service/review")
@CrossOrigin(origins = "*")
public class ReviewComplaintController {

    private final ReviewComplaintService reviewComplaintService;
    private final ResponseService responseService;

    @GetMapping("/ping")
    public String ping() {
        return "제발";
    }
    @PostMapping(value = "/register/{reviewIndex}")
    public CommonResult registerReview(@PathVariable Long reviewIndex, @RequestBody ComplaintRegisterDto complaintRegisterDto){
        reviewComplaintService.registerReview(reviewIndex, complaintRegisterDto);
        return responseService.getSuccessfulResult();
    }

    @GetMapping(value = "/show/listReview")
    public CommonResult listReview(){
        List<Object> reportedReviewList = reviewComplaintService.getReviewList();
        CommonResult commonResult = responseService.getListResult(reportedReviewList);
        return commonResult;
    }

    @GetMapping("/content/show/{reportedReviewIndex}")
    public CommonResult reportedReviewContentFindById(@PathVariable long reportedReviewIndex) {
        LinkedHashMap<String, Object> ReviewContent = reviewComplaintService.getReviewContent(reportedReviewIndex);
        CommonResult commonResult = responseService.getSingleResult(ReviewContent);
        return commonResult;
    }

    @GetMapping("/complaint/show/{reportedReviewIndex}")
    public CommonResult reportedReviewFindById(@PathVariable long reportedReviewIndex){
        List<Complaint> reviewComplaintList = reviewComplaintService.getReportedReview(reportedReviewIndex);
        CommonResult commonResult = responseService.getSingleResult(reviewComplaintList);
        return commonResult;
    }

    @DeleteMapping("/delete/{complaintIndex}")
    public CommonResult deleteReview(@PathVariable long complaintIndex){
        reviewComplaintService.deleteComplaint(complaintIndex);
        return responseService.getSuccessfulResult();
    }
}
