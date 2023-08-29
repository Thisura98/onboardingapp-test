package lk.test.myapplication.models.login;

public class LoginRequestBody {
    public String username;
    public String password;

    public LoginRequestBody(String username, String password){
        this.username = username;
        this.password = password;
    }
}
