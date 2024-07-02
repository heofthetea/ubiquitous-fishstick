package stark.prm.project.data;

import java.util.UUID;
import java.lang.IllegalArgumentException;

public class Note {
    protected final UUID id;
    protected String description;
    protected Lecture lecture;


    public Note(String description, Lecture lecture) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.lecture = lecture;
    }

    /**
     * !! This should only ever be used when loading the Database from files. Otherwise, have ids be generated!
     * @param id
     * @param description
     * @param lecture
     */
    public Note(UUID id, String description, Lecture lecture) {
        this.id = id;
        this.description = description;
        this.lecture = lecture;
    }



    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Lecture getLecture() {
        return lecture;
    }

    /**
     * @return null if lecture does not exist, else the lecture's module
     */
    public Module getModule() {
        return (lecture == null) ? null : lecture.getModule();
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

}
