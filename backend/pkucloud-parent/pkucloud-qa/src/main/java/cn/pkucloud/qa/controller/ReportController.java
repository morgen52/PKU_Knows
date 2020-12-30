package cn.pkucloud.qa.controller;

import cn.pkucloud.common.PageResult;
import cn.pkucloud.common.Result;
import cn.pkucloud.qa.entity.Report;
import cn.pkucloud.qa.service.QaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("qa/report")
public class ReportController {
    @Autowired
    private QaService qaService;

    @GetMapping
    public PageResult<Report> getReportByPage(@RequestHeader("iss") String issuer,
                                              @RequestHeader("uid") String uid,
                                              @RequestHeader("role") String role,
                                              @RequestHeader("mod") String mod,
                                              @RequestParam int size,
                                              @RequestParam int page) {
        return qaService.getReportByPage(issuer, uid, role, mod, size, page);
    }

    @GetMapping("{id}")
    public Result<Report> getReportById(@RequestHeader("iss") String issuer,
                                        @RequestHeader("uid") String uid,
                                        @RequestHeader("role") String role,
                                        @RequestHeader("mod") String mod,
                                        @PathVariable String id) {
        return qaService.getReportById(issuer, uid, role, mod, id);
    }
}
