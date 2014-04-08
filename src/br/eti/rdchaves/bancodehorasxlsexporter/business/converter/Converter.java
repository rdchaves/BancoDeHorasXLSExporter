package br.eti.rdchaves.bancodehorasxlsexporter.business.converter;

import java.io.File;
import java.util.List;

public interface Converter<T> {

    File convert(File targetFile, List<T> checkpoints) throws Exception;

    File convert(List<T> checkpoints) throws Exception;

}
