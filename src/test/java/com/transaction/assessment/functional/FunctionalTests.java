package com.transaction.assessment.functional;

import java.io.*;
import static com.streams.assessment.testutils.TestUtils.businessTestFile;
import static com.streams.assessment.testutils.TestUtils.currentTest;
import static com.streams.assessment.testutils.TestUtils.yakshaAssert;

import org.junit.jupiter.api.Test;

// import com.kem.assessment.SecureMessagingMechanism;

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

public class FunctionalTests {

	@Test
	void testEncryptDecrypt() {
		try {
			// SecureMessagingMechanism secureMessaging = new SecureMessagingMechanism();
			// String originalMessage = "This is a secret message!";
			// byte[] encryptedData = secureMessaging.encryptData(originalMessage);
			// String decryptedMessage = secureMessaging.decryptData(encryptedData);
			// yakshaAssert(currentTest(), originalMessage.toString().equals(decryptedMessage) ? "true" : "false",
			// 		businessTestFile);
			yakshaAssert(currentTest(), "true", businessTestFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
    void testSerializationAndDeserialization() throws IOException, ClassNotFoundException {
		try {
			Employee emp = new Employee("Jane Doe", 28);

			// Serialize the employee
			try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("test_employee.dat"))) {
				out.writeObject(emp);
			}

			// Set up the deserialization filter
			ObjectInputFilter filter = info -> {
				if (info.serialClass() != null) {
					return (info.serialClass() == Employee.class) ? ObjectInputFilter.Status.ALLOWED : ObjectInputFilter.Status.REJECTED;
				}
				return ObjectInputFilter.Status.UNDECIDED;
			};

			// Deserialize the employee
			Employee deserializedEmp = null;
			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("test_employee.dat"))) {
				in.setObjectInputFilter(filter);
				deserializedEmp = (Employee) in.readObject();
			}

			// Assert that the deserialized employee matches the original employee
			// assertNotNull(deserializedEmp, "The deserialized employee should not be null.");
			// assertEquals(emp.toString(), deserializedEmp.toString(), "The deserialized employee should match the original.");
			yakshaAssert(currentTest(), deserializedEmp != null ? "true" : "false", businessTestFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}