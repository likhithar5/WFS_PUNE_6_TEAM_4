package dao;

import enums.DatabaseProduct;
import enums.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import exceptions.UserAlreadyExistException;
import exceptions.UserNotFoundException;
import persistence.database.*;
//import persistence.database.types.*;
import beans.User;
//import exceptions.UserAlreadyExistException;
//import exceptions.UserNotFoundException;

public class UserDaoImpl implements UserDao {

	
	private static Connection conn;
	Database database = null;
	public UserDaoImpl() {
		database = DatabaseFactory.getDatabaseOf(DatabaseProduct.MY_SQL);
		conn = database.getConnection();
	}

	
	private static final String SELECT_USER = "select * from users";
	private static final String INSERT_USER = "insert into users (id,name,email,phone,role,last_logged_in) values (?,?,?,?,?,?);";
	private static final String SELECT_USER_BY_ID_EMAIL = "select * from users where id=? and email=?;";
	private static final String SELECT_USER_BY_ID =  "select * from users where id=?;";
	private static final String UPDATE_CREDITS = "UPDATE credits SET meeting_credits = ? WHERE id = ?";
	

	@Override
	public List<User> getAllUser() {
       
        List<User> users = new ArrayList<>();
        try {
        	PreparedStatement ps = conn.prepareStatement(SELECT_USER);
        	ResultSet rs = ps.executeQuery();
        	while(rs.next()) {
        		User user = new User();
        		user.setUserId(rs.getInt(1));
        		user.setName(rs.getString(2));
        		user.setEmail(rs.getString(3));
        		user.setPhone(rs.getString(4));
        		user.setCredits(rs.getInt(5));
        		user.setRole(Role.valueOf(rs.getString(6)));
        		//user.setLastLoggedIn(rs.getDat);
        		users.add(user);
        	}
        }catch(Exception e) {
			Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
			throw new RuntimeException(e);
        }
		return users;
	}

	@Override
	public boolean addUser(User u) /*throws UserAlreadyExistException*/{
	
			try {
				PreparedStatement statement = conn.prepareStatement(INSERT_USER);
				statement.setInt(1, u.getUserId());
				statement.setString(2, u.getName());
				statement.setString(3, u.getEmail());
				statement.setString(4, u.getPhone());
				statement.setInt(5, u.getCredits());
				statement.setString(6, u.getRole().name());
				
				LocalDateTime lastLoggedIn = u.getLastLoggedIn();
				System.out.println(lastLoggedIn);
			    if (lastLoggedIn != null) {
			        // Format LocalDateTime as a string in the desired format
			        String formattedLastLoggedIn = lastLoggedIn.toString(); // Adjust the format if needed
			        statement.setString(7, formattedLastLoggedIn);
			    } else {
			        statement.setNull(7, Types.VARCHAR); // Set it as NULL in the database
			    }


				int resultSet = statement.executeUpdate();
				if (resultSet == 0) {
					throw new UserAlreadyExistException("User already exist");
				}

			} catch (UserAlreadyExistException | SQLException e) {
				Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
				throw new RuntimeException(e);
			}
		return true;
	}

	@Override
	public User getUserByIdAndEmail(int user_id, String user_email) /*throws UserNotFoundException*/{
		
		    User user = null;
			try {
				PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_ID_EMAIL);
				ps.setInt(1,user_id);
				ps.setString(2,user_email);
				
				ResultSet rs = ps.executeQuery();
				if(rs != null) {
					rs.next();
					user = new User();
					user.setUserId(rs.getInt(1));
					user.setName(rs.getString(2));
					user.setEmail(rs.getString(3));
					user.setPhone(rs.getString(4));
					user.setCredits(rs.getInt(5));
					user.setRole(Role.valueOf(rs.getString(6)));
					//set date
				}
				else {
					Logger.getLogger(this.getClass().getName()).severe("User Not Found >> user_id : " + user_id);
					throw new UserNotFoundException("User Not Found");
				}
			}catch(Exception e) {
				Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
				throw new RuntimeException(e);
			}
			
      return user;
	}

	@Override
	public int getUserCredits(int userId) {
		int credits = 0;
		PreparedStatement ps = null;
		try {
			 ps = conn.prepareStatement(SELECT_USER_BY_ID);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				credits = rs.getInt(1);
			}else {
				Logger.getLogger(this.getClass().getName()).severe("User Not Found with id :"+userId);
				throw new UserNotFoundException("User Not Found");
			}
		}catch(Exception e) {
			Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
		}
		return credits;
	}

	@Override
	public void setUserCredits(int userId, int credits)/* throws UserNotFoundException*/ {
		PreparedStatement ps;

		try {
			

			// Check if the user exists before updating credits
			PreparedStatement checkUserPs = conn.prepareStatement(SELECT_USER_BY_ID);
			checkUserPs.setInt(1, userId);
			ResultSet resultSet = checkUserPs.executeQuery();

			if (!resultSet.next()) {
				// The user does not exist, throw a custom exception
				throw new UserNotFoundException("User with ID " + userId + " not found");
			}

			ps = conn.prepareStatement(UPDATE_CREDITS);
			ps.setInt(1, credits);
			ps.setInt(2, userId);
			ps.executeUpdate();
		} catch (UserNotFoundException | SQLException e) {
			Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
		}

	}

}
