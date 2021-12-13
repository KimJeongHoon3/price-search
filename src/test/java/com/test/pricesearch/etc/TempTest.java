package com.test.pricesearch.etc;

import com.test.pricesearch.domain.ItemType;
import com.test.pricesearch.util.AesUtil;
import org.junit.jupiter.api.Test;

public class TempTest {
    @Test
    void test(){
        ItemType itemType = ItemType.valueOf("채소");
        System.out.println(itemType.getTypeName());

    }

    @Test
    void 암호화() throws Exception {
        String encrypt = AesUtil.encrypt("http://fruit.api.postype.net");
        System.out.println(encrypt);
        System.out.println(AesUtil.decrypt(encrypt));

        String encrypt2 = AesUtil.encrypt("http://vegetable.api.postype.net");
        System.out.println(encrypt2);
        System.out.println(AesUtil.decrypt(encrypt2));
    }
}
