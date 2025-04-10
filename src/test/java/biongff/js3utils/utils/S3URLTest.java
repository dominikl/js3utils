package biongff.js3utils.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class S3URLTest {

    @Test
    public void testValidS3URLParsing() {
        String url = "https://s3.amazonaws.com/my-bucket/path/to/file.txt";
        S3URL s3url = new S3URL(url);
        
        assertEquals("https://s3.amazonaws.com", s3url.getEndpoint());
        assertEquals("my-bucket", s3url.getBucket());
        assertEquals("path/to", s3url.getPath());
        assertEquals("file.txt", s3url.getFolder());
        assertEquals("path/to/file.txt", s3url.getKey());
    }

    @Test
    public void testURLWithTrailingSlash() {
        String url = "https://s3.amazonaws.com/my-bucket/path/to/file.txt/";
        S3URL s3url = new S3URL(url);
        
        assertEquals("https://s3.amazonaws.com", s3url.getEndpoint());
        assertEquals("my-bucket", s3url.getBucket());
        assertEquals("path/to", s3url.getPath());
        assertEquals("file.txt", s3url.getFolder());
    }

    @Test
    public void testURLWithSpaces() {
        String url = "  https://s3.amazonaws.com/my-bucket/path/to/file.txt  ";
        S3URL s3url = new S3URL(url);
        
        assertEquals("https://s3.amazonaws.com", s3url.getEndpoint());
        assertEquals("my-bucket", s3url.getBucket());
        assertEquals("path/to", s3url.getPath());
        assertEquals("file.txt", s3url.getFolder());
    }

    @Test
    public void testInvalidS3URL() {
        String[] invalidUrls = {
            "not-a-url",
            "http://",
            "https://s3.amazonaws.com",
            "https://s3.amazonaws.com/",
            "https://s3.amazonaws.com/bucket"
        };

        for (String invalidUrl : invalidUrls) {
            assertThrows(IllegalArgumentException.class, () -> new S3URL(invalidUrl),
                "URL should be invalid: " + invalidUrl);
        }
    }
}
