package com.mju.complaint.client;

import com.mju.complaint.domain.model.Result.SingleResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "board-service", url = "http://3.34.240.33:8080")
public interface QuestionBoardServiceClient {

    @GetMapping("/board-service/question/show/request/{questionIndex}")
    public SingleResult questionFindById(@PathVariable Long questionIndex);

    @GetMapping("/board-service/commend/show/request/{commendIndex}")
    public SingleResult commendFindById(@PathVariable Long commendIndex);

}
