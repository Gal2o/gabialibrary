package gabia.library.common.exception;

// TODO: 추후에 공통으로 뺄 코드
public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
