package stark.prm.project.data;

import android.provider.ContactsContract;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class Module {
    private final UUID id;
    private String name;
    private String dozent; // I'll keep this one as only the name, as I don't want to bloat this even more
    //TODO add List of Homework?

    public Module(String name, String dozent) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.dozent = dozent;
    }

    /**
     * Checks 2 modules for equality based on their id, name and dozent.
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Module)) return false;
        Module module = (Module) o;
        return Objects.equals(id, module.id) && Objects.equals(name, module.name) && Objects.equals(dozent, module.dozent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dozent);
    }


    public List<Note> getNotes() {
        return Database.getInstance().getNotes().values().stream()
                .filter(h -> h.getModule().equals(this))
                .collect(Collectors.toList());
    }


    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDozent() {
        return dozent;
    }

    public void setDozent(String dozent) {
        this.dozent = dozent;
    }
}
