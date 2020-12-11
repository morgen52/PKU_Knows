package cn.pkucloud.qa.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "pkucloud-wxa")
public interface WxaClient {
}
