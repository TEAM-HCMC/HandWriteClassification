package ac.kr.inu.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LogFlagTest {

    int flag0 = 0;
    int flag1 = 1;
    int flag2 = 2;

    @Test
    public void 플래그에_해당하는_메세지_찾기() {
        assertThat(LogFlag.findMessageByFlag(flag0)).isEqualTo(LogFlag.NOT_CREATED.getMessage());
        assertThat(LogFlag.findMessageByFlag(flag1)).isEqualTo(LogFlag.CREATING.getMessage());
        assertThat(LogFlag.findMessageByFlag(flag2)).isEqualTo(LogFlag.CREATED.getMessage());
    }
}