package rodrigo.chaves.hoursbankconverter.exporter;

import java.io.File;
import java.util.List;

public interface Converter<T> {

    File convert(File targetFile, List<T> checkpoints) throws Exception;

    File convert(List<T> checkpoints) throws Exception;

}
