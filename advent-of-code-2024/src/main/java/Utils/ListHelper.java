package Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public final class ListHelper {
    private static final Logger logger = LogManager.getLogger(ListHelper.class);

    private ListHelper(){}

    protected List<Integer> convertToIntList(final List<String> input) {
        return input.stream().map(Integer::parseInt).toList();
    }
}
