package io.github.steviegt6.futile.backend.singletons;

public final class Singleton<T> {
    private T Instance;
    private boolean HasBeenSet;

    public T getInstance() throws Exception {
        if (!HasBeenSet)
            throw new Exception("Can't retrieve instance of unset singleton!");

        return Instance;
    }

    public void setInstance(T instance) throws Exception {
        if (HasBeenSet)
            throw new Exception("Can't set instance of already-set singleton!");

        HasBeenSet = true;
        Instance = instance;
    }

    public boolean hasBeenSet() {
        return HasBeenSet;
    }
}
