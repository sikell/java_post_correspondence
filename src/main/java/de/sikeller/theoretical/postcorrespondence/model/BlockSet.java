package de.sikeller.theoretical.postcorrespondence.model;

import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockSet {
    private Map<Integer, Block> set;

    public int size() {
        return set.size();
    }

    public Block get(int index) {
        return set.get(index);
    }

    public static BlockSetBuilder builder() {
        return new BlockSetBuilder();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class BlockSetBuilder {
        private final Map<Integer, Block> indexSet = new HashMap<>();
        private final AtomicInteger index = new AtomicInteger(1);

        public BlockSetBuilder add(Block block) {
            if (indexSet.values().contains(block)) {
                throw new IllegalArgumentException("This block is already added!");
            }
            indexSet.put(index.getAndIncrement(), block);
            return this;
        }

        public BlockSet build() {
            return new BlockSet(indexSet);
        }
    }
}
