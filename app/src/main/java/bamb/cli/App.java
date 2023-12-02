package bamb.cli;

import bamb.cli.commands.NewCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "bamb", description = "The Bamb CLI helps you to initialize compile your Ebook application. Making your work easier")
public class App {
    public static void main(String[] args) {
        NewCommand newCommand = new NewCommand();

        CommandLine commandLine = new CommandLine(new App());
        commandLine.addSubcommand("new", newCommand);

        commandLine.execute(args);
    }
}
