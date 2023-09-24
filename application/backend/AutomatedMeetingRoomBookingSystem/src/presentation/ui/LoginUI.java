package presentation.ui;

import beans.User;
import service.LoginService;
import service.LoginServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginUI {
    private static final BufferedReader bufferedReader;
    private static final LoginService loginService;

    static{
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        loginService = new LoginServiceImpl();
    }

    public static User performLoginAndGetUser() throws IOException {
        System.out.println("Login");
        System.out.println("--------------------------------------");
        System.out.print("User ID : ");
        int userId = Integer.parseInt(bufferedReader.readLine());

        System.out.print("User Email : ");
        String userEmail = bufferedReader.readLine();

        /// if user credentials are okay then a non-null user object will be returned.
        return loginService.login(userId,userEmail);
    }
}
