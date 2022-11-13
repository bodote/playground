package de.playground.so74230479;

public class ExceptionBadRequest {
    public void exceptionBadRequest(String errorMessage) throws Exception {
        errorMessage = errorMessage.equals("") ? "Error en su solicitud, request invalido! HTTP 400 Bad Request" : errorMessage;
        throw new Exception(
                errorMessage);
    }
    public void myOtherMethod(boolean myBoolean) throws Exception {
        boolean stateCampaign = myBoolean;
        if (!stateCampaign) {
            this.exceptionBadRequest("Esta campaña no se puede gestionar.");
        }
    }
}
