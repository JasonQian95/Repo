package UI;
import java.util.ArrayList;

import Users.Parent;
import Users.Teacher;

/**
 * Created by Jason Qian on 13/11/2016.
 */

public class Classroom {

    private ArrayList<Teacher> teachers;
    private ArrayList<Parent> parentsInClass;
    private ArrayList<Parent> parentsJoined;

    public Classroom (String name, Teacher teacher){
        teachers = new ArrayList<Teacher>();
        teachers.add(teacher);
        parentsInClass = new ArrayList<Parent>();
        parentsJoined = new ArrayList<Parent>();
    }

    public void addTeacher (Teacher teacher){
        teachers.add(teacher);
    }

    public void removeTeacher (Teacher teacher) throws IllegalStateException{
        if (teachers.size() == 1){
            throw new IllegalStateException(); // probably should be a different exception
        }
        teachers.remove(teacher);

    }

    public void addParent (Parent parent){
        parentsInClass.add(parent);
    }

    public void removeParent(Parent parent){
        parentsInClass.remove(parent);
    }

    public void joinParent (Parent parent) throws IllegalArgumentException{
        if (parentsInClass.contains(parent)){
            parentsJoined.add(parent);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public void leaveParent(Parent parent){
        parentsJoined.remove(parent);
    }
}
