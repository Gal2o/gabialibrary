package gabia.library.exception;

import gabia.library.common.exception.BusinessException;

public class ExtensionCountException extends BusinessException {

    public ExtensionCountException(String errorMessage) {
        super(errorMessage);
    }
}
