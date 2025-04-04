package biongff.js3utils;

import biongff.js3utils.cmds.Download;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Spec;

@Command(
    name = "js3utils",
    description = "JS3Utils - Java S3 Utilities",
    version = "0.1.1-SNAPSHOT",
    mixinStandardHelpOptions = true
)
public class Cli implements Runnable {
    @Spec 
    CommandSpec spec;

    @Option(names = {"-q", "--quiet"}, description = "Quiet mode")
    public boolean quiet;

    public static void main(String[] args) {
        CommandLine cli = new CommandLine(new Cli())
        .addSubcommand("download", new Download());

        if (args.length == 0) {
            cli.usage(System.out);
            System.exit(1);
        }
        else {
            int exitCode = cli.execute(args);
        System.exit(exitCode);
        }
    }

    @Override
    public void run() {
        throw new ParameterException(spec.commandLine(), "Specify a subcommand");
    }
}
