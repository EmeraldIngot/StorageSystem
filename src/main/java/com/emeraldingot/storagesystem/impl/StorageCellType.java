package com.emeraldingot.storagesystem.impl;

import com.emeraldingot.storagesystem.item.*;

public enum StorageCellType {
    CELL_1K(new StorageCell1K()),
    CELL_4K(new StorageCell4K()),
    CELL_16K(new StorageCell16K()),
    CELL_64K(new StorageCell64K()),
    CELL_256K(new StorageCell256K());

    private final StorageCell cell;

    StorageCellType(StorageCell cell) {
        this.cell = cell;
    }

    public StorageCell getCell() {
        return cell;
    }

}
