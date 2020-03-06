package client.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActxApiException extends RuntimeException {

    private ActxApiError error;

    private ErrorCode eosErrorCode;

    public ActxApiException(ErrorCode eosErrorCode) {
        this.eosErrorCode = eosErrorCode;
    }

    public ActxApiException(ActxApiError apiError) {
        this.error = apiError;
    }

    public ActxApiException(String message, ErrorCode eosErrorCode) {
        super(message);
        this.eosErrorCode = eosErrorCode;
    }

    public ActxApiException(Throwable cause, ErrorCode eosErrorCode) {
        super(cause);
        this.eosErrorCode = eosErrorCode;
    }

    public ActxApiException(Throwable cause) {
        super(cause);
    }

    public ActxApiException(String message, Throwable cause, ErrorCode eosErrorCode) {
        super(message, cause);
        this.eosErrorCode = eosErrorCode;
    }

    public ErrorCode getEosErrorCode() {
        return eosErrorCode;
    }

    public void setEosErrorCode(ErrorCode eosErrorCode) {
        this.eosErrorCode = eosErrorCode;
    }

}
