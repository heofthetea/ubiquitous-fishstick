package stark.prm.project;

import org.junit.Test;
import org.junit.Assert;

import java.util.Date;

import stark.prm.project.data.Database;
import stark.prm.project.data.Homework;
import stark.prm.project.data.Lecture;
import stark.prm.project.data.Module;
import stark.prm.project.data.Note;

public class DatabaseTest {

    /**
     * GIVEN a database
     * WHEN a module, a lecture and a note is added,
     * THEN the database holds one module, one lecture and one note.
     */
    @Test
    public void testDbAdd() {
        Database db = Database.getInstance();
        Module mod1 = new Module("module 1", "Dozent 1");
        Lecture lec1 = new Lecture(mod1, "topic 1");

        db.add(mod1);
        db.add(lec1);
        db.add(new Note("description 1", lec1));


        Assert.assertEquals(1, db.getModules().size());
        Assert.assertEquals(1, db.getLectures().size());
        Assert.assertEquals(1, db.getLectures().size());

        db.destroy();
    }

    /**
     * Yeah I'm sorry this test is complete spaghetti code but I just wanted to get it over with lol
     */
    @Test
    public void addHomeworkTest() {
        Database db = Database.getInstance();
        Module mod1 = new Module("module 1", "Dozent 1");
        Lecture lec1 = new Lecture(mod1, "topic 1");


        final Homework HOMEWORK_WITH_LECTURE = new Homework(
                "homework with lecture",
                lec1,
                "homework 1",
                2,
                .5,
                new Date()
        );
        final Homework HOMEWORK_WITH_MODULE  = new Homework(
                "homework with module",
                mod1,
                "homework 2",
                2,
                .5,
                new Date()
        );
        db.add(HOMEWORK_WITH_LECTURE);
        db.add(HOMEWORK_WITH_MODULE);

        // Test overwriting of module
        boolean errorThrown = false;
        try {
            HOMEWORK_WITH_LECTURE.setModule(
                    new Module(
                            "another module",
                            "another dozent"
                    )
            );
        } catch(IllegalArgumentException e) {
            errorThrown = true;
        }

        Assert.assertTrue(errorThrown);




        Assert.assertEquals(2, db.getNotes().size());
        Assert.assertNotNull(db.getNotes().get(HOMEWORK_WITH_LECTURE.getId())); // check that module is transitively accessible

    }
}
