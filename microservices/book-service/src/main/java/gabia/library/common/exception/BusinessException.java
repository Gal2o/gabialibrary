package gabia.library.common.exception;

// TODO: 추후에 공통으로 뺄 코드

/**
 * @author Wade
 * This class is that manages a business exception.
 */
public class BusinessException extends RuntimeException {

    private final String errorMessage;


    public BusinessException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
