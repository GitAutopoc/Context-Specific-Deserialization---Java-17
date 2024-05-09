package com.object.assessment;

import java.io.*;

class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{name='" + name + "', age=" + age + '}';
    }
}

public class EmployeeSerialization {
    public static void main(String[] args) {
        Employee emp = new Employee("John Doe", 30);

        // Serialization
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("employee.dat"))) {
            out.writeObject(emp);
            System.out.println("Employee object serialized successfully.");
        } catch (IOException e) {
            System.err.println("Error during serialization: " + e);
        }

        // Setting up the filter
        ObjectInputFilter filter = info -> {
            if (info.serialClass() != null) {
                return (info.serialClass() == Employee.class) ? ObjectInputFilter.Status.ALLOWED : ObjectInputFilter.Status.REJECTED;
            }
            return ObjectInputFilter.Status.UNDECIDED;
        };

        // Deserialization with filter
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("employee.dat"))) {
            in.setObjectInputFilter(filter);
            Employee deserializedEmp = (Employee) in.readObject();
            System.out.println("Deserialized Employee: " + deserializedEmp);
        } catch (IOException e) {
            System.err.println("Error during deserialization: " + e);
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + e);
        }
    }
}
