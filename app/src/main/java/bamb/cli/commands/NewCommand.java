package bamb.cli.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Help.Ansi;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

import bamb.cli.Metadata;
import bamb.cli.utils.ISBN;

@Command(name = "new", description = "Create a new Bamb Application")
public final class NewCommand implements Runnable {

    @Parameters(index = "0", description = "Bamb application name", defaultValue = ".")
    private String name;

    private Metadata<ISBN> metadata;

    private void generateMetadataQuestions() {
        try {
            metadata = new Metadata<ISBN>();
            Scanner scanner = new Scanner(System.in);
            LocalDateTime date = LocalDateTime.now();

            metadata.setTitle(this.name);
            metadata.setAuthor("Anonymous");
            metadata.setIdentifier(ISBN.randomISBN13());
            metadata.setDate(date.toString());
            metadata.setLanguage("en");

            Field[] fields = Metadata.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                String fieldValue = (String) field.get(metadata);
            
                System.out.println(fieldName + fieldValue);
                System.out.print("question " + fieldName + ":");
                String clientAnswer = scanner.next();

                if (!clientAnswer.isEmpty()) {
                    field.set(metadata, clientAnswer);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void generateApplication() {
        try {
            File sourcePath = new File("src/main/resources/templates/bamb");
            File destinPath = new File(System.getProperty("user.dir") + "/" + this.name);

            FileUtils.copyDirectory(sourcePath, destinPath);

            File sourceGitignorePath = new File(destinPath, "_gitignore");
            File destinGitignorePath = new File(destinPath, ".gitignore");
            FileUtils.moveFile(sourceGitignorePath, destinGitignorePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Ansi ansi = Ansi.AUTO;
        String helloMessage = "@|bg(yellow) INITIALIZE YOUR BAMB APPLICATION|@";

        File destinPath = new File(System.getProperty("user.dir") + "/" + name);

        try {
            if (destinPath.exists()) {
                throw new IOException("Already exists a dir with name " + name);
            }

            System.out.println(ansi.text(helloMessage));
            generateMetadataQuestions();
            System.out.println("Questions:");
            System.out.println("Title: " + metadata.getTitle());
            System.out.println("Description: " + metadata.getDescription());
            System.out.println("Author: " + metadata.getAuthor());
            System.out.println("Identifier: " + metadata.getIdentifier());
            System.out.println("Date: " + metadata.getDate());
            System.out.println("Language: " + metadata.getLanguage());

            generateApplication();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
