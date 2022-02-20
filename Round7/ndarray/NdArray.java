import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NdArray<E> extends AbstractCollection<E>
{
    private ArrayList<Integer> dimensionLens;
    private final ArrayList<E> data;
    private interface Offset { int calculate(int[] indexes); }
    private final Offset dataOffset = indexes -> IntStream.rangeClosed(1, indexes.length)
            .reduce(0, (sum, i) ->
                    sum + IntStream.range(i, indexes.length)
                            .reduce(1, (product, i2) ->
                                    product * dimensionLens.get(i2))
                            * indexes[i - 1]);

    public NdArray(Integer firstDimLen, Integer... furtherDimLens)
    {
        dimensionLens = new ArrayList<>(Stream.concat(Stream.of(firstDimLen), Arrays.stream(furtherDimLens)).toList());
        dimensionLens.stream().filter(x -> x < 0).findFirst().ifPresent(x -> {
            throw new NegativeArraySizeException(String.format("Illegal dimension size %d.", x));
        });
        data = new ArrayList<>(Collections.nCopies(this.size(), null));
    }

    public E get(int... indices)
    {
        if (indices.length != dimensionLens.size())
            throw new IllegalArgumentException(
                    String.format("The array has %d dimensions but %d indices were given.", dimensionLens.size(), indices.length));

        var dimensionIndex = new AtomicInteger(0);
        Arrays.stream(indices).forEach(x -> {
            var limit = dimensionLens.get(dimensionIndex.getAndIncrement());
            if (x < 0 || x >= limit)
                throw new IndexOutOfBoundsException(String.format("Illegal index %d for dimension %d of length %d.", x, dimensionIndex.get(), limit));
        });

        var offset = dataOffset.calculate(Arrays.stream(indices).toArray());
        return data.get(offset);
    }

    public void set(E item, int... indices)
    {
        if (indices.length != dimensionLens.size())
            throw new IllegalArgumentException(
                    String.format("The array has %d dimensions but %d indices were given.", dimensionLens.size(), indices.length));

        var dimensionIndex = new AtomicInteger(0);
        Arrays.stream(indices).forEach(x -> {
            var limit = dimensionLens.get(dimensionIndex.getAndIncrement());
            if (x < 0 || x >= limit)
                throw new IndexOutOfBoundsException(String.format("Illegal index %d for dimension %d of length %d.", x, dimensionIndex.get(), limit));
        });

        var offset = dataOffset.calculate(Arrays.stream(indices).toArray());
        data.set(offset, item);
    }

    @Override
    public Iterator<E> iterator()
    {
        return data.iterator();
    }

    @Override
    public int size()
    {
        return dimensionLens.isEmpty() ? 0 : dimensionLens.stream().reduce(1, Math::multiplyExact);
    }

    public int[] getDimensions()
    {
        return dimensionLens.stream().mapToInt(x -> x).toArray();
    }
}
