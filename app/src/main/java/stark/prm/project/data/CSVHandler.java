package stark.prm.project.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class CSVHandler {
    /**
     * Path to the raw files, where the csv files are stored.
     * Android Studio (or Gradle or idk what is to blame for this) does an absolute
     * fascinatingly horrible job at organizing resource.
     */
    private static final String RAW_PATH = "src/main/res/raw/";
    private static final String DATE_PATTERN = "dd-MM-yyyy";

    //----------------------------------------------------------------------------------------------------
    //WRITING to CSV

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

    public static void writeHomeworks(Map<UUID, Homework> homeworks) {
        try (PrintWriter writer = new PrintWriter(RAW_PATH + "homeworks.csv")) {
            // Write the header line
            writer.println("id,description,moduleID,pageNumber,progress,dueDate,lectureID");

            // Iterate over the homeworks map
            for (Homework homework : homeworks.values()) {
                // Append the id, description, moduleID, pageNumber, progress, dueDate, and lectureID of the Homework object
                SimpleDateFormat theFuckingFormatter = new SimpleDateFormat(DATE_PATTERN);
                String dueDate = theFuckingFormatter.format(homework.getDueDate());

                String line = "\"" +
                        homework.getId().toString() +
                        "\",\"" +
                        homework.getDescription() +
                        "\",\"" +
                        ((homework.getModule() != null) ? homework.getModule().getId().toString() : "") +
                        "\",\"" +
                        homework.getPageNumber() +
                        "\",\"" +
                        homework.getProgress() +
                        "\",\"" +
                        dueDate +
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

    //----------------------------------------------------------------------------------------------------
    //READING from CSV


    public static HashMap<UUID, Module> readModules(Database context) {
        HashMap<UUID, Module> modules = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(RAW_PATH + "modules.csv"))) {
            // Read the header line
            String line = reader.readLine(); //TODO is header check necessary?

            // While there are more lines in the CSV file
            while ((line = reader.readLine()) != null) {
                // Split the line into an array of strings
                String[] data = line.split("\",\""); // Split at commata, and remove all but 2 quotes as a byproduct

                // Create a new Module object
                Module module = new Module(
                        UUID.fromString(data[0].substring(
                                1)),
                        data[1],
                        data[2].substring(0, data[2].length() - 1)
                );

                // Add the Module object to the modules map
                modules.put(module.getId(), module);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the CSV file.");
            e.printStackTrace();
        }
        return modules;
    }

    /**
     * @param context A {@link Database} <i>Instance</i>, meaning the Database has to be created first
     * @return
     */
    public static HashMap<UUID, Lecture> readLectures(Database context) {
        HashMap<UUID, Lecture> lectures = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(RAW_PATH + "lectures.csv"))) {
            // Read the header line
            String line = reader.readLine(); //TODO is header check necessary?

            // While there are more lines in the CSV file
            while ((line = reader.readLine()) != null) {
                // Split the line into an array of strings
                String[] data = line.split("\",\"");

                // compare against the required length of a UUID string
                Module module = (data[2].length() < 36) ? null : context.getModules().get(UUID.fromString(data[2].substring(0, data[2].length() - 1)));

                // Create a new Lecture object
                // The first character of data[0] and the last of data[-1] are quotes from the csv file creation, meaning those have to be removed using substring()
                Lecture lecture = new Lecture(UUID.fromString(data[0].substring(1)), module, data[1]);

                // Add the Lecture object to the lectures map
                lectures.put(lecture.getId(), lecture);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the CSV file.");
            e.printStackTrace();
        }

        return lectures;
    }

    public static HashMap<UUID, Note> readNotes(Database context) {
        HashMap<UUID, Note> notes = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(RAW_PATH + "notes.csv"))) {
            // Read the header line
            String line = reader.readLine(); //TODO is header check necessary?

            // While there are more lines in the CSV file
            while ((line = reader.readLine()) != null) {
                // Split the line into an array of strings
                String[] data = line.split("\",\"");
                // make lecture null if it is an empty string
                Lecture lecture = (data[2].length() < 36) ? null : context.getLectures().get(UUID.fromString(data[2].substring(0, data[2].length() - 1)));

                // Create a new Note object
                // The first character of data[0] and the last of data[-1] are quotes from the csv file creation, meaning those have to be removed using substring()
                Note note = new Note(UUID.fromString(data[0].substring(1)), data[1], lecture);

                // Add the Note object to the notes map
                notes.put(note.getId(), note);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the CSV file.");
            e.printStackTrace();
        }
        return notes;
    }

    public static HashMap<UUID, Homework> readHomeworks(Database context) {
        HashMap<UUID, Homework> homeworks = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(RAW_PATH + "homeworks.csv"))) {
            // Read the header line
            String line = reader.readLine(); //TODO is header check necessary?

            SimpleDateFormat theFuckingFormatter = new SimpleDateFormat(DATE_PATTERN);


            // While there are more lines in the CSV file
            while ((line = reader.readLine()) != null) {
                // Split the line into an array of strings
                String[] data = line.split("\",\"");

                // need to check against 1, not 0, because it will contain
                Module module = (data[2].length() < 36) ? null : context.getModules().get(UUID.fromString(data[2]));
                Lecture lecture = (data[6].length() < 36) ? null: context.getLectures().get(UUID.fromString(data[6].substring(0, data[6].length() - 1)));

                // Create a new Homework object
                // The first character of data[0] and the last of data[-1] are quotes from the csv file creation, meaning those have to be removed using substring()
                Homework homework = new Homework(
                        UUID.fromString(data[0].substring(1)),
                        data[1],
                        lecture,
                        Integer.parseInt(data[3]),
                        Double.parseDouble(data[4]),
                        theFuckingFormatter.parse(data[5]),
                        module
                );

                // Add the Homework object to the homeworks map
                homeworks.put(homework.getId(), homework);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading from the CSV file.");
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return homeworks;
    }
}
