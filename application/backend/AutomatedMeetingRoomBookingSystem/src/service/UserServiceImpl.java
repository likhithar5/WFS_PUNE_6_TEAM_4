package services;

import java.util.List;

import beans.User;
import dao.UserDao;
import dao.UserDaoImpl;
import exceptions.UserAlreadyExistException;
import exceptions.UserNotFoundException;

public class UserServiceImpl implements UserService{

	private UserDao userdao;
	public UserServiceImpl() {
		userdao = new UserDaoImpl();
	}
	
	@Override
	public List<User> getAllUser() {
		 return userdao.getAllUser();
	}

	@Override
	public boolean addUser(User u) {
		try {
			return userdao.addUser(u);
		} catch (UserAlreadyExistException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public User getUserByIdAndEmail(int user_id, String user_email) {
		try {
			return userdao.getUserByIdAndEmail(user_id, user_email);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getUserCredits(int userId) {
	    return userdao.getUserCredits(userId);
	}

	@Override
	public void setUserCredits(int userId, int credits)  {
		try {
			userdao.setUserCredits(userId, credits);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
	}

}
