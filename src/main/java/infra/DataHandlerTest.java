package infra;

import domain.User;

public class DataHandlerTest {

	public static void main(String[] args) {

		DataHandler dh = new DataHandler();
		//dh.createNewDatabase();
		dh.storeUser(new User("admin", "123"));
		System.out.println(dh.isUserExist("admin"));
		
	}

}
