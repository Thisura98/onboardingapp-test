package lk.test.myapplication.models.login;

public class LoginRequestBody {
    public String Username;
    public String Password;

    public LoginRequestBody(String username, String password){
        Username = username;
        Password = password;
    }
}
