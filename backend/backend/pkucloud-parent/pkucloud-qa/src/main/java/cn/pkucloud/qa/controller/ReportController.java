package cn.pkucloud.qa.controller;

import cn.pkucloud.common.PageResult;
import cn.pkucloud.common.Result;
import cn.pkucloud.qa.entity.Report;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("report")
@CrossOrigin
public class ReportController {
    private final QaService qaService;

    public ReportController(QaService qaService) {
        this.qaService = qaService;
    }

    @GetMapping("{id}")
    public Result<Report> getReportById(@PathVariable String id) {
        return qaService.getReportById(id);
    }

    @GetMapping("page/{page}")
    public PageResult<Report> getReportByPage(@PathVariable String page) {
        return qaService.getReportByPage(page);
    }

    @PutMapping("{id}")
    public Result<?> putReportById(@PathVariable String id) {
        return qaService.putReportById(id);
    }

    @DeleteMapping("{id}")
    public Result<?> deleteReportById(@PathVariable String id) {
        return qaService.deleteReportById(id);
    }
}