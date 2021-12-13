package com.test.pricesearch.domain;

import lombok.*;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@NoArgsConstructor
public class Item {
    @NonNull
    private ItemType itemType;
    @NonNull
    private String name;
    private int price;
}
