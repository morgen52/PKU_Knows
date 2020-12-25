import com.obs.services.ObsClient;
import com.obs.services.model.PostSignatureRequest;
import com.obs.services.model.PostSignatureResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ObsTest {
    private static String endPoint = "i.pkucloud.cn";

    private static String ak = "OFGKBBEFMXVZMDWLT85N";

    private static String sk = "n0UsVYitR2sGFgHYGwwnRxQ4a1oGIOKBQ7bJxp2T";

    public static void main(String[] args) {
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);

        PostSignatureRequest request = new PostSignatureRequest();

        Map<String, Object> formParams = new HashMap<>();

        formParams.put("x-obs-acl", "private");
        formParams.put("cache-control", "public, max-age=31536000");
        formParams.put("content-type", "image/jpeg");
        formParams.put("content-md5", "1234");

        String key = UUID.randomUUID().toString().replace("-", "");
        request.setObjectKey(key);
        request.setBucketName("wepku-i");
        ArrayList<String> conditions = new ArrayList<>();
        conditions.add("\"{'x-obs-acl': 'private'}\"");

        PostSignatureResponse response = obsClient.createPostSignature(request);
        System.out.println("response = " + response.getPolicy());
        System.out.println("response = " + response.getSignature());
    }
}
