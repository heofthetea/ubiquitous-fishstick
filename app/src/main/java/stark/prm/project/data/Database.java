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


    /**
     *
     * @param note stand-alone {@link Note} object with respective changes
     * @return note if replacement was successful, else null
     */
    public Note update(Note note) {
        return notes.replace(note.getId(), note);
    }

    /**
     *
     * @param module stand-alone {@link Module} object with respective changes
     * @return module if replacement was successful, else null
     */
    public Module update(Module module) {
        return modules.replace(module.getId(), module);
    }

    /**
     *
     * @param lecture stand-alone {@link Lecture} object with respective changes
     * @return lecture if replacement was successful, else null
     */
    public Lecture update(Lecture lecture) {
        return lectures.replace(lecture.getId(), lecture);
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
