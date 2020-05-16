package data.movables.player;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

import java.util.Arrays;

public class ArrayConverter  extends SimpleArgumentConverter {
    @Override
    protected Object convert(Object source, Class<?> aClass) throws ArgumentConversionException {

        String[] split = ((String) source).split("\\s*,\\s*");
        Long[] result = Arrays.stream(split).map(s -> Long.parseLong(s)).toArray(Long[]::new);
        return result;

    }
}
