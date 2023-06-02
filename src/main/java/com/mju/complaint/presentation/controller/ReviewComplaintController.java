package com.mju.complaint.presentation.controller;

import com.mju.complaint.application.ReviewComplaintService;
import com.mju.complaint.domain.Complaint;
import com.mju.complaint.domain.model.Result.CommonResult;
import com.mju.complaint.domain.service.ResponseService;
import com.mju.complaint.domain.service.UserService;
import com.mju.complaint.presentation.dto.ComplaintRegisterDto;
import jakarta.servlet.http.HttpServletRequest;
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
    private final UserService userService;
    @GetMapping("/ping")
    public String ping() {
        return "제발";
    }
    @PostMapping(value = "/register/{reviewIndex}")
    public CommonResult registerReview(@PathVariable Long reviewIndex, @RequestBody ComplaintRegisterDto complaintRegisterDto, HttpServletRequest request){
        String userId = userService.getUserId(request);
        userService.checkUserId(userId);
        reviewComplaintService.registerReview(userId, reviewIndex, complaintRegisterDto);
        return responseService.getSuccessfulResult();
    }

    @GetMapping(value = "/show/listReview")
    public CommonResult listReview(HttpServletRequest request){
        List<Object> reportedReviewList = reviewComplaintService.getReviewList();
        String userId = userService.getUserId(request);
        userService.checkUserType(userId, "MANAGER");
        CommonResult commonResult = responseService.getListResult(reportedReviewList);
        return commonResult;
    }

    @GetMapping("/content/show/{reportedReviewIndex}")
    public CommonResult reportedReviewContentFindById(@PathVariable long reportedReviewIndex,HttpServletRequest request) {
        LinkedHashMap<String, Object> ReviewContent = reviewComplaintService.getReviewContent(reportedReviewIndex);
        String userId = userService.getUserId(request);
        userService.checkUserType(userId, "MANAGER");
        CommonResult commonResult = responseService.getSingleResult(ReviewContent);
        return commonResult;
    }

    @GetMapping("/complaint/show/{reportedReviewIndex}")
    public CommonResult reportedReviewFindById(@PathVariable long reportedReviewIndex,HttpServletRequest request){
        List<Complaint> reviewComplaintList = reviewComplaintService.getReportedReview(reportedReviewIndex);
        String userId = userService.getUserId(request);
        userService.checkUserType(userId, "MANAGER");
        CommonResult commonResult = responseService.getSingleResult(reviewComplaintList);
        return commonResult;
    }

    @DeleteMapping("/delete/{complaintIndex}")
    public CommonResult deleteReview(@PathVariable long complaintIndex, HttpServletRequest request){
        String userId = userService.getUserId(request);
        userService.checkUserType(userId, "MANAGER");
        reviewComplaintService.deleteComplaint(complaintIndex);
        return responseService.getSuccessfulResult();
    }
}
