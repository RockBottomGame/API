package de.ellpeck.rockbottom.api.tile.state;

import com.google.common.base.Preconditions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class SpecificIntProp extends TileProp<Integer> {

    private final int def;
    private final List<Integer> allowedValues;

    public SpecificIntProp(String name, Integer def, Integer... allowedValues) {
        this(name, def, Arrays.asList(allowedValues));
    }

    public SpecificIntProp(String name, Integer def, List<Integer> allowedValues) {
        super(name);
        this.def = def;
        this.allowedValues = Collections.unmodifiableList(allowedValues);

        Preconditions.checkArgument(this.allowedValues.contains(this.def), "The default value of a specific Integer prop has to be an allowed value too!");
    }

    @Override
    public int getVariants() {
        return this.allowedValues.size();
    }

    @Override
    public Integer getValue(int index) {
        return this.allowedValues.get(index);
    }

    @Override
    public int getIndex(Integer value) {
        return this.allowedValues.indexOf(value);
    }

    @Override
    public Integer getDefault() {
        return this.def;
    }
}
