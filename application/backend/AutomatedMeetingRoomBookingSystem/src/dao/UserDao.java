package dao;

import beans.User;
import exceptions.UserAlreadyExistException;
import exceptions.UserNotFoundException;

import java.util.List;

public interface UserDao {
	 List<User> getAllUser();
	 boolean addUser(User u) throws UserAlreadyExistException;
	 User getUserByIdAndEmail(int user_id,String user_email) throws UserNotFoundException;
	 int getUserCredits(int userId);
	 void setUserCredits(int userId,int credits) throws UserNotFoundException;

	String getUserRole(int userId) throws UserNotFoundException;
}
