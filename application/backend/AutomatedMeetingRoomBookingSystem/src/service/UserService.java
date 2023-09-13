package services;

import java.util.List;

import beans.User;
import exceptions.UserNotFoundException;

public interface UserService {
	 List<User> getAllUser();
	 boolean addUser(User u);
	 User getUserByIdAndEmail(int user_id,String user_email);
	 int getUserCredits(int userId);
	 void setUserCredits(int userId,int credits) throws UserNotFoundException;
}
