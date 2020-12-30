package cn.pkucloud.obs.controller;

import cn.pkucloud.common.Result;
import cn.pkucloud.obs.service.ObsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api")
@CrossOrigin
public class ObsController {
    @Autowired
    private ObsService obsService;

    @GetMapping("signature")
    public Result<?> getPostSignature(String md5) {

        return obsService.getPostSignature();
    }
}
