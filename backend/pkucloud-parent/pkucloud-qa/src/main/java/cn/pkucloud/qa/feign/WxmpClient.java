package cn.pkucloud.qa.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "pkucloud-wxmp")
public interface WxmpClient {
}
