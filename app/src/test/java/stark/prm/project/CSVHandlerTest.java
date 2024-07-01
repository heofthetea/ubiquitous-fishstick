package stark.prm.project;

import org.junit.Test;
import org.junit.Assert;

import java.util.Date;

import stark.prm.project.data.Database;
import stark.prm.project.data.Homework;
import stark.prm.project.data.Lecture;
import stark.prm.project.data.Module;
import stark.prm.project.data.Note;

public class CSVHandlerTest {


    /**
     * I'm testing this using my terminal lol
     *
     * What should be returned:
     * cat lectures.csv: 3 entries
     * cat notes.csv: 2 entries
     * cat homeworks.csv: 3 entries
     * cat modules.csv: 1 entry
     *
     */
    @Test
    public void writeLecturesTest() {
        Database db = null;
        try {
            db = Database.getInstance();

            Module tempModule = new Module("Module 1", "Dozent 1");
            db.add(tempModule);
            Lecture tempLecture = new Lecture(tempModule, "Lecture 1");
            db.add(tempLecture);

            db.add(new Lecture(tempModule, "Lecture 2"));
            db.add(new Lecture(tempModule, "Lecture 3"));
            db.commit();


            db.add(new Note("Note 1", tempLecture));
            db.add(new Note("Note 2", tempLecture));
            db.commit();


            db.add(new Homework("Homework 1", tempModule, 1, .5, new Date()));
            db.add(new Homework("Homework 2", tempLecture, 1, .5, new Date()));
            db.add(new Homework("Homework with 2 null references", (Module) null, 1, .5, new Date()));

            db.commit();

            db.destroy();

            //--------------------------------------------------------------------------------------------------------------------------
            // Test the reading portion
            db = Database.getInstance();
            db.load();

            Assert.assertEquals(3, db.getLectures().size());
            Assert.assertEquals(1, db.getModules().size());
            Assert.assertEquals(2 + 3, db.getNotes().size()); // need to account for merging of homeworks and notes

            // Test if number of individual homeworks/notes matches
            Assert.assertEquals(
                    3,
                    db.getNotes().values().stream()
                            .filter(n -> n instanceof Homework)
                            .count()
            );
            Assert.assertEquals(
                    2,
                    db.getNotes().values().stream()
                            .filter(n -> !(n instanceof Homework))
                            .count()
            );

        } catch (Exception e) {
            Assert.fail(); // fail the test if any errors occur
        } finally {
            if (db != null)
                db.destroy();
        }





    }
}
