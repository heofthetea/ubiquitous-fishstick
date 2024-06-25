package stark.prm.project.data;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Database {
    private static Database instance;
    private static HashMap<UUID, Lecture> lectures;
    private static HashMap<UUID, Module> modules;
    private static HashMap<UUID, Note> notes;
    private static HashMap<UUID, Homework> homework; //TODO discuss whether to throw this one out due to inheritance


    private Database() {
        lectures = new HashMap<>();
        modules = new HashMap<>();
        notes = new HashMap<>();
        homework = new HashMap<>();
    }

    public static Database getInstance() {
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public static void destroy() {
        instance = null;
    }

    //----------------------------------------------------------------------------------------------

    //----------------------------------------------------------------------------------------------

    public void setInstance(Database instance) {
        Database.instance = instance;
    }

    public HashMap<UUID, Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(HashMap<UUID, Lecture> lectures) {
        Database.lectures = lectures;
    }

    public HashMap<UUID, Module> getModules() {
        return modules;
    }

    public void setModules(HashMap<UUID, Module> modules) {
        Database.modules = modules;
    }

    public HashMap<UUID, Note> getNotes() {
        return notes;
    }

    public void setNotes(HashMap<UUID, Note> notes) {
        Database.notes = notes;
    }

    public HashMap<UUID, Homework> getHomework() {
        return homework;
    }

    public void setHomework(HashMap<UUID, Homework> homework) {
        Database.homework = homework;
    }
}
