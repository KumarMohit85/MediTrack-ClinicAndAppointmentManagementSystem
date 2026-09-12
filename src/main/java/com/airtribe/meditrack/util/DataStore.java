package com.airtribe.meditrack.util;

import java.util.ArrayList;
import java.util.List;

import com.airtribe.meditrack.entity.MedicalEntity;

public class DataStore<T extends MedicalEntity> {

    private List<T> data = new ArrayList<>();

    public void add(T item) {
        if (item != null) {
            data.add(item);
        }
    }

    public T getById(String id) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(id)) {
                return data.get(i);
            }
        }
        return null;
    }

    public List<T> getAll() {
        List<T> copy = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            copy.add(data.get(i));
        }
        return copy;
    }

    public void update(T item) {
        if (item == null) {
            return;
        }
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(item.getId())) {
                data.set(i, item);
                return;
            }
        }
    }

    public boolean delete(String id) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(id)) {
                data.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean exists(String id) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return data.size();
    }

    public void clear() {
        data.clear();
    }
}
