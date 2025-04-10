package biongff.js3utils.cmds;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import biongff.js3utils.Cli;

public class DownloadTest {

    @Test
    public void testDownloadCommand(@TempDir Path tempDir) {
        Download download = new Download();
        Cli parent = new Cli();
        parent.quiet = true;
        
        try {
            java.lang.reflect.Field parentField = Download.class.getDeclaredField("parent");
            parentField.setAccessible(true);
            parentField.set(download, parent);

            java.lang.reflect.Field urlField = Download.class.getDeclaredField("url");
            urlField.setAccessible(true);
            urlField.set(download, "https://minio-dev.openmicroscopy.org/idr/Testing/test.ome.zarr");

            java.lang.reflect.Field destinationField = Download.class.getDeclaredField("destinationPath");
            destinationField.setAccessible(true);
            destinationField.set(download, tempDir.toString());

            Integer result = download.call();
            assertEquals(0, result, "Download should complete successfully");
            assertTrue(tempDir.resolve("test.ome.zarr/.zattrs").toFile().exists(), 
                "Downloaded folder should exist");

        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        }
    }

    @Test
    public void testDownloadInvalidUrl(@TempDir Path tempDir) {
        Download download = new Download();
        Cli parent = new Cli();
        parent.quiet = true;

        try {
            java.lang.reflect.Field parentField = Download.class.getDeclaredField("parent");
            parentField.setAccessible(true);
            parentField.set(download, parent);

            java.lang.reflect.Field urlField = Download.class.getDeclaredField("url");
            urlField.setAccessible(true);
            urlField.set(download, "https://invalid-url/nonexistent");

            java.lang.reflect.Field destinationField = Download.class.getDeclaredField("destinationPath");
            destinationField.setAccessible(true);
            destinationField.set(download, tempDir.toString());

            Integer result = download.call();
            assertEquals(1, result, "Download should fail with invalid URL");

        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Invalid S3 URL format"), 
                "Exception should indicate invalid URL format");
        } catch (Exception e) {
            fail("Test failed with unexpected exception: " + e.getMessage());
        }
    }
}
