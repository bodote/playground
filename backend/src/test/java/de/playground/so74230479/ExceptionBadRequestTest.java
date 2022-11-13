package de.playground.so74230479;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionBadRequestTest {
    ExceptionBadRequest ebr = new ExceptionBadRequest();

    @Test
    void testNoFields() {
        Exception noValidCampaign = assertThrows(Exception.class,
                () -> ebr.myOtherMethod(false));
        assertEquals("Esta campaña no se puede gestionar.", noValidCampaign.getMessage());
    }
    @Test
    void testWithFields() {
        try {
            ebr.myOtherMethod(true);
        } catch (Exception e){
            fail("no exception expected");
        }

    }
}