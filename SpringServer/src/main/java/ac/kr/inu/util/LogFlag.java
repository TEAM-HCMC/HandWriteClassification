package ac.kr.inu.util;

import java.util.Arrays;

public enum LogFlag {
    NOT_CREATED(0, "없음"),
    CREATING(1, "생성 중"),
    CREATED(2, "생성 완료"),
    UNEXPECTED(500, "ERROR");

    private int flag;
    private String message;

    LogFlag(int flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static String findMessageByFlag(int flag) {
        return Arrays.stream(values())
                .filter(flagEnum -> flagEnum.isFlag(flag))
                .findFirst()
                .map(LogFlag::getMessage)
                .orElse(UNEXPECTED.getMessage());
    }

    private boolean isFlag(int flag) {
        return this.flag == flag;
    }

}
