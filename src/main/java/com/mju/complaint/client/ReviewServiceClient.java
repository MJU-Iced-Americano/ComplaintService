package com.mju.complaint.client;

import com.mju.complaint.domain.model.Result.SingleResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "review-service")
public interface ReviewServiceClient {

    @GetMapping("13.209.194.108/review-service/review/show/request/{reviewIndex}")
    public SingleResult reviewFindById(@PathVariable Long reviewIndex);
}
