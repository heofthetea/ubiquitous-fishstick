package stark.prm.project.data;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Homework extends Note {

    private Integer pageNumber;
    private Double progress; // progress of homework in %
    private Date dueDate;
    private Module module; // always has to match up with the module of inherited field 'lecture', if said field is not null

    public Homework(String description, Module module, Integer pageNumber, Double progress, Date dueDate) {
        super(description, null);
        this.pageNumber = pageNumber;
        this.progress = progress;
        this.dueDate = dueDate;
        this.module = module;
    }

    /**
     * The module for this homework is determined implicitly by the passed lecture.
     * Hence, this constructor does not accept the passed lecture to be null.
     * If it is, use the other Constructor {@link Homework#Homework(String, Module, Integer, Double, Date)} instead.
     *
     * @param description description of the homework
     * @param lecture    lecture the homework is associated with
     * @param pageNumber page number of the homework
     * @param progress  progress of the homework in %
     * @param dueDate due date of the homework 🖕 @niko
     */
    public Homework(String description, Lecture lecture, Integer pageNumber, Double progress, Date dueDate) {
        super(description, lecture);
        this.pageNumber = pageNumber;
        this.progress = progress;
        this.dueDate = dueDate;
        if (lecture == null) throw new IllegalArgumentException("lecture cannot be null");
        this.module = lecture.getModule();
    }

    /**
     * !! This should only ever be used when loading the Database from files. Otherwise, have ids be generated!
     *
     * @param id         the id of the homework
     * @param description the description of the homework
     * @param lecture   the lecture the homework is associated with
     * @param pageNumber the page number of the homework
     * @param progress  the progress of the homework
     * @param dueDate  the due date of the homework
     * @param module   the module the homework is associated with 🖕 @niko
     */
    public Homework(UUID id, String description, Lecture lecture, Integer pageNumber, Double progress, Date dueDate, Module module) {
        super(id, description, lecture);
        this.pageNumber = pageNumber;
        this.progress = progress;
        this.dueDate = dueDate;
        this.module = module;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Homework)) return false;
        Homework homework = (Homework) o;
        return description.equals(homework.getDescription()) && dueDate.equals(homework.getDueDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, dueDate);
    }

    //----------------------------------------------------------------------------------------------


    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        if (this.getLecture() != null) {
            if (!this.getLecture().getModule().equals(module))
                throw new IllegalArgumentException("provided module does not match module of lecture");
        }
        this.module = module;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
