package club.kuzyayo.stars.auth.api.dto;

public enum Role {
    USER(Constants.ROLE_USER),
    ADMIN(Constants.ROLE_ADMIN),
    SYSTEM(Constants.ROLE_SYSTEM);

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static class Constants {
        public static final String ROLE_USER = "ROLE_USER";
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        public static final String ROLE_SYSTEM = "ROLE_SYSTEM";
    }
}
