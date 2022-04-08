package com.example.search.engine.contentsupplier;

import java.io.IOException;
import java.util.List;

public interface ContentSupplier {
    List<String> supplyContent() throws IOException;
}
