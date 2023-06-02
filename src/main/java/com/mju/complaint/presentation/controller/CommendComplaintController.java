package com.mju.complaint.presentation.controller;

import com.mju.complaint.application.CommendComplaintService;
import com.mju.complaint.application.QnAComplaintService;
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
@RequestMapping("/complaint-service/commend")
@CrossOrigin(origins = "*")
public class CommendComplaintController {

    private final CommendComplaintService commendComplaintService;

    private final ResponseService responseService;
    private final UserService userService;
    @GetMapping("/ping")
    public String ping() {
        return "paadfong";
    }
//////////////////////////////////////<답변 관리>//////////////////////////////////////////////////////////////

    // [강사진,수강생,관리자]답변 신고 등록
    @PostMapping(value = "/register/{commendIndex}")
    public CommonResult registerCommend(@PathVariable Long commendIndex, @RequestBody ComplaintRegisterDto complaintRegisterDto, HttpServletRequest request) {
        String userId = userService.getUserId(request);
        userService.checkUserId(userId);
        commendComplaintService.registerCommend(userId, commendIndex, complaintRegisterDto);
        return responseService.getSuccessfulResult();
    }

    //신고된 답변 List 조회
    @GetMapping(value = "/show/listQnA")
    public CommonResult listQnA(HttpServletRequest request) {
        List<Object> reportedCommendList = commendComplaintService.getCommendList();
        String userId = userService.getUserId(request);
        userService.checkUserType(userId, "MANAGER");
        CommonResult commonResult = responseService.getListResult(reportedCommendList);
        return commonResult;
    }
    //신고된 답변 선택한것 조회(답변 내용)
    @GetMapping("/content/show/{reportedCommendIndex}")
    public CommonResult reportedCommendContentFindById(@PathVariable long reportedCommendIndex,HttpServletRequest request) {
        LinkedHashMap<String, Object> CommendContent = commendComplaintService.getCommendContent(reportedCommendIndex);
        String userId = userService.getUserId(request);
        userService.checkUserType(userId, "MANAGER");
        CommonResult commonResult = responseService.getSingleResult(CommendContent);
        return commonResult;
    }

    //신고된 답변 선택한것 조회(신고 내용 목록)
    @GetMapping("/complaint/show/{reportedCommendIndex}")
    public CommonResult reportedQnAFindById(@PathVariable long reportedCommendIndex,HttpServletRequest request) {
        List<Complaint> commendComplaintList = commendComplaintService.getReportedCommend(reportedCommendIndex);
        String userId = userService.getUserId(request);
        userService.checkUserType(userId, "MANAGER");
        CommonResult commonResult = responseService.getSingleResult(commendComplaintList);
        return commonResult;
    }
    // 신고내역 삭제
    @DeleteMapping("/delete/{complaintIndex}")
    public CommonResult deleteComplaint(@PathVariable long complaintIndex,HttpServletRequest request) {
        String userId = userService.getUserId(request);
        userService.checkUserType(userId, "MANAGER");
        commendComplaintService.deleteComplaint(complaintIndex);
        return responseService.getSuccessfulResult();
    }



}
