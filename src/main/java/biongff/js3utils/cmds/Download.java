package biongff.js3utils.cmds;

import java.util.concurrent.Callable;

import biongff.js3utils.Cli;
import biongff.js3utils.utils.JS3Utils;
import biongff.js3utils.utils.S3URL;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(
    name = "download",
    description = "Download a folder from a public S3-compatible storage"
)
public class Download implements Callable<Integer> {

    @ParentCommand
    private Cli parent;

    @Parameters(index = "0", description = "The full URL (e.g., https://uk1s3.embassy.ebi.ac.uk/idr/zarr/v0.4/idr0062A/6001240.zarr)")
    private String url;

    @Parameters(index = "1", defaultValue = ".", description = "The destination path (default: current directory)")
    private String destinationPath;

    @Override
    public Integer call() {
        S3URL s3Url = new S3URL(url);
        if (!parent.quiet) {
            System.out.println("Endpoint: " + s3Url.getEndpoint());
            System.out.println("Bucket: " + s3Url.getBucket());
            System.out.println("Path: " + s3Url.getPath());
            System.out.println("Folder: " + s3Url.getFolder());
            System.out.println("Key: " + s3Url.getKey());
        }
        try {
            JS3Utils.downloadFolder(s3Url, destinationPath, !parent.quiet);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 1;
        }
        return 0;
    }
}
