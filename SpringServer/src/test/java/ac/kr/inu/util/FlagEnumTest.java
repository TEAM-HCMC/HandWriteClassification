package ac.kr.inu.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FlagEnumTest {

    int flag0 = 0;
    int flag1 = 1;
    int flag2 = 2;

    @Test
    public void 플래그에_해당하는_메세지_찾기() {
        assertThat(FlagEnum.findMessageByFlag(flag0)).isEqualTo(FlagEnum.NOT_CREATED.getMessage());
        assertThat(FlagEnum.findMessageByFlag(flag1)).isEqualTo(FlagEnum.CREATING.getMessage());
        assertThat(FlagEnum.findMessageByFlag(flag2)).isEqualTo(FlagEnum.CREATED.getMessage());
    }
}