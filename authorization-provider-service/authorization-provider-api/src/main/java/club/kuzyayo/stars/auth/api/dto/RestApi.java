package club.kuzyayo.stars.auth.api.dto;

public interface RestApi {

    String USER_INFO_BASE_URL = "/user";
    String SAVE_EXTERNAL_USER_RELATIVE_PATH = "/external";
    String SAVE_EXTERNAL_USER_PATH = USER_INFO_BASE_URL + SAVE_EXTERNAL_USER_RELATIVE_PATH;
}
