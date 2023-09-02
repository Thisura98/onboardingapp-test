package lk.test.myapplication.retrofit.login;

public class LoginRequestBody {
    public String username;
    public String password;

    public LoginRequestBody(String username, String password){
        this.username = username;
        this.password = password;
    }
}
