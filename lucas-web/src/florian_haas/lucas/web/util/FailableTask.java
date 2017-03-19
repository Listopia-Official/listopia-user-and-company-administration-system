package florian_haas.lucas.web.util;

import java.util.List;

@FunctionalInterface
public interface FailableTask {

	public boolean executeTask(List<Object> params) throws Exception;

}
