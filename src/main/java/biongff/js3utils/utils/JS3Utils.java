package biongff.js3utils.utils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.messages.Item;

public class JS3Utils { 

    /**
     * Downloads a folder from an S3 bucket.
     * @param s3Url The S3 URL
     * @param destinationPath The destination path on the local file system
     * @param verbose If true, prints verbose output
     * @throws Exception If an error occurs during the download
     */
    public static void downloadFolder(S3URL s3Url, String destinationPath, boolean verbose) throws Exception {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(s3Url.getEndpoint())
                .build();

        Files.createDirectories(Path.of(destinationPath).resolve(s3Url.getFolder()));

        for (String objectKey : listObjects(s3Url.getEndpoint(), s3Url.getBucket(), s3Url.getKey())) {
            String destKey = objectKey.replace(s3Url.getPath()+"/", "");
            Files.createDirectories(Path.of(destinationPath).resolve(destKey));
            if (verbose) {
                System.out.println(s3Url.getEndpoint() + "/" + s3Url.getBucket() + "/" + objectKey + 
                " -> " + destinationPath + "/" + destKey);   
            }
            try (InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(s3Url.getBucket())
                            .object(objectKey)
                            .build())) {
                Files.copy(stream, Path.of(destinationPath).resolve(destKey), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    /**
     * Lists objects in a bucket that start with the specified prefix.
     * @param endpoint The S3 endpoint URL
     * @param bucket The bucket name
     * @param prefix The prefix to filter objects
     * @return List of object keys that match the prefix
     * @throws Exception If any error occurs during listing
     */
    public static List<String> listObjects(String endpoint, String bucket, String prefix) throws Exception {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .build();

        List<String> objects = new ArrayList<>();
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucket)
                        .prefix(prefix)
                        .recursive(true)
                        .build());

        for (Result<Item> result : results) {
            objects.add(result.get().objectName());
        }

        return objects;
    }
    
}
