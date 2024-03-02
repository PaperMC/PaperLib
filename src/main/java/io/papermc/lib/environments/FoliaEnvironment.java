package io.papermc.lib.environments;

public class FoliaEnvironment extends PaperEnvironment {

    public FoliaEnvironment() {
        super();
    }

    @Override
    public String getName() {
        return "Folia";
    }

    @Override
    public boolean isFolia() {
        return true;
    }
}
