package com.rbkmoney.geck.serializer.kit.tbase;

import com.rbkmoney.damsel.payment_processing.errors.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import static com.rbkmoney.geck.serializer.kit.tbase.TErrorMapping.*;

/**
 * Created by vpankrashkin on 27.02.18.
 */
public class ErrorMappingTest {

    @Test
    public void testFailure() throws IOException {
        PaymentFailure failure = PaymentFailure.rejected_by_inspector(new GeneralFailure());
        Assert.assertEquals("rejected_by_inspector", TErrorMapping.toStringVal(toGeneral(failure)));
        Assert.assertEquals("rejected_by_inspector", TErrorMapping.toStringVal(failure));
    }

    @Test
    public void testSubFailure() throws IOException {
        PaymentFailure failure = PaymentFailure.authorization_failed(AuthorizationFailure.payment_tool_rejected(PaymentToolReject.bank_card_rejected(BankCardReject.card_expired(new GeneralFailure()))));
        Assert.assertEquals("authorization_failed:payment_tool_rejected:bank_card_rejected:card_expired", TErrorMapping.toStringVal(toGeneral(failure)));
        Assert.assertEquals("authorization_failed:payment_tool_rejected:bank_card_rejected:card_expired", TErrorMapping.toStringVal(failure));
    }
}
