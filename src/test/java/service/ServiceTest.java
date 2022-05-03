package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.*;

public class ServiceTest {

    public Service service;

    String filenameStudent = "studenti.xml";
    String filenameTema = "teme.xml";
    String filenameNota = "note.xml";

    @Before
    public void init() throws IOException {



        PrintWriter filenameStudentPW = new PrintWriter(filenameStudent);
        PrintWriter filenameTemaPW = new PrintWriter(filenameTema);
        PrintWriter filenameNotaPW = new PrintWriter(filenameNota);
        filenameStudentPW.write("\n");
        filenameTemaPW.write("\n");
        filenameNotaPW.write("\n");

        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);

    }

    @Test
    public void saveStudent() {

        service.saveStudent("paie2807", "Armin", 936);
        Iterable<Student> students = service.findAllStudents();
        assertEquals(students.iterator().next().getID(), "paie2807");
    }

    @Test
    public void saveNotaInvalidNull(){
        Exception thrown = assertThrows(
                Exception.class,
                () -> service.saveTema(null,"homework", 7,2)
        );
        assertTrue(thrown.getMessage().contains("ID invalid! \n"));

    }

    @Test
    public void saveNotaInvalidEmpty() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> service.saveTema("", "homework", 7, 2)
        );
        assertTrue(thrown.getMessage().contains("ID invalid! \n"));
    }

    @Test
    public void saveTemaInvalidDescriptionEmpy() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> service.saveTema("tema1", "", 7, 2)
        );
        assertTrue(thrown.getMessage().contains("Descriere invalida! \n"));
    }

    @Test
    public void saveTemaInvalidDescriptionNull() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> service.saveTema("tema1", null, 7, 2)
        );
        assertTrue(thrown.getMessage().contains("Descriere invalida! \n"));
    }

    @Test
    public void saveTemaInvalidDeadlineLessThan1() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> service.saveTema("tema1", "homework", 0, 2)
        );
        assertTrue(thrown.getMessage().contains("Deadline invalid! \n"));
    }

    @Test
    public void saveTemaInvalidDeadlineGreaterThan14() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> service.saveTema("tema1", "homework", 15, 2)
        );
        assertTrue(thrown.getMessage().contains("Deadline invalid! \n"));
    }

    @Test
    public void saveTemaInvalidDeadlineGSmallerThanStartline() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> service.saveTema("tema1", "homework", 6, 8)
        );
        assertTrue(thrown.getMessage().contains("Invalid deadline startline combo \n"));
    }

    @Test
    public void saveTemaInvalidStartlineLessThan1() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> service.saveTema("tema1", "homework", 5, 0)
        );
        assertTrue(thrown.getMessage().contains("Data de primire invalida! \n"));
    }

    @Test
    public void saveTemaInvalidStartlineGreaterThan14() {
        Exception thrown = assertThrows(
                Exception.class,
                () -> service.saveTema("tema1", "homework", 5, 15)
        );
        assertTrue(thrown.getMessage().contains("Data de primire invalida! \n"));
    }

}