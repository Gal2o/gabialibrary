package gabia.library.utils.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

/**
 * @author Wade
 * This is a common page util class associated with a page.
 */

@Component
public class PageUtils {

    public int getRealPage(Integer page) {
        return isNull(page) ? 0 : page - 1;
    }

    public Pageable getPageable(Integer page, int pageSize, Sort.Direction direction, String... properties) {
        return PageRequest.of(getRealPage(page), pageSize, Sort.by(direction, properties));
    }

    public PageResponseData getPageResponseData(Page<?> pageList, int scaleSize) {
        PageResponseData pageResponseData = new PageResponseData();

        pageResponseData.setPagingInfo(pageList.getNumber(), pageList.getTotalPages(), scaleSize);

        return pageResponseData;
    }

}
