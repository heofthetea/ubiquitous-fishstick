package stark.prm.project.data;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Database {
    private static Database instance;
    private static HashMap<UUID, Lecture> lectures;
    private static HashMap<UUID, Module> modules;
    // Since Homeworks are Notes, they should also be stored in this Map.
    private static HashMap<UUID, Note> notes;


    private Database() {
        lectures = new HashMap<>();
        modules = new HashMap<>();
        notes = new HashMap<>();
    }

    public static Database getInstance() {
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public void destroy() {
        instance = null;
    }

    //----------------------------------------------------------------------------------------------
    public void add(Note note) {
        notes.put(note.getId(), note);
    }

    public void add(Lecture lecture) {
        lectures.put(lecture.getId(), lecture);
    }

    public void add(Module module) {
        modules.put(module.getId(), module);
    }

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
}
