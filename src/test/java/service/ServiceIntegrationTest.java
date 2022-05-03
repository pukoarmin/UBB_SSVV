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

public class ServiceIntegrationTest {

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
    public void addStudent() {

        service.saveStudent("paie2807", "Armin", 936);
        Iterable<Student> students = service.findAllStudents();
        assertEquals(students.iterator().next().getID(), "paie2807");
    }

    @Test
    public void addAssignment() {
        service.saveTema("tema1", "haha", 7, 2);
        Iterable<Tema> teme = service.findAllTeme();
        assertEquals(teme.iterator().next().getID(), "tema1");
    }


    @Test
    public void addGrade(){
        service.saveStudent("paie2807", "Armin", 936);
        service.saveTema("tema1", "haha", 7, 2);
        service.saveNota("paie2807", "tema1", 9, 3, "ok");
        Iterable<Nota> note = service.findAllNote();
        assertEquals("", note.iterator().next().getNota(), 9);
    }



}