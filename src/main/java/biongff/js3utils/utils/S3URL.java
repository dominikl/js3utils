package biongff.js3utils.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class S3URL {
    private static final Pattern S3_URL_PATTERN = Pattern.compile(
        "(?<endpoint>^http.?://?[^/]+)/(?<bucket>[^/]+)/(?<path>.+?)(?:/(?<folder>[^/]+))?$"
    );

    private String endpoint;
    private String bucket;
    private String path;
    private String folder;
    
    public S3URL(String url) {
        Matcher matcher = S3_URL_PATTERN.matcher(url);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid S3 URL format: " + url);
        }
        
        this.endpoint = matcher.group("endpoint");
        this.bucket = matcher.group("bucket");
        this.path = matcher.group("path");
        this.folder = matcher.group("folder");
    }

    public String getEndpoint() { return endpoint; }
    public String getBucket() { return bucket; }
    public String getPath() { return path; }
    public String getFolder() { return folder; }
    public String getKey() { return path + "/" + folder; }
}
