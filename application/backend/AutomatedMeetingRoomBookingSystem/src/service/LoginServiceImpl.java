package service;

import beans.User;
import dao.UserDao;
import dao.UserDaoImpl;

public class LoginServiceImpl implements LoginService{

	private UserDao userdao;
	public LoginServiceImpl() {
		userdao = new UserDaoImpl();
	}
	@Override
	public User login(int id, String email) {
		User user = null;
		try {
		   user= userdao.getUserByIdAndEmail(id, email);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return user;      
	}

}
