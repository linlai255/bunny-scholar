package com.linlai.bunnyscholar.controller;

import com.linlai.bunnyscholar.pojo.CurrentUser;
import com.linlai.bunnyscholar.pojo.Result;
import com.linlai.bunnyscholar.service.IRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author linlai
 * @since 2023-03-18
 */
@RestController
@RequestMapping("/api/record")
public class RecordController {

    private IRecordService recordService;

    public RecordController(IRecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping("/submit")
    public Result<String> submit(@RequestBody String content) {
        String result = recordService.submit(content, CurrentUser.get());
        return Result.success(result);
    }

}
