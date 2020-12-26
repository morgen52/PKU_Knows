package cn.pkucloud.wxmp.controller;

import cn.pkucloud.wxmp.crypto.CryptoException;
import cn.pkucloud.wxmp.dto.wx.xml.XmlRequest;
import cn.pkucloud.wxmp.dto.wx.xml.XmlResponse;
import cn.pkucloud.wxmp.service.MpService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("api/mp")
@Validated
public class MpController {
    private final MpService mpService;

    public MpController(MpService mpService) {
        this.mpService = mpService;
    }

    @GetMapping
    public String echo(@NotBlank String signature,
                       @NotBlank String echostr,
                       @Positive int timestamp,
                       @NotBlank String nonce) {
        return mpService.echo(signature, echostr, timestamp, nonce);
    }

    @PostMapping(consumes = "text/xml", produces = "text/xml")
    public XmlResponse msgHandler(@NotBlank @RequestParam String signature,
                                  @Positive @RequestParam int timestamp,
                                  @NotBlank @RequestParam String nonce,
                                  @RequestParam(required = false) String openid,
                                  @NotBlank @RequestParam String encrypt_type,
                                  @NotBlank @RequestParam String msg_signature,
                                  @NotNull @RequestBody XmlRequest xmlRequest) throws JsonProcessingException, CryptoException {
        return mpService.msgHandler(signature, timestamp, nonce, openid, encrypt_type, msg_signature, xmlRequest);
    }
}
