package com.sinamekidev.maraton.cloud;

import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.huaweicloud.sdk.obs.v1.ObsClient;
import com.huaweicloud.sdk.obs.v1.region.ObsRegion;
import okhttp3.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class BucketManager {
    private static BucketManager bucketManagerInstance = null;
    private final String AK="";
    private final String SK="";
    private ICredential auth;
    private ObsClient obsClient;
    private String header = "MIIRHQYJKoZIhvcNAQcCoIIRDjCCEQoCAQExDTALBglghkgBZQMEAgEwgg8vBgkqhkiG9w0BBwGggg8gBIIPHHsidG9rZW4iOnsiZXhwaXJlc19hdCI6IjIwMjMtMTAtMTJUMDc6NDQ6MzkuMzI2MDAwWiIsIm1ldGhvZHMiOlsicGFzc3dvcmQiXSwiY2F0YWxvZyI6W10sInJvbGVzIjpbeyJuYW1lIjoidGVfYWRtaW4iLCJpZCI6IjAifSx7Im5hbWUiOiJ0ZV9hZ2VuY3kiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jc2JzX3JlcF9hY2NlbGVyYXRpb24iLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NfZGlza0FjYyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Rzc19tb250aCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX29ic19kZWVwX2FyY2hpdmUiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9iY3NfbmVzX3NnIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYV9jbi1zb3V0aC00YyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2RlY19tb250aF91c2VyIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfaW50bF9vYSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Nicl9zZWxsb3V0IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZmxvd19jYSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc19vbGRfcmVvdXJjZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3Bhbmd1IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfd2VsaW5rYnJpZGdlX2VuZHBvaW50X2J1eSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Nicl9maWxlIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfaWRtZV9saW5reF9mb3VuZGF0aW9uIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZG1zLXJvY2tldG1xNS1iYXNpYyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Rtcy1rYWZrYTMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9zbnQ5YmlubCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2VkZ2VzZWNfb2J0IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfb2JzX2RlY19tb250aCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2NzYnNfcmVzdG9yZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2Vjc19jNmEiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9FQ19PQlQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9rb29waG9uZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX211bHRpX2JpbmQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9zbW5fY2FsbG5vdGlmeSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX29yZ2lkX2NhIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYV9hcC1zb3V0aGVhc3QtM2QiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9pYW1faWRlbnRpdHljZW50ZXJfaW50bCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2NzYnNfcHJvZ3Jlc3NiYXIiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9jZXNfcmVzb3VyY2Vncm91cF90YWciLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3Nfb2ZmbGluZV9hYzciLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9ldnNfcmV0eXBlIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfa29vbWFwIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZXZzX2Vzc2QyIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZXZzX3Bvb2xfY2EiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9wZWRhX3NjaF9jYSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2FfY24tc291dGh3ZXN0LTJiIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfaHdjcGgiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3Nfb2ZmbGluZV9kaXNrXzQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9od2RldiIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX29wX2dhdGVkX2NiaF92b2x1bWUiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9zbW5fd2VsaW5rcmVkIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZGF0YWFydHNpbnNpZ2h0IiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfaHZfdmVuZG9yIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYV9jbi1ub3J0aC00ZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3dhZl9jbWMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9hX2NuLW5vcnRoLTRkIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfZWNzX2FjNyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2VwcyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2NzYnNfcmVzdG9yZV9hbGwiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9vcmdhbml6YXRpb25zX2ludGwiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9pYW1faWRlbnRpdHljZW50ZXJfY24iLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9hX2NuLW5vcnRoLTRmIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfb2EiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9zZnNfbGlmZWN5Y2xlIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfb3BfZ2F0ZWRfcm91bmR0YWJsZSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2FfYXAtc291dGhlYXN0LTFlIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfYV9ydS1tb3Njb3ctMWIiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9hX2FwLXNvdXRoZWFzdC0xZCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX2FfYXAtc291dGhlYXN0LTFmIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfc21uX2FwcGxpY2F0aW9uIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfY3NlX2dhdGV3YXkiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9zbnQ5Ymk2bCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX0lQRENlbnRlcl9DQV8yMDIzMDgzMCIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3JhbSIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX29yZ2FuaXphdGlvbnMiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NfZ3B1X2c1ciIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX29wX2dhdGVkX21lc3NhZ2VvdmVyNWciLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9lY3NfcmkiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9tZ2MiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9zbnQ5YiIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX3VudmVyaWZpZWQiLCJpZCI6IjAifSx7Im5hbWUiOiJvcF9nYXRlZF9hX3J1LW5vcnRod2VzdC0yYyIsImlkIjoiMCJ9LHsibmFtZSI6Im9wX2dhdGVkX3JhbV9pbnRsIiwiaWQiOiIwIn0seyJuYW1lIjoib3BfZ2F0ZWRfaWVmX3BsYXRpbnVtIiwiaWQiOiIwIn1dLCJwcm9qZWN0Ijp7ImRvbWFpbiI6eyJ4ZG9tYWluX3R5cGUiOiJIV0NfSEsiLCJuYW1lIjoiaHdjNjY5MTQwMjciLCJpZCI6ImFjNmE1YmNjMDVlMTQxMzdhYmNjMWNhOWRhMDZkNzQ0IiwieGRvbWFpbl9pZCI6IjllMzg1NzlkZWMwNjRjMmI5NzE0ZmQzOWQ3OTQ3NTQxIn0sIm5hbWUiOiJhcC1zb3V0aGVhc3QtMSIsImlkIjoiMmI1ODUxODFiMDRiNGI4MWE2OWE4OTRmNjg5ZmJiYjAifSwiaXNzdWVkX2F0IjoiMjAyMy0xMC0xMVQwNzo0NDozOS4zMjYwMDBaIiwidXNlciI6eyJkb21haW4iOnsieGRvbWFpbl90eXBlIjoiSFdDX0hLIiwibmFtZSI6Imh3YzY2OTE0MDI3IiwiaWQiOiJhYzZhNWJjYzA1ZTE0MTM3YWJjYzFjYTlkYTA2ZDc0NCIsInhkb21haW5faWQiOiI5ZTM4NTc5ZGVjMDY0YzJiOTcxNGZkMzlkNzk0NzU0MSJ9LCJuYW1lIjoiaHdjNjY5MTQwMjciLCJwYXNzd29yZF9leHBpcmVzX2F0IjoiIiwiaWQiOiIyNzE3ZDdiOTkyYTc0ZGEyYmYzODRlMTIzYWQ3ZmZlYyJ9fX0xggHBMIIBvQIBATCBlzCBiTELMAkGA1UEBhMCQ04xEjAQBgNVBAgMCUd1YW5nRG9uZzERMA8GA1UEBwwIU2hlblpoZW4xLjAsBgNVBAoMJUh1YXdlaSBTb2Z0d2FyZSBUZWNobm9sb2dpZXMgQ28uLCBMdGQxDjAMBgNVBAsMBUNsb3VkMRMwEQYDVQQDDApjYS5pYW0ucGtpAgkA3LMrXRBhahAwCwYJYIZIAWUDBAIBMA0GCSqGSIb3DQEBAQUABIIBABbVVXLQfmUT3yhRY+l-lhRty3KPCManbWSf9TElK0w8j7jWW6m5EBDGHytGCIWaYb4piOHJt6ZUjol9atsaXYVr0g3ZL4zK1-48Ql8lSVKFTvgzUtCryZQlG-kBbdsjwZgfbF-RbLtcv3oHpHF3SZT5yg6ikwX-rcAeViY+D8Hz8ivWuO9E8Xn3e55jKX7UAq8avnCIwux3hemJkUOqV6dAMXtIX96cKCZU3NI6iMDzsnq4C2oo1YVNWN5vtY2Nw64CjpWLrsYZaiY40qopjvnq6PTKTgDB+NoHMe+02kT264XF+Mx2TCbdTaHqoR7EC2Omwg+fOrWxyXggAkeLBDo=";
    private BucketManager(){}
    public static BucketManager getInstance(){
        if(bucketManagerInstance == null){
            bucketManagerInstance = new BucketManager();
        }
        return bucketManagerInstance;
    }
    public void initiliaze(){
        this.auth = new BasicCredentials().withAk(AK).withSk(SK);
        this.obsClient = ObsClient.newBuilder().withCredential(auth).withRegion(ObsRegion.valueOf("ap-southeast-1")).build();

    }
    public String uploadFile(byte[] multipartFile,String user_uid) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        if (multipartFile != null){
            RequestBody body = RequestBody.create(mediaType, multipartFile);
            Request request = new Request.Builder()
                    .url("http://maraton.obs.ap-southeast-1.myhuaweicloud.com/"+user_uid+".jpg")
                    .method("PUT", body)
                    .addHeader("X-Subject-Token", header)
                    .build();
            Response response = client.newCall(request).execute();
            return "http://maraton.obs.ap-southeast-1.myhuaweicloud.com/"+user_uid+".jpg";
        }
        else{
            return null;
        }
    }
    public byte[] readFile(String url) throws IOException {
        System.out.println(url);
        if (!url.isEmpty()){
            System.out.println("EMPTY DEGIL URL:" + url);
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = null;
            Request request = new Request.Builder()
                    .url(url)
                    .method("GET",body)
                    .addHeader("X-Subject-Token", header)
                    .build();
            Response response = client.newCall(request).execute();

            return null;
        }
        return null;
    }
}
