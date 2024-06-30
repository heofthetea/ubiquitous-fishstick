package stark.prm.project.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import stark.prm.project.R;

public class CSVHandler {
    /**
     * Path to the raw files, where the csv files are stored.
     * Android Studio (or Gradle or idk what is to blame for this) does an absolute
     * fascinatingly horrible job at organizing resource.
     */
    private static final String RAW_PATH = "src/main/res/raw/";

    /**
     * Writes a given Map of {@link Lecture} objects to a csv file.
     * Overwrites any data that may have previously been stored in that file.
     * <p>
     * Note: Object relations to {@link Module} are resolved using the respective module's id.
     *
     * @param lectures Map of {@link Lecture} objects
     */
    public static void writeLectures(HashMap<UUID, Lecture> lectures) {
        try (PrintWriter writer = new PrintWriter(RAW_PATH + "lectures.csv")) {
            // Write the header line
            writer.println("id,topic,moduleID");

            for (Lecture lecture : lectures.values()) {
                String line = "\"" +
                        lecture.getId().toString() +
                        "\",\"" +
                        lecture.getTopic() +
                        "\",\"" +
                        lecture.getModule().getId().toString()
                        + "\"";

                writer.println(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the CSV file.");
            e.printStackTrace();
        }
    }

    public static void writeModules(HashMap<UUID, Module> modules) {
        try (PrintWriter writer = new PrintWriter(RAW_PATH + "modules.csv")) {
            // Write the header line
            writer.println("id,name,dozent");

            // Iterate over the modules map
            for (Module module : modules.values()) {
                String line = "\"" +
                        module.getId().toString() +
                        "\",\"" +
                        module.getName() +
                        "\",\"" +
                        module.getDozent()
                        + "\"";

                // Write the StringBuilder to the CSV file
                writer.println(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the CSV file.");
            e.printStackTrace();
        }
    }

    public static void writeNotes(Map<UUID, Note> notes) {
        try (PrintWriter writer = new PrintWriter(RAW_PATH + "notes.csv")) {
            // Write the header line
            writer.println("id,description,lectureID");

            // Iterate over the notes map
            for (Note note : notes.values()) {
                // Append the id, description, and lectureID of the Note object
                String line = "\"" +
                        note.getId().toString() +
                        "\",\"" +
                        note.getDescription() +
                        "\",\"" +
                        ((note.getLecture() != null) ? note.getLecture().getId().toString() : "")
                        + "\"";

                // Write the StringBuilder to the CSV file
                writer.println(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the CSV file.");
            e.printStackTrace();
        }
    }

        public static void writeHomeworks (Map < UUID, Homework> homeworks){
            try (PrintWriter writer = new PrintWriter(RAW_PATH + "homeworks.csv")) {
                // Write the header line
                writer.println("id,description,moduleID,pageNumber,progress,dueDate,lectureID");

                // Iterate over the homeworks map
                for (Homework homework : homeworks.values()) {
                    // Append the id, description, moduleID, pageNumber, progress, dueDate, and lectureID of the Homework object
                    String line = "\"" +
                            homework.getId().toString() +
                            "\",\"" +
                            homework.getDescription() +
                            "\",\"" +
                            ((homework.getModule() != null) ? homework.getModule().getId().toString() : "" ) +
                            "\",\"" +
                            homework.getPageNumber() +
                            "\",\"" +
                            homework.getProgress() +
                            "\",\"" +
                            homework.getDueDate().toString() +
                            "\",\"" +
                            ((homework.getLecture() != null) ? homework.getLecture().getId().toString() : "") // writes nothing if the homework is not assigned a lecture
                            + "\"";

                    writer.println(line);
                }
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the CSV file.");
                e.printStackTrace();
            }
        }
    }
