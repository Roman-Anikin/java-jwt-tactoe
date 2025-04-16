package security.model;

public class JwtResponse {

    private final String type;
    private String accessToken;
    private String refreshToken;

    public JwtResponse(String accessToken, String refreshToken) {
        type = "Bearer";
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getType() {
        return type;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
