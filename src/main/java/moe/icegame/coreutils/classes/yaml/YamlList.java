package moe.icegame.coreutils.classes.yaml;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * A list that directly writes and reads from a YamlConfiguration.
 * @param <T> The type of the elements in this list.
 */
public class YamlList<T> implements List<T> {
    private final ConfigurationSection cfg;
    private final String key;

    public YamlList(ConfigurationSection cfg, String key) {
        this.cfg = cfg;
        this.key = key;
    }

    private List<T> getList() {
        return (List<T>) cfg.getList(key);
    }

    private void writeList(List<T> li) {
        cfg.set(key, li);
    }

    @Override
    public int size() {
        return getList().size();
    }

    @Override
    public boolean isEmpty() {
        return getList().isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return getList().contains(o);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return getList().iterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return getList().toArray();
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return getList().toArray(a);
    }

    @Override
    public boolean add(T s) {
        List<T> li = getList();
        li.add(s);
        writeList(li);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        List<T> li = getList();
        boolean ret = li.remove(o);
        writeList(li);
        return ret;
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return new HashSet<>(getList()).containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        List<T> li = getList();
        boolean ret = li.addAll(c);
        writeList(li);
        return ret;
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends T> c) {
        List<T> li = getList();
        boolean ret = li.addAll(index, c);
        writeList(li);
        return ret;
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        List<T> li = getList();
        boolean ret = li.removeAll(c);
        writeList(li);
        return ret;
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        List<T> li = getList();
        boolean ret = li.retainAll(c);
        writeList(li);
        return ret;
    }

    @Override
    public void clear() {
        List<T> li = getList();
        li.clear();
        writeList(li);
    }

    @Override
    public T get(int index) {
        return getList().get(index);
    }

    @Override
    public T set(int index, T element) {
        List<T> li = getList();
        T ret = li.set(index, element);
        writeList(li);
        return ret;
    }

    @Override
    public void add(int index, T element) {
        List<T> li = getList();
        li.add(index, element);
        writeList(li);
    }

    @Override
    public T remove(int index) {
        List<T> li = getList();
        T ret = li.remove(index);
        writeList(li);
        return ret;
    }

    @Override
    public int indexOf(Object o) {
        return getList().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return getList().lastIndexOf(o);
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator() {
        return getList().listIterator();
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return getList().listIterator(index);
    }

    @NotNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return getList().subList(fromIndex, toIndex);
    }
}
