package ba.etf.musicportal;

/**
 * Created by luka454 on 17/11/2015.
 */
public class SessionManager {


    public TokenModel login(String username, String password){
        //TODO: Call login and remember token
        currentUser = new TokenModel("blablabla", "luka454", "luka454@hotmail.com");
        return currentUser;
    }

    public boolean isLoggedIn(){
        //TODO: check token expiration;
        return currentUser == null;
    }

    public void logout() {
        //TODO: Call logout on server
        currentUser = null;
    }

    public static String getCurrentUser(){
        if(currentUser == null)
            return null;
        return currentUser == null ? null : currentUser.username;
    }

    private static TokenModel currentUser = null;

    public class TokenModel {
        String token;
        String username;
        String email;
        //TODO: maybe to add token expiration;
        public TokenModel(String token, String username, String email){
            this.token = token;
            this.username = username;
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
